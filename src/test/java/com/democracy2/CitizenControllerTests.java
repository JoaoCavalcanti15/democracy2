package com.democracy2;

import com.democracy2.controllers.CitizenController;
import com.democracy2.domain.Citizen;
import com.democracy2.domain.Theme;
import com.democracy2.services.CitizenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CitizenControllerTests {

    @Mock
    private CitizenService citizenService;

    @InjectMocks
    private CitizenController citizenController;

    private List<Citizen> citizens;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize citizens
        citizens = List.of(
                new Citizen("Citizen 1", "123456789", "authToken1"),
                new Citizen("Citizen 2", "234567890", "authToken2"),
                new Citizen("Citizen 3", "345678901", "authToken3"),
                new Citizen("Citizen 4", "456789012", "authToken4"),
                new Citizen("Citizen 5", "567890123", "authToken5")
        );
    }

    @Test
    public void testGetAllCitizens() {
        when(citizenService.getAllCitizens()).thenReturn(citizens);

        List<Citizen> result = citizenController.getAllCitizens();

        assertEquals(5, result.size());
        assertEquals("Citizen 1", result.get(0).getName());
    }

    @Test
    public void testCreateCitizen() {
        Citizen newCitizen = new Citizen("Citizen 6", "678901234", "authToken6");
        when(citizenService.createCitizen(newCitizen)).thenReturn(newCitizen);

        Citizen result = citizenController.createCitizen(newCitizen);

        assertEquals("Citizen 6", result.getName());
    }

    @Test
    public void testGetCitizenById() {
        Citizen citizen = citizens.get(0);
        when(citizenService.getCitizenById(1L)).thenReturn(citizen);

        Citizen result = citizenController.getCitizenById(1L);

        assertEquals("Citizen 1", result.getName());
    }

    @Test
    public void testUpdateCitizen() {
        Citizen updatedCitizen = new Citizen("Updated Citizen", "123456789", "updatedToken");
        when(citizenService.updateCitizen(1L, updatedCitizen)).thenReturn(updatedCitizen);

        Citizen result = citizenController.updateCitizen(1L, updatedCitizen);

        assertEquals("Updated Citizen", result.getName());
    }

    @Test
    public void testDeleteCitizen() {
        // No return type for delete operation
        citizenController.deleteCitizen(1L);
    }

    @Test
    public void testChooseDelegate() {
        Citizen citizen = citizens.get(0);
        when(citizenService.chooseDelegate(1L, 2L, Theme.HEALTH)).thenReturn(citizen);

        ResponseEntity<Citizen> response = citizenController.chooseDelegate(1L, 2L, Theme.HEALTH);

        assertEquals("Citizen 1", response.getBody().getName());
    }
}
