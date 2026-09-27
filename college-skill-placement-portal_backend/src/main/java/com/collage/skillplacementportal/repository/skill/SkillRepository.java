package com.collage.skillplacementportal.repository.skill;

import com.collage.skillplacementportal.entity.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    Optional<Skill> findByName(String name);

    //Does Java already exist?↓
   // Yes → Don't create duplicate
   // No  → Create skill
}
