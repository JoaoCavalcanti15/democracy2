package com.democracy2.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String pdfAttachment;
    private LocalDateTime validity;
    
    @Enumerated(EnumType.STRING)
    private Theme theme;
    
    private boolean closed;

    @ManyToOne
    @JoinColumn(name = "delegate_id")
    private Delegate delegateProponent;

    @OneToOne(mappedBy = "proposal", cascade = CascadeType.ALL)
    private Vote vote;

    private int supportCount;

    public Proposal() {
    }

    public Proposal(String title, String description, String pdfAttachment, Theme theme, LocalDateTime validity, Delegate delegateProponent, boolean closed) {
        this.title = title;
        this.description = description;
        this.pdfAttachment = pdfAttachment;
        this.theme = theme;
        this.validity = validity;
        this.delegateProponent = delegateProponent;
        this.closed = closed;
        this.supportCount = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPdfAttachment() {
        return pdfAttachment;
    }

    public void setPdfAttachment(String pdfAttachment) {
        this.pdfAttachment = pdfAttachment;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public LocalDateTime getValidity() {
        return validity;
    }

    public void setValidity(LocalDateTime validity) {
        this.validity = validity;
    }

    public Delegate getDelegateProponent() {
        return delegateProponent;
    }

    public void setDelegateProponent(Delegate delegateProponent) {
        this.delegateProponent = delegateProponent;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public int getSupportCount() {
        return supportCount;
    }

    public void setSupportCount(int i) {
        this.supportCount = i;
    }

    public void incrementSupportCount() {
        this.supportCount++;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Proposal proposal = (Proposal) obj;
        return closed == proposal.closed &&
                Objects.equals(id, proposal.id) &&
                Objects.equals(title, proposal.title) &&
                Objects.equals(description, proposal.description) &&
                Objects.equals(pdfAttachment, proposal.pdfAttachment) &&
                theme == proposal.theme &&
                Objects.equals(validity, proposal.validity) &&
                Objects.equals(delegateProponent, proposal.delegateProponent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, pdfAttachment, theme, closed, validity, delegateProponent);
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pdfAttachment='" + pdfAttachment + '\'' +
                ", theme=" + theme +
                ", closed=" + closed +
                ", validity=" + validity +
                ", delegateProponent=" + delegateProponent +
                '}';
    }
}
