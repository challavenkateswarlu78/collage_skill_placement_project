package com.collage.skillplacementportal.service.skill;

import com.collage.skillplacementportal.entity.skill.Skill;
import com.collage.skillplacementportal.repository.skill.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    // Create skill
    public Skill createSkill(Skill skill) {

        if (skillRepository.findByName(skill.getName()).isPresent()) {
            throw new RuntimeException("Skill already exists");
        }

        return skillRepository.save(skill);
    }

    // Get all skills
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    // Get skill by ID
    public Skill getSkillById(Long id) {

        return skillRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Skill not found with id: " + id)
                );
    }

    // Update skill
    public Skill updateSkill(Long id, Skill skillDetails) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Skill not found with id: " + id)
                );

        skill.setName(skillDetails.getName());

        return skillRepository.save(skill);
    }

    // Delete skill
    public void deleteSkill(Long id) {

        if (!skillRepository.existsById(id)) {
            throw new RuntimeException("Skill not found with id: " + id);
        }

        skillRepository.deleteById(id);
    }
}
