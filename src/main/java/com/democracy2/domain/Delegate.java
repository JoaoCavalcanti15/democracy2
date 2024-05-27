package com.democracy2.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class Delegate extends Citizen {
    @OneToMany(mappedBy = "delegateProponent")
    private List<Proposal> proposals;

    public Delegate() {
    }

    public Delegate(String name, String citizenCardNumber, String authToken, List<Proposal> proposals) {
        super(name, citizenCardNumber, authToken);
        this.proposals = proposals;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Delegate delegate = (Delegate) obj;
        return Objects.equals(getId(), delegate.getId()) &&
                Objects.equals(getName(), delegate.getName()) &&
                Objects.equals(getCitizenCardNumber(), delegate.getCitizenCardNumber()) &&
                Objects.equals(getAuthToken(), delegate.getAuthToken()) &&
                Objects.equals(proposals, delegate.proposals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCitizenCardNumber(), getAuthToken(), proposals);
    }

    @Override
    public String toString() {
        return "Delegate{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", citizenCardNumber='" + getCitizenCardNumber() + '\'' +
                ", authToken='" + getAuthToken() + '\'' +
                ", proposals=" + proposals +
                '}';
    }
}
