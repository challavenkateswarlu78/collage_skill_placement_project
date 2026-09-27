package com.collage.skillplacementportal.repository.job;

import com.collage.skillplacementportal.entity.job.ApplicationStatus;
import com.collage.skillplacementportal.entity.job.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Long> {

    // Get all applications of a student
    List<JobApplication> findByStudentId(Long studentId);

    // Get student's applications with pagination
    Page<JobApplication> findByStudentId(
            Long studentId,
            Pageable pageable
    );

    // Get all applications for a job
    List<JobApplication> findByJobId(Long jobId);

    // Check whether student already applied for a job
    Optional<JobApplication> findByStudentIdAndJobId(
            Long studentId,
            Long jobId
    );

    // Get student's applications by status
    List<JobApplication> findByStudentIdAndStatus(
            Long studentId,
            ApplicationStatus status
    );

    // Get job applications by status
    List<JobApplication> findByJobIdAndStatus(
            Long jobId,
            ApplicationStatus status
    );

    // Search applications by job title
    List<JobApplication> findByJobTitleContainingIgnoreCase(
            String title
    );

    // Search applications by company
    List<JobApplication> findByJobCompanyContainingIgnoreCase(
            String company
    );

    // Search applications by student name
    List<JobApplication> findByStudentNameContainingIgnoreCase(
            String name
    );
}