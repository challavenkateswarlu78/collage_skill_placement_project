package com.collage.skillplacementportal.service.job;

import com.collage.skillplacementportal.entity.job.Job;
import com.collage.skillplacementportal.repository.job.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // Create job
    public Job createJob(Job job) {

        if (job.getActive() == null) {
            job.setActive(true);
        }

        return jobRepository.save(job);
    }

    // Get all jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Get job by ID
    public Job getJobById(Long id) {

        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Job not found with id: " + id
                        )
                );
    }

    // Get active jobs
    public List<Job> getActiveJobs() {
        return jobRepository.findByActiveTrue();
    }

    // Search by company
    public List<Job> getByCompany(String company) {
        return jobRepository.findByCompanyIgnoreCase(company);
    }

    // Search by location
    public List<Job> getByLocation(String location) {
        return jobRepository.findByLocationIgnoreCase(location);
    }

    // Search by job type
    public List<Job> getByJobType(String jobType) {
        return jobRepository.findByJobTypeIgnoreCase(jobType);
    }

    // Update job
    public Job updateJob(
            Long id,
            Job updatedJob) {

        Job existingJob = getJobById(id);

        existingJob.setTitle(
                updatedJob.getTitle()
        );

        existingJob.setCompany(
                updatedJob.getCompany()
        );

        existingJob.setDescription(
                updatedJob.getDescription()
        );

        existingJob.setLocation(
                updatedJob.getLocation()
        );

        existingJob.setExperience(
                updatedJob.getExperience()
        );

        existingJob.setJobType(
                updatedJob.getJobType()
        );

        existingJob.setJobUrl(
                updatedJob.getJobUrl()
        );

        existingJob.setActive(
                updatedJob.getActive()
        );

        return jobRepository.save(existingJob);
    }

    // Delete job
    public void deleteJob(Long id) {

        if (!jobRepository.existsById(id)) {
            throw new RuntimeException(
                    "Job not found with id: " + id
            );
        }

        jobRepository.deleteById(id);
    }
}