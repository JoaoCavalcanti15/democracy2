package com.democracy2.controllers;

import com.democracy2.domain.Delegate;
import com.democracy2.services.DelegateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delegates")
public class DelegateController {

    private final DelegateService delegateService;

    @Autowired
    public DelegateController(DelegateService delegateService) {
        this.delegateService = delegateService;
    }

    @GetMapping
    public ResponseEntity<List<Delegate>> getAllDelegates() {
        List<Delegate> delegates = delegateService.getAllDelegates();
        return ResponseEntity.ok(delegates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delegate> getDelegateById(@PathVariable Long id) {
        Delegate delegate = delegateService.findById(id);
        return ResponseEntity.ok(delegate);
    }

    // You can add more endpoints as needed for delegate-related operations
}
