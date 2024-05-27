package com.democracy2.services;

import com.democracy2.domain.Citizen;
import com.democracy2.domain.Delegate;
import com.democracy2.domain.Proposal;
import com.democracy2.domain.Vote;
import com.democracy2.repositories.CitizenRepository;
import com.democracy2.repositories.ProposalRepository;
import com.democracy2.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProposalService {

    private final ProposalRepository proposalRepository;
    private final VoteRepository voteRepository;
    private final CitizenRepository citizenRepository;

    @Autowired
    public ProposalService(ProposalRepository proposalRepository, 
                           VoteRepository voteRepository, 
                           CitizenRepository citizenRepository) {
        this.proposalRepository = proposalRepository;
        this.voteRepository = voteRepository;
        this.citizenRepository = citizenRepository;
    }

    public Proposal presentBill(Proposal proposal, Delegate delegateProponent) {
        proposal.setDelegateProponent(delegateProponent);
        proposal.setClosed(false);
        proposal.setSupportCount(0);
        return proposalRepository.save(proposal);
    }

    public Proposal supportProposal(Long proposalId, Long citizenId) {
        Proposal proposal = proposalRepository.findById(proposalId)
            .orElseThrow(() -> new IllegalArgumentException("Proposal not found"));

        Citizen citizen = citizenRepository.findById(citizenId)
            .orElseThrow(() -> new IllegalArgumentException("Citizen not found"));

        // Check if the citizen has already supported the proposal
        if (citizen.getSupportedProposals().contains(proposal)) {
            throw new IllegalArgumentException("Citizen has already supported this proposal");
        }

        // Add the proposal to the citizen's list of supported proposals
        citizen.getSupportedProposals().add(proposal);

        proposal.incrementSupportCount();
        if (proposal.getSupportCount() >= 10000) {
            openVoting(proposal);
        }

        return proposalRepository.save(proposal);
    }

    private void openVoting(Proposal proposal) {
        proposal.setClosed(false);

        // Determine the voting end date
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime proposedEndDate = proposal.getValidity().minusDays(1);
        LocalDateTime minEndDate = now.plusDays(15);
        LocalDateTime maxEndDate = now.plusDays(60);

        LocalDateTime votingEndDate = proposedEndDate;
        if (votingEndDate.isBefore(minEndDate)) {
            votingEndDate = minEndDate;
        } else if (votingEndDate.isAfter(maxEndDate)) {
            votingEndDate = maxEndDate;
        }

        // Create a new vote instance with initial vote count as 1
        Vote vote = new Vote(proposal, votingEndDate);
        voteRepository.save(vote);

        proposal.setVote(vote);
        proposalRepository.save(proposal);
    }

    public void closeExpiredProposals() {
        List<Proposal> expiredProposals = proposalRepository.findExpiredProposals(LocalDateTime.now());
        for (Proposal proposal : expiredProposals) {
            proposal.setClosed(true);
            proposalRepository.save(proposal);
        }
    }

    public List<Proposal> getActiveProposals() {
        return proposalRepository.findActiveProposals();
    }

    // Other service methods...
}
