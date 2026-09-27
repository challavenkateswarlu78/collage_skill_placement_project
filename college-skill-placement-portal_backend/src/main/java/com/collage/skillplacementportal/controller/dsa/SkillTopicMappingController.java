package com.collage.skillplacementportal.controller.dsa;

import com.collage.skillplacementportal.entity.dsa.SkillTopicMapping;
import com.collage.skillplacementportal.service.dsa.SkillTopicMappingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skill-topic-mappings")
public class SkillTopicMappingController {

    private final SkillTopicMappingService mappingService;

    public SkillTopicMappingController(
            SkillTopicMappingService mappingService) {

        this.mappingService = mappingService;
    }

    @PostMapping
    public SkillTopicMapping createMapping(
            @RequestBody SkillTopicMapping mapping) {

        return mappingService.createMapping(mapping);
    }

    @GetMapping
    public List<SkillTopicMapping> getAllMappings() {

        return mappingService.getAllMappings();
    }

    @GetMapping("/skill/{skillId}")
    public List<SkillTopicMapping> getMappingsBySkill(
            @PathVariable Long skillId) {

        return mappingService.getMappingsBySkill(skillId);
    }

    @DeleteMapping("/{id}")
    public void deleteMapping(
            @PathVariable Long id) {

        mappingService.deleteMapping(id);
    }
}