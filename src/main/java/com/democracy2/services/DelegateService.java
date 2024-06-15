package com.democracy2.services;

import com.democracy2.domain.Delegate;
import com.democracy2.repositories.DelegateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DelegateService {

    private final DelegateRepository delegateRepository;

    @Autowired
    public DelegateService(DelegateRepository delegateRepository) {
        this.delegateRepository = delegateRepository;
    }

    public Delegate findById(Long id) {
        return delegateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delegate not found"));
    }

    public List<Delegate> getAllDelegates() {
        return delegateRepository.findAll();
    }

    // You can add more service methods as needed
}
