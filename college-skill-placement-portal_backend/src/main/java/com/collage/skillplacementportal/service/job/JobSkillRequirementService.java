package com.collage.skillplacementportal.service.job;

import com.collage.skillplacementportal.entity.job.JobSkillRequirement;
import com.collage.skillplacementportal.repository.job.JobSkillRequirementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSkillRequirementService {

    private final JobSkillRequirementRepository requirementRepository;

    public JobSkillRequirementService(
            JobSkillRequirementRepository requirementRepository) {

        this.requirementRepository = requirementRepository;
    }

    // Create requirement
    public JobSkillRequirement createRequirement(
            JobSkillRequirement requirement) {

        if (requirement.getMinimumLevel() < 0 ||
                requirement.getMinimumLevel() > 100) {

            throw new RuntimeException(
                    "Minimum skill level must be between 0 and 100"
            );
        }

        return requirementRepository.save(requirement);
    }

    // Get all requirements
    public List<JobSkillRequirement> getAllRequirements() {

        return requirementRepository.findAll();
    }

    // Get requirements for a job
    public List<JobSkillRequirement> getByJobId(
            Long jobId) {

        return requirementRepository.findByJobId(
                jobId
        );
    }

    // Get jobs requiring a skill
    public List<JobSkillRequirement> getBySkillId(
            Long skillId) {

        return requirementRepository.findBySkillId(
                skillId
        );
    }

    // Get requirement by ID
    public JobSkillRequirement getById(Long id) {

        return requirementRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job skill requirement not found with id: "
                                        + id
                        )
                );
    }

    // Update requirement
    public JobSkillRequirement updateRequirement(
            Long id,
            JobSkillRequirement updatedRequirement) {

        JobSkillRequirement existing =
                getById(id);

        if (updatedRequirement.getMinimumLevel() < 0 ||
                updatedRequirement.getMinimumLevel() > 100) {

            throw new RuntimeException(
                    "Minimum skill level must be between 0 and 100"
            );
        }

        existing.setJob(
                updatedRequirement.getJob()
        );

        existing.setSkill(
                updatedRequirement.getSkill()
        );

        existing.setMinimumLevel(
                updatedRequirement.getMinimumLevel()
        );

        return requirementRepository.save(existing);
    }

    // Delete requirement
    public void deleteRequirement(Long id) {

        if (!requirementRepository.existsById(id)) {

            throw new RuntimeException(
                    "Job skill requirement not found with id: "
                            + id
            );
        }

        requirementRepository.deleteById(id);
    }
}
