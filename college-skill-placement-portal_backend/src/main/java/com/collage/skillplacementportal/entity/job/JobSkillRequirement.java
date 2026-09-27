package com.collage.skillplacementportal.entity.job;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import com.collage.skillplacementportal.entity.skill.Skill;
import jakarta.persistence.*;

@Entity
@Table(
        name = "job_skill_requirements",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"job_id", "skill_id"}
                )
        }
)
public class JobSkillRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "job_id",
            nullable = false
    )
    private Job job;

    @ManyToOne
    @JoinColumn(
            name = "skill_id",
            nullable = false
    )
    private Skill skill;

    @Column(nullable = false)
    @Min(
            value = 0,
            message = "Minimum skill level cannot be less than 0"
    )
    @Max(
            value = 100,
            message = "Minimum skill level cannot exceed 100"
    )
    private double minimumLevel;

    public JobSkillRequirement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public double getMinimumLevel() {
        return minimumLevel;
    }

    public void setMinimumLevel(double minimumLevel) {
        this.minimumLevel = minimumLevel;
    }
}