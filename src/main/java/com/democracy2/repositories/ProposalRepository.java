package com.democracy2.repositories;

import com.democracy2.domain.Proposal;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    // You can add custom query methods here if needed

    @Query("SELECT p FROM Proposal p WHERE p.validity <= :currentTime AND p.closed = false")
    List<Proposal> findExpiredProposals(@Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT p FROM Proposal p WHERE p.validity >= CURRENT_TIMESTAMP")
    List<Proposal> findActiveProposals();
}
