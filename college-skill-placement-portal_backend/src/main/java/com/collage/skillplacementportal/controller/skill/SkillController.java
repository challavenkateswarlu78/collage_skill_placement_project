package com.collage.skillplacementportal.controller.skill;

import com.collage.skillplacementportal.entity.skill.Skill;
import com.collage.skillplacementportal.service.skill.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    // Create skill
    @PostMapping
    public Skill createSkill(@RequestBody Skill skill) {
        return skillService.createSkill(skill);
    }

    // Get all skills
    @GetMapping
    public List<Skill> getAllSkills() {
        return skillService.getAllSkills();
    }

    // Get skill by ID
    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(skillService.getSkillById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update skill
    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(
            @PathVariable Long id,
            @RequestBody Skill skill) {

        try {
            return ResponseEntity.ok(
                    skillService.updateSkill(id, skill)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete skill
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {

        try {
            skillService.deleteSkill(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}