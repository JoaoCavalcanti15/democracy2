package com.democracy2.repositories;

import com.democracy2.domain.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {
    // You can add custom query methods here if needed
}
