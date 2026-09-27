package com.collage.skillplacementportal.entity.job;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "application_status_history")
public class ApplicationStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "application_id",
            nullable = false
    )
    private JobApplication application;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(nullable = false)
    private LocalDateTime changedAt;

    public ApplicationStatusHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobApplication getApplication() {
        return application;
    }

    public void setApplication(
            JobApplication application) {

        this.application = application;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(
            ApplicationStatus status) {

        this.status = status;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(
            LocalDateTime changedAt) {

        this.changedAt = changedAt;
    }
}