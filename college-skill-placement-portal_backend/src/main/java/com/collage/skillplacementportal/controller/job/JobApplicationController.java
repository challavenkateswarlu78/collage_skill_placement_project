package com.collage.skillplacementportal.controller.job;

import com.collage.skillplacementportal.dto.job.ApplicationSummaryDTO;
import com.collage.skillplacementportal.entity.job.ApplicationStatus;
import com.collage.skillplacementportal.entity.job.ApplicationStatusHistory;
import com.collage.skillplacementportal.entity.job.JobApplication;
import com.collage.skillplacementportal.repository.job.JobApplicationRepository;
import com.collage.skillplacementportal.service.job.JobApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.collage.skillplacementportal.service.security.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    private final JobApplicationService applicationService;
    private final AuthorizationService authorizationService;
    private final JobApplicationRepository jobApplicationRepository;
    public JobApplicationController(
            JobApplicationService applicationService, AuthorizationService authorizationService, JobApplicationRepository jobApplicationRepository) {

        this.applicationService = applicationService;
        this.authorizationService = authorizationService;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    // Student applies for a job
    @PostMapping("/student/{studentId}/job/{jobId}")
    public JobApplication applyForJob(
            @PathVariable Long studentId,
            @PathVariable Long jobId) {

        if (!authorizationService.isStudentOwner(studentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot apply using another student's ID"
            );
        }

        return applicationService.applyForJob(
                studentId,
                jobId
        );
    }

    // Get all applications of a student
    @GetMapping("/student/{studentId}")
    public List<JobApplication> getStudentApplications(
            @PathVariable Long studentId) {

        if (!authorizationService
                .isStudentOwner(studentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another student's applications"
            );
        }

        return applicationService
                .getStudentApplications(studentId);
    }

    // Get all applications for a job
    @GetMapping("/job/{jobId}")
    public List<JobApplication> getJobApplications(
            @PathVariable Long jobId) {

        return applicationService
                .getJobApplications(jobId);
    }

    // Get application by ID
    @GetMapping("/{id}")
    public JobApplication getApplicationById(
            @PathVariable Long id) {

        JobApplication application =
                applicationService.getApplicationById(id);

        if (authorizationService.getCurrentUser()
                .getRole().name().equals("STUDENT")) {

            if (!authorizationService.isStudentOwner(
                    application.getStudent().getId())) {

                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "You cannot access another student's application"
                );
            }
        }

        return application;
    }

    // Update application status
    @PutMapping("/{id}/status")
    public JobApplication updateStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {

        return applicationService.updateStatus(
                id,
                status
        );
    }

    // Get student's applications by status
    @GetMapping(
            "/student/{studentId}/status/{status}"
    )
    public List<JobApplication>
    getStudentApplicationsByStatus(
            @PathVariable Long studentId,
            @PathVariable ApplicationStatus status) {

        if (!authorizationService
                .isStudentOwner(studentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another student's applications"
            );
        }

        return applicationService
                .getStudentApplicationsByStatus(
                        studentId,
                        status
                );
    }

    // Get job applications by status
    @GetMapping(
            "/job/{jobId}/status/{status}"
    )
    public List<JobApplication>
    getJobApplicationsByStatus(
            @PathVariable Long jobId,
            @PathVariable ApplicationStatus status) {

        return applicationService
                .getJobApplicationsByStatus(
                        jobId,
                        status
                );
    }

    // Withdraw application
    @DeleteMapping("/{id}")
    public String deleteApplication(
            @PathVariable Long id) {

        JobApplication application =
                applicationService.getApplicationById(id);

        if (!authorizationService.isStudentOwner(
                application.getStudent().getId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot withdraw another student's application"
            );
        }

        applicationService.deleteApplication(id);

        return "Application withdrawn successfully";
    }

    @GetMapping("/student/{studentId}/summary")
    public ApplicationSummaryDTO getApplicationSummary(
            @PathVariable Long studentId) {
        if (!authorizationService
                .isStudentOwner(studentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another student's application summary"
            );
        }
        return applicationService
                .getApplicationSummary(studentId);
    }
    @GetMapping("/{id}/history")
    public List<ApplicationStatusHistory> getApplicationHistory(
            @PathVariable Long id) {

        JobApplication application =
                applicationService.getApplicationById(id);

        if (authorizationService.getCurrentUser()
                .getRole().name().equals("STUDENT")) {

            if (!authorizationService.isStudentOwner(
                    application.getStudent().getId())) {

                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "You cannot access another student's application history"
                );
            }
        }

        return applicationService
                .getApplicationHistory(id);
    }
    @GetMapping("/search")
    public ResponseEntity<List<JobApplication>> searchApplications(
            @RequestParam String keyword) {

        List<JobApplication> applications =
                jobApplicationRepository
                        .findByJobTitleContainingIgnoreCase(keyword);

        applications.addAll(
                jobApplicationRepository
                        .findByJobCompanyContainingIgnoreCase(keyword)
        );

        applications.addAll(
                jobApplicationRepository
                        .findByStudentNameContainingIgnoreCase(keyword)
        );

        return ResponseEntity.ok(applications);
    }
    // Get all applications with pagination
    @GetMapping
    public ResponseEntity<Page<JobApplication>> getAllApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<JobApplication> applications =
                jobApplicationRepository.findAll(pageable);

        return ResponseEntity.ok(applications);
    }
}