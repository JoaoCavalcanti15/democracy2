package com.democracy2.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Delegate extends Citizen {
    
    @OneToMany(mappedBy = "delegateProponent")
    private List<Proposal> proposals;

    public Delegate() {
    }
    
    public Delegate(Citizen c) {
        super(c.getName(), c.getCitizenCardNumber(), c.getAuthToken());
        this.setId(c.getId());
    }

    public Delegate(String name, String citizenCardNumber, String authToken) {
        super(name, citizenCardNumber, authToken);
        this.proposals = new ArrayList<>();
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    // Equals, HashCode, ToString

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Delegate delegate = (Delegate) obj;
        return Objects.equals(getName(), delegate.getName()) &&
                Objects.equals(getCitizenCardNumber(), delegate.getCitizenCardNumber()) &&
                Objects.equals(getAuthToken(), delegate.getAuthToken()) &&
                Objects.equals(proposals, delegate.proposals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCitizenCardNumber(), getAuthToken(), proposals);
    }

    @Override
    public String toString() {
        return "Delegate{" +
                ", name='" + getName() + '\'' +
                ", citizenCardNumber='" + getCitizenCardNumber() + '\'' +
                ", authToken='" + getAuthToken() + '\'' +
                ", proposals=" + proposals +
                '}';
    }
}

