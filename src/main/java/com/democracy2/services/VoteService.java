package com.democracy2.services;

import com.democracy2.domain.Vote;
import com.democracy2.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Vote> getOngoingVotes() {
        return voteRepository.findOngoingVotes();
    }

    public void closeVote(Long voteId) {
        // Retrieve the vote from the database
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new IllegalArgumentException("Vote not found"));

        // Check if the vote is already closed
        if (vote.getEndDate().isBefore(LocalDateTime.now()) || vote.getApproved()) {
            throw new IllegalStateException("Vote is already closed or has been approved");
        }

        // Update the vote status to closed
        vote.setEndDate(LocalDateTime.now());

        // Calculate the total votes (in favor and against)
        int totalVotes = vote.getVotesInFavor() + vote.getVotesAgainst();

        // Check if the proposal is approved or rejected based on the voting result
        if ((double) vote.getVotesInFavor() / totalVotes > 0.5) {
            vote.setApproved(true);
        } else {
            vote.setApproved(false);
        }

        // Save the updated vote
        voteRepository.save(vote);
    }
}
