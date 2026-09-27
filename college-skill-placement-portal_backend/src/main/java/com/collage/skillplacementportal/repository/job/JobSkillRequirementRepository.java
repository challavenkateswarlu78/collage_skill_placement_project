package com.collage.skillplacementportal.repository.job;

import com.collage.skillplacementportal.entity.job.JobSkillRequirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSkillRequirementRepository
        extends JpaRepository<JobSkillRequirement, Long> {

    List<JobSkillRequirement> findByJobId(
            Long jobId
    );

    List<JobSkillRequirement> findBySkillId(
            Long skillId
    );
}