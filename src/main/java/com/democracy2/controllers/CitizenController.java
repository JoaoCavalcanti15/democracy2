package com.democracy2.controllers;

import com.democracy2.domain.Citizen;
import com.democracy2.domain.Theme;
import com.democracy2.services.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citizens")
public class CitizenController {

    private final CitizenService citizenService;

    @Autowired
    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @GetMapping
    public List<Citizen> getAllCitizens() {
        return citizenService.getAllCitizens();
    }

    @PostMapping
    public Citizen createCitizen(@RequestBody Citizen citizen) {
        return citizenService.createCitizen(citizen);
    }

    @GetMapping("/{id}")
    public Citizen getCitizenById(@PathVariable Long id) {
        return citizenService.getCitizenById(id);
    }

    @PutMapping("/{id}")
    public Citizen updateCitizen(@PathVariable Long id, @RequestBody Citizen citizenDetails) {
        return citizenService.updateCitizen(id, citizenDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteCitizen(@PathVariable Long id) {
        citizenService.deleteCitizen(id);
    }

    @PostMapping("/{citizenId}/choose-delegate")
    public ResponseEntity<Citizen> chooseDelegate(@PathVariable Long citizenId,
                                                   @RequestParam Long delegateId,
                                                   @RequestParam Theme theme) {
        Citizen chosenCitizen = citizenService.chooseDelegate(citizenId, delegateId, theme);
        return ResponseEntity.ok(chosenCitizen);
    }

    
}
