package com.democracy2.controllers;

import com.democracy2.domain.Delegate;
import com.democracy2.domain.Proposal;
import com.democracy2.services.DelegateService;
import com.democracy2.services.ProposalService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proposals")
public class ProposalController {

    private final ProposalService proposalService;
    private final DelegateService delegateService;

    @Autowired
    public ProposalController(ProposalService proposalService, DelegateService delegateService) {
        this.proposalService = proposalService;
        this.delegateService = delegateService;
    }

    @PostMapping("/present")
    public ResponseEntity<Proposal> presentBill(@RequestBody Proposal proposal,
                                                @RequestParam Long delegateId) {
        // Retrieve the delegate from the database (you can use delegateId)
        Delegate delegateProponent = delegateService.findById(delegateId);
        
        // Present the bill
        Proposal presentedProposal = proposalService.presentBill(proposal.getId(), delegateProponent.getId());
        
        return ResponseEntity.ok(presentedProposal);
    }

    @GetMapping("/closeExpired")
    public ResponseEntity<String> closeExpiredProposals() {
        proposalService.closeExpiredProposals();
        return ResponseEntity.ok("Expired proposals closed successfully.");
    }

    @GetMapping("/active")
    public ResponseEntity<List<Proposal>> getActiveProposals() {
        List<Proposal> activeProposals = proposalService.getActiveProposals();
        return ResponseEntity.ok(activeProposals);
    }

    @PostMapping("/{proposalId}/support")
    public ResponseEntity<Proposal> supportProposal(@PathVariable Long proposalId,
                                                    @RequestParam Long citizenId) {
        Proposal supportedProposal = proposalService.supportProposal(proposalId, citizenId);
        return ResponseEntity.ok(supportedProposal);
    }

    // You can add more controller methods as needed
}
