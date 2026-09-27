package com.collage.skillplacementportal.controller.job;

import com.collage.skillplacementportal.entity.job.JobSkillRequirement;
import com.collage.skillplacementportal.service.job.JobSkillRequirementService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-skill-requirements")
public class JobSkillRequirementController {

    private final JobSkillRequirementService requirementService;

    public JobSkillRequirementController(
            JobSkillRequirementService requirementService) {

        this.requirementService = requirementService;
    }

    @PostMapping
    public JobSkillRequirement createRequirement(
            @Valid @RequestBody JobSkillRequirement requirement){

        return requirementService.createRequirement(
                requirement
        );
    }

    @GetMapping
    public List<JobSkillRequirement> getAllRequirements() {

        return requirementService.getAllRequirements();
    }

    @GetMapping("/{id}")
    public JobSkillRequirement getById(
            @PathVariable Long id) {

        return requirementService.getById(id);
    }

    @GetMapping("/job/{jobId}")
    public List<JobSkillRequirement> getByJobId(
            @PathVariable Long jobId) {

        return requirementService.getByJobId(
                jobId
        );
    }

    @GetMapping("/skill/{skillId}")
    public List<JobSkillRequirement> getBySkillId(
            @PathVariable Long skillId) {

        return requirementService.getBySkillId(
                skillId
        );
    }

    @PutMapping("/{id}")
    public JobSkillRequirement updateRequirement(
            @PathVariable Long id,
            @Valid @RequestBody JobSkillRequirement requirement) {

        return requirementService.updateRequirement(
                id,
                requirement
        );
    }

    @DeleteMapping("/{id}")
    public String deleteRequirement(
            @PathVariable Long id) {

        requirementService.deleteRequirement(id);

        return "Job skill requirement deleted successfully";
    }
}