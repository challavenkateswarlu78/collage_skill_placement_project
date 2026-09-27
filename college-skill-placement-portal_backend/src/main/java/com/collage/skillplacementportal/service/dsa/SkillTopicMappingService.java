package com.collage.skillplacementportal.service.dsa;

import com.collage.skillplacementportal.entity.dsa.SkillTopicMapping;
import com.collage.skillplacementportal.repository.dsa.SkillTopicMappingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillTopicMappingService {

    private final SkillTopicMappingRepository mappingRepository;

    public SkillTopicMappingService(
            SkillTopicMappingRepository mappingRepository) {

        this.mappingRepository = mappingRepository;
    }

    public SkillTopicMapping createMapping(
            SkillTopicMapping mapping) {

        return mappingRepository.save(mapping);
    }

    public List<SkillTopicMapping> getMappingsBySkill(
            Long skillId) {

        return mappingRepository.findBySkillId(skillId);
    }

    public List<SkillTopicMapping> getAllMappings() {

        return mappingRepository.findAll();
    }

    public void deleteMapping(Long id) {

        if (!mappingRepository.existsById(id)) {
            throw new RuntimeException(
                    "Mapping not found with id: " + id
            );
        }

        mappingRepository.deleteById(id);
    }
}