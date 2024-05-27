package com.democracy2.controllers;

import com.democracy2.domain.Vote;
import com.democracy2.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @GetMapping("/ongoing")
    public ResponseEntity<List<Vote>> getOngoingVotes() {
        List<Vote> votes = voteService.getOngoingVotes();
        return ResponseEntity.ok(votes);
    }

    @PostMapping("/close/{voteId}")
    public ResponseEntity<Void> closeVote(@PathVariable Long voteId) {
        voteService.closeVote(voteId);
        return ResponseEntity.noContent().build();
    }
}
