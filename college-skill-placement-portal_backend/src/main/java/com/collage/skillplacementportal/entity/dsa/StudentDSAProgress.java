package com.collage.skillplacementportal.entity.dsa;

import com.collage.skillplacementportal.entity.student.Student;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "student_dsa_progress",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"student_id", "problem_id"}
                )
        }
)
public class StudentDSAProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private DSAProblem problem;

    @Column(nullable = false)
    private Boolean solved;

    private Integer pointsEarned;

    private LocalDateTime solvedAt;

    public StudentDSAProgress() {
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

    public DSAProblem getProblem() {
        return problem;
    }

    public void setProblem(DSAProblem problem) {
        this.problem = problem;
    }

    public Boolean getSolved() {
        return solved;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }

    public Integer getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(Integer pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public LocalDateTime getSolvedAt() {
        return solvedAt;
    }

    public void setSolvedAt(LocalDateTime solvedAt) {
        this.solvedAt = solvedAt;
    }
}