package com.democracy2.repositories;

import com.democracy2.domain.Delegate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelegateRepository extends JpaRepository<Delegate, Long> {
    // You can add custom query methods here if needed
}
