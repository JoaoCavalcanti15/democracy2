package com.democracy2.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "delegateProponent")
    private Proposal proposal;

    private int votesInFavor;
    private int votesAgainst;
    private LocalDateTime endDate;

    private boolean approved;

    public Vote() {
    }

    public Vote(Proposal proposal, LocalDateTime endDate) {
        this.proposal = proposal;
        this.endDate = endDate;
        this.votesInFavor = 1; // Starts with 1 because of the delegate's vote
        this.votesAgainst = 0;
        approved = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    public int getVotesInFavor() {
        return votesInFavor;
    }

    public void setVotesInFavor(int votesInFavor) {
        this.votesInFavor = votesInFavor;
    }

    public int getVotesAgainst() {
        return votesAgainst;
    }

    public void setVotesAgainst(int votesAgainst) {
        this.votesAgainst = votesAgainst;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean getApproved() {
        return this.approved;
    }

    public void setApproved(boolean b) {
        this.approved = b;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vote vote = (Vote) obj;
        return votesInFavor == vote.votesInFavor &&
               votesAgainst == vote.votesAgainst &&
                Objects.equals(id, vote.id) &&
                Objects.equals(proposal, vote.proposal) &&
                Objects.equals(endDate, vote.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, proposal, votesInFavor, votesAgainst, endDate);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", proposal=" + proposal +
                ", votesInFavor=" + votesInFavor +
                ", votesAgainst=" + votesAgainst +
                ", endDate=" + endDate +
                '}';
    }
}
