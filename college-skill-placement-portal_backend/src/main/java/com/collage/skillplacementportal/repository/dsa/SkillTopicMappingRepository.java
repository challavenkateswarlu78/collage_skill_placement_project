package com.collage.skillplacementportal.repository.dsa;

import com.collage.skillplacementportal.entity.dsa.SkillTopicMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillTopicMappingRepository
        extends JpaRepository<SkillTopicMapping, Long> {

    List<SkillTopicMapping> findBySkillId(
            Long skillId
    );

    List<SkillTopicMapping> findBySkillIdIn(
            List<Long> skillIds
    );
}