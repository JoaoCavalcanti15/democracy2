package com.democracy2;

import com.democracy2.controllers.ProposalController;
import com.democracy2.domain.Citizen;
import com.democracy2.domain.Delegate;
import com.democracy2.domain.Proposal;
import com.democracy2.domain.Theme;
import com.democracy2.domain.Vote;
import com.democracy2.repositories.*;
import com.democracy2.services.CitizenService;
import com.democracy2.services.ProposalService;
import com.democracy2.services.VoteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class Democracy2Tests {

    @Autowired
    private ProposalController proposalController;

    @Autowired
    private ProposalService proposalService;

    @Autowired
    private VoteService voteService;
    
    @Autowired
    private CitizenService citizenService;
    
    @Autowired
    private CitizenRepository citizenRepository;
    
    @Autowired
    private DelegateRepository delegateRepository;
    
    @Autowired
    private ProposalRepository proposalRepository;
    
    @Autowired
    private VoteRepository voteRepository;

    private LocalDateTime date;
    
    private Citizen citizen1;
    private Citizen citizen2;
    private Citizen citizen3;
    
    private Delegate delegate1;
    private Delegate delegate2;

    private Theme theme1;
    private Theme theme2;
    private Theme theme3;
    
    private Proposal proposal1;
    private Proposal proposal2;
    private Proposal proposal3;
    
    private Vote vote1; 

    @BeforeEach
    @Transactional
    void setup() {
        
        date = LocalDateTime.now().plusDays(30);
        
        citizen1 = new Citizen("John Doe", "1234567890", "authToken1");
        citizen2 = new Citizen("Alice Smith", "0987654321", "authToken2");
        citizen3 = new Citizen("Bob Johnson", "5432109876", "authToken3");
       
        delegate1 = new Delegate("John Doe Delegate", "1234567890D", "authToken1D");
        delegate2 = new Delegate("Alice Smith Delegate", "0987654321D", "authToken2D");

        theme1 = Theme.HEALTH;
        theme2 = Theme.EDUCATION;
        theme3 = Theme.IMMIGRATION;
        
        proposal1 = new Proposal("Hospitals", "Invest", "proposal1.pdf", theme1, date, delegate1);
        proposal2 = new Proposal("Schools", "Improve education system", "proposal2.pdf", theme2, date, delegate2);
        proposal3 = new Proposal("Immigration Policy", "Revise immigration laws", "proposal3.pdf", theme3, date, delegate1);
        
        vote1 = new Vote(proposal1, date);
        		
        // Save the entities
        citizenRepository.save(citizen1);
        citizenRepository.save(citizen2);
        citizenRepository.save(citizen3);
        
        delegateRepository.save(delegate1);
        delegateRepository.save(delegate2);
        
        proposalRepository.save(proposal1);
        proposalRepository.save(proposal2);
        proposalRepository.save(proposal3);
        
        voteRepository.save(vote1);
    }

    
    @Test
    @Transactional
    void testOngoingVotes() {
        assertEquals(voteService.getOngoingVotes().size(), 1);
    }
    
    @Test
    @Transactional
    void testPresentBill() {
    	proposalService.presentBill(proposal1.getId(), delegate1.getId());
    	proposalRepository.save(proposal1);
    	delegateRepository.save(delegate1);
    	assertEquals(delegate1.getProposals().size(), 1);
    }
    
    @Test
    @Transactional
    void testCloseExpiredProposals() {
    	List<Proposal> proposals = proposalService.getActiveProposals();
    	LocalDateTime date1 = LocalDateTime.now().minusDays(30);
    	proposal1.setValidity(date1);
    	proposalRepository.save(proposal1);
    	proposalService.closeExpiredProposals();
    	assertEquals(proposalService.getActiveProposals().size(), 2);
    }
    
    @Test
    void testGetActiveProposals() {
    	assertEquals(proposalService.getActiveProposals().size(), 3);
    }
    
    @Test	
    @Transactional
    void testSupportProposal() {
    	proposal2.setSupportCount(9999);
        proposalRepository.save(proposal2);
        proposalService.supportProposal(proposal2.getId(), citizen2.getId());
        assertEquals(voteService.getOngoingVotes().size(), 2);
        assertEquals(citizen2.getSupportedProposals().size(), 1);
    }
    
    @Test
    @Transactional
    void testChooseDelegate() {
    	citizenService.chooseDelegate(citizen1.getId(), delegate1.getId(), theme1);
    	assertEquals(citizen1.getChosenDelegates().size(), 1);
    }
    
    @Test
    @Transactional
    void testVoteInProposal() {
    	citizenService.voteInProposal(citizen1.getId(), vote1.getId(), true);
    	citizenService.voteInProposal(citizen2.getId(), vote1.getId(),false);
    	assertEquals(vote1.getVotesAgainst(),1);
    	assertEquals(vote1.getVotesInFavor(),2);
    }
    
    @Test
    @Transactional
    void testCloseVotes() {
    	citizenService.voteInProposal(citizen1.getId(), vote1.getId(), true);
    	citizenService.voteInProposal(citizen2.getId(), vote1.getId(),false);
    	voteService.closeVote(vote1.getId());
    	assertEquals(vote1.getApproved(), true);
    }
    
    
    
    
    
    
    
    
}

