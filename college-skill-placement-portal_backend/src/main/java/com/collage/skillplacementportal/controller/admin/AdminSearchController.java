package com.collage.skillplacementportal.controller.admin;

import com.collage.skillplacementportal.entity.job.Job;
import com.collage.skillplacementportal.entity.job.JobApplication;
import com.collage.skillplacementportal.entity.student.Student;
import com.collage.skillplacementportal.repository.job.JobApplicationRepository;
import com.collage.skillplacementportal.repository.job.JobRepository;
import com.collage.skillplacementportal.repository.student.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/search")
public class AdminSearchController {

    private final StudentRepository studentRepository;
    private final JobRepository jobRepository;
    private final JobApplicationRepository jobApplicationRepository;

    public AdminSearchController(
            StudentRepository studentRepository,
            JobRepository jobRepository,
            JobApplicationRepository jobApplicationRepository) {

        this.studentRepository = studentRepository;
        this.jobRepository = jobRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> searchStudents(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                studentRepository
                        .findByNameContainingIgnoreCase(keyword)
        );
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> searchJobs(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                jobRepository
                        .findByTitleContainingIgnoreCase(keyword)
        );
    }

    @GetMapping("/applications")
    public ResponseEntity<List<JobApplication>> searchApplications(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                jobApplicationRepository
                        .findByJobTitleContainingIgnoreCase(keyword)
        );
    }
}
