package com.collage.skillplacementportal.controller.job;
import com.collage.skillplacementportal.repository.job.JobRepository;
import jakarta.validation.Valid;
import com.collage.skillplacementportal.entity.job.Job;
import com.collage.skillplacementportal.service.job.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;
    private final JobRepository jobRepository;
    public JobController(JobService jobService,
                         JobRepository jobRepository) {
        this.jobService = jobService;
        this.jobRepository = jobRepository;
    }

    // Create job
    @PostMapping
    public Job createJob(
            @Valid @RequestBody Job job) {
        return jobService.createJob(job);
    }

    // Get all jobs
    @GetMapping
    public ResponseEntity<Page<Job>> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Job> jobs = jobRepository.findAll(pageable);

        return ResponseEntity.ok(jobs);
    }

    // Get job by ID
    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    // Get active jobs
    @GetMapping("/active")
    public List<Job> getActiveJobs() {
        return jobService.getActiveJobs();
    }

    // Search by company
    @GetMapping("/company/{company}")
    public List<Job> getByCompany(
            @PathVariable String company) {

        return jobService.getByCompany(company);
    }

    // Search by location
    @GetMapping("/location/{location}")
    public List<Job> getByLocation(
            @PathVariable String location) {

        return jobService.getByLocation(location);
    }

    // Search by job type
    @GetMapping("/type/{jobType}")
    public List<Job> getByJobType(
            @PathVariable String jobType) {

        return jobService.getByJobType(jobType);
    }

    // Update job
    @PutMapping("/{id}")
    public Job updateJob(
            @PathVariable Long id,
            @Valid @RequestBody Job job) {

        return jobService.updateJob(id, job);
    }

    // Delete job
    @DeleteMapping("/{id}")
    public String deleteJob(
            @PathVariable Long id) {

        jobService.deleteJob(id);

        return "Job deleted successfully";
    }

    @GetMapping("/search")
    public ResponseEntity<List<Job>> searchJobs(
            @RequestParam String keyword) {

        List<Job> jobs =
                jobRepository.findByTitleContainingIgnoreCase(keyword);

        jobs.addAll(
                jobRepository.findByCompanyContainingIgnoreCase(keyword)
        );

        return ResponseEntity.ok(jobs);
    }
}