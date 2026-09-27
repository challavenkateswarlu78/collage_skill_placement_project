package com.collage.skillplacementportal.entity.job;

import com.collage.skillplacementportal.entity.student.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "job_applications",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"student_id", "job_id"}
                )
        }
)
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "student_id",
            nullable = false
    )
    private Student student;

    @ManyToOne
    @JoinColumn(
            name = "job_id",
            nullable = false
    )
    private Job job;


    @NotNull(message = "Application status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    private LocalDateTime appliedAt;

    private LocalDateTime updatedAt;
    private double matchPercentageAtApplication;

    public JobApplication() {
    }
    public double getMatchPercentageAtApplication() {
        return matchPercentageAtApplication;
    }
    public void setMatchPercentageAtApplication(
            double matchPercentageAtApplication) {

        this.matchPercentageAtApplication =
                matchPercentageAtApplication;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}