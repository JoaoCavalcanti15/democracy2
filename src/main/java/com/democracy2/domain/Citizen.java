package com.democracy2.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Citizen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @Column(unique = true)
    protected String citizenCardNumber;

    @Column(unique = true)
    protected String authToken;

    @OneToMany(mappedBy = "delegateProponent", cascade = CascadeType.ALL)
    private List<Proposal> supportedProposals;

    @OneToMany(mappedBy = "proposal", cascade = CascadeType.ALL)
    private List<Vote> votes;

    @ElementCollection
    @CollectionTable(name = "chosen_delegates", joinColumns = @JoinColumn(name = "citizen_id"))
    @MapKeyJoinColumn(name = "delegate_id")
    @Column(name = "theme")
    private Map<Delegate, Theme> chosenDelegates = new HashMap<>();

    public Citizen() {
        this.supportedProposals = new ArrayList<>();
    }

    public Citizen(String name, String citizenCardNumber, String authToken) {
        this.name = name;
        this.citizenCardNumber = citizenCardNumber;
        this.authToken = authToken;
        this.supportedProposals = new ArrayList<>();
        this.votes = new ArrayList<>();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCitizenCardNumber() {
        return citizenCardNumber;
    }

    public void setCitizenCardNumber(String citizenCardNumber) {
        this.citizenCardNumber = citizenCardNumber;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public List<Proposal> getSupportedProposals() {
        return supportedProposals;
    }

    public void setSupportedProposals(List<Proposal> supportedProposals) {
        this.supportedProposals = supportedProposals;
    }

    public List<Vote> getVotedProposals() {
        return this.votes;
    }

    public void setVotedProposals(List<Vote> votes) {
        this.votes = votes;
    }

    public Map<Delegate, Theme> getChosenDelegates() {
        return chosenDelegates;
    }

    public void setChosenDelegates(Map<Delegate, Theme> chosenDelegates) {
        this.chosenDelegates = chosenDelegates;
    }

    // Equals, HashCode, ToString

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Citizen citizen = (Citizen) obj;
        return Objects.equals(name, citizen.name) &&
                Objects.equals(citizenCardNumber, citizen.citizenCardNumber) &&
                Objects.equals(authToken, citizen.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, citizenCardNumber, authToken);
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", citizenCardNumber='" + citizenCardNumber + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}
