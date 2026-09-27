package com.collage.skillplacementportal.controller.job;

import com.collage.skillplacementportal.dto.job.JobMatchDTO;
import com.collage.skillplacementportal.dto.job.JobSkillMatchDTO;
import com.collage.skillplacementportal.service.job.JobMatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-match")
public class JobMatchController {

    private final JobMatchService jobMatchService;

    public JobMatchController(
            JobMatchService jobMatchService) {

        this.jobMatchService = jobMatchService;
    }

    @GetMapping("/student/{studentId}/job/{jobId}")
    public JobMatchDTO calculateMatch(
            @PathVariable Long studentId,
            @PathVariable Long jobId) {

        return jobMatchService.calculateMatch(
                studentId,
                jobId
        );
    }
    @GetMapping("/student/{studentId}")
    public List<JobMatchDTO> getMatchesForStudent(
            @PathVariable Long studentId) {

        return jobMatchService.getMatchesForStudent(
                studentId
        );
    }
    @GetMapping("/student/{studentId}/recommended")
    public List<JobMatchDTO> getRecommendedJobs(
            @PathVariable Long studentId) {

        return jobMatchService.getRecommendedJobs(
                studentId
        );
    }
    @GetMapping(
            "/student/{studentId}/job/{jobId}/skills"
    )
    public List<JobSkillMatchDTO> getSkillMatches(
            @PathVariable Long studentId,
            @PathVariable Long jobId) {

        return jobMatchService.getSkillMatches(
                studentId,
                jobId
        );
    }
}