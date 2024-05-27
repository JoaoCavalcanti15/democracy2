package com.democracy2.services;

import com.democracy2.domain.Citizen;
import com.democracy2.domain.Delegate;
import com.democracy2.domain.Theme;
import com.democracy2.domain.Vote;
import com.democracy2.repositories.CitizenRepository;
import com.democracy2.repositories.DelegateRepository;
import com.democracy2.repositories.VoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitizenService {

    private final CitizenRepository citizenRepository;
    private final DelegateRepository delegateRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public CitizenService(CitizenRepository citizenRepository, 
                          DelegateRepository delegateRepository, 
                          VoteRepository voteRepository) {
        this.citizenRepository = citizenRepository;
        this.delegateRepository = delegateRepository;
        this.voteRepository = voteRepository;
    }

    public List<Citizen> getAllCitizens() {
        return citizenRepository.findAll();
    }

    public Citizen createCitizen(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    public Citizen getCitizenById(Long id) {
        Optional<Citizen> optionalCitizen = citizenRepository.findById(id);
        return optionalCitizen.orElse(null);
    }

    public Citizen updateCitizen(Long id, Citizen citizenDetails) {
        Optional<Citizen> optionalCitizen = citizenRepository.findById(id);
        if (optionalCitizen.isPresent()) {
            Citizen existingCitizen = optionalCitizen.get();
            existingCitizen.setName(citizenDetails.getName());
            existingCitizen.setCitizenCardNumber(citizenDetails.getCitizenCardNumber());
            existingCitizen.setAuthToken(citizenDetails.getAuthToken());
            return citizenRepository.save(existingCitizen);
        } else {
            return null; // Handle not found case
        }
    }

    public void deleteCitizen(Long id) {
        citizenRepository.deleteById(id);
    }
    
    public Citizen chooseDelegate(Long citizenId, Long delegateId, Theme theme) {
        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new IllegalArgumentException("Citizen not found"));
    
        Delegate delegate = delegateRepository.findById(delegateId)
                .orElseThrow(() -> new IllegalArgumentException("Delegate not found"));
    
        // Check if the citizen has already chosen a delegate for the given theme
        if (citizen.getChosenDelegates().containsValue(theme)) {
            throw new IllegalStateException("Citizen has already chosen a delegate for the theme: " + theme);
        }
    
        // Add the chosen delegate for the theme
        citizen.getChosenDelegates().put(delegate,theme);
    
        // Save the updated citizen
        return citizenRepository.save(citizen);
    }

    public Citizen voteInProposal(Long citizenId, Long voteId, boolean inFavor) {
        // Retrieve citizen from the database
        Citizen citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new IllegalArgumentException("Citizen not found"));

        // Check if citizen has already voted on this proposal
        for (Vote vote : citizen.getVotedProposals()) {
            if (vote.getProposal().getId().equals(voteId)) {
                // Citizen has already voted on this proposal, return without registering the vote
                return citizen;
            }
        }

        // Register the citizen's vote
        Vote newVote = voteRepository.getById(voteId);
        if (inFavor) {
            newVote.setVotesInFavor(newVote.getVotesInFavor() + 1); 
        } else {
            newVote.setVotesAgainst(newVote.getVotesAgainst() + 1); 
        }
        voteRepository.save(newVote);

        // Add the new vote to the citizen's list of votes
        citizen.getVotedProposals().add(newVote);
        citizenRepository.save(citizen);

        return citizen;
    }
}
