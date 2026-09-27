package com.collage.skillplacementportal.entity.dsa;

import com.collage.skillplacementportal.entity.skill.Skill;
import jakarta.persistence.*;

@Entity
@Table(
        name = "skill_topic_mapping",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"skill_id", "topic"}
                )
        }
)
public class SkillTopicMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(nullable = false)
    private String topic;

    public SkillTopicMapping() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}