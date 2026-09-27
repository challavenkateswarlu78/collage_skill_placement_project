package com.collage.skillplacementportal.service.job;

import com.collage.skillplacementportal.dto.job.ApplicationSummaryDTO;
import com.collage.skillplacementportal.dto.job.JobMatchDTO;
import com.collage.skillplacementportal.entity.job.ApplicationStatus;
import com.collage.skillplacementportal.entity.job.ApplicationStatusHistory;
import com.collage.skillplacementportal.entity.job.Job;
import com.collage.skillplacementportal.entity.job.JobApplication;
import com.collage.skillplacementportal.entity.student.Student;
import com.collage.skillplacementportal.exception.DuplicateResourceException;
import com.collage.skillplacementportal.repository.job.ApplicationStatusHistoryRepository;
import com.collage.skillplacementportal.repository.job.JobApplicationRepository;
import com.collage.skillplacementportal.repository.job.JobRepository;
import com.collage.skillplacementportal.repository.student.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import com.collage.skillplacementportal.service.notification.NotificationService;
@Service
public class JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final StudentRepository studentRepository;
    private final JobMatchService jobMatchService;
    private final ApplicationStatusHistoryRepository historyRepository;
    private final NotificationService notificationService;

    public JobApplicationService(
            JobApplicationRepository applicationRepository,
            JobRepository jobRepository,
            StudentRepository studentRepository,
            JobMatchService jobMatchService,
            ApplicationStatusHistoryRepository historyRepository,
            NotificationService notificationService) {

        this.applicationRepository =
                applicationRepository;

        this.jobRepository =
                jobRepository;

        this.studentRepository =
                studentRepository;

        this.jobMatchService =
                jobMatchService;

        this.historyRepository =
                historyRepository;
        this.notificationService = notificationService;
    }

    // =========================================================
    // Apply for a job
    // =========================================================

    public JobApplication applyForJob(
            Long studentId,
            Long jobId) {

        // Find student
        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student not found with id: "
                                                + studentId
                                )
                        );

        // Find job
        Job job =
                jobRepository.findById(jobId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job not found with id: "
                                                + jobId
                                )
                        );

        // Check whether job is active
        if (Boolean.FALSE.equals(job.getActive())) {

            throw new RuntimeException(
                    "This job is no longer active"
            );
        }

        // Check whether student already applied
        if (applicationRepository
                .findByStudentIdAndJobId(
                        studentId,
                        jobId
                )
                .isPresent()) {


            throw new DuplicateResourceException(
                    "Student has already applied for this job"
            );
        }

        // Create application
        JobApplication application =
                new JobApplication();

        application.setStudent(student);

        application.setJob(job);

        application.setStatus(
                ApplicationStatus.APPLIED
        );

        // Set timestamps
        LocalDateTime now =
                LocalDateTime.now();

        application.setAppliedAt(now);

        application.setUpdatedAt(now);

        // Calculate student's job match
        JobMatchDTO match =
                jobMatchService.calculateMatch(
                        studentId,
                        jobId
                );

        // Store match percentage at application time
        application.setMatchPercentageAtApplication(
                match.getMatchPercentage()
        );

        // ---------------------------------------------------------
        // Save application first
        // ---------------------------------------------------------

        applicationRepository.save(application);

        // ---------------------------------------------------------
        // Create initial status history
        // ---------------------------------------------------------

        ApplicationStatusHistory history =
                new ApplicationStatusHistory();

        history.setApplication(application);

        history.setStatus(
                ApplicationStatus.APPLIED
        );

        history.setChangedAt(now);

        // Save history
        historyRepository.save(history);

        // Return application
        return application;
    }


    // =========================================================
    // Get student's applications
    // =========================================================

    public List<JobApplication> getStudentApplications(
            Long studentId) {

        return applicationRepository
                .findByStudentId(studentId);
    }


    // =========================================================
    // Get applications for a job
    // =========================================================

    public List<JobApplication> getJobApplications(
            Long jobId) {

        return applicationRepository
                .findByJobId(jobId);
    }


    // =========================================================
    // Get one application
    // =========================================================

    public JobApplication getApplicationById(
            Long id) {

        return applicationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Application not found with id: "
                                        + id
                        )
                );
    }


    // =========================================================
    // Update application status
    // =========================================================

    public JobApplication updateStatus(
            Long applicationId,
            ApplicationStatus status) {

        // Find application
        JobApplication application =
                getApplicationById(applicationId);

        // Update current status
        application.setStatus(status);

        // Update timestamp
        LocalDateTime now =
                LocalDateTime.now();

        application.setUpdatedAt(now);

        // Save updated application
        JobApplication savedApplication =
                applicationRepository.save(
                        application
                );

        // ---------------------------------------------------------
        // Create status history record
        // ---------------------------------------------------------

        ApplicationStatusHistory history =
                new ApplicationStatusHistory();

        history.setApplication(
                savedApplication
        );

        history.setStatus(status);

        history.setChangedAt(now);

        // Save history
        historyRepository.save(history);

// ---------------------------------------------------------
// Create notification for student
// ---------------------------------------------------------

        String title = "Application Status Updated";

        String message =
                "Your application for "
                        + savedApplication.getJob().getTitle()
                        + " at "
                        + savedApplication.getJob().getCompany()
                        + " has been updated to "
                        + status.name()
                        + ".";

        notificationService.createNotification(
                savedApplication.getStudent().getId(),
                title,
                message,
                "APPLICATION_STATUS"
        );

// Return updated application
        return savedApplication;
    }


    // =========================================================
    // Get applications by student and status
    // =========================================================

    public List<JobApplication> getStudentApplicationsByStatus(
            Long studentId,
            ApplicationStatus status) {

        return applicationRepository
                .findByStudentIdAndStatus(
                        studentId,
                        status
                );
    }


    // =========================================================
    // Get applications by job and status
    // =========================================================

    public List<JobApplication> getJobApplicationsByStatus(
            Long jobId,
            ApplicationStatus status) {

        return applicationRepository
                .findByJobIdAndStatus(
                        jobId,
                        status
                );
    }


    // =========================================================
// Withdraw application
// =========================================================

    public void deleteApplication(
            Long applicationId) {

        // Find application
        JobApplication application =
                getApplicationById(applicationId);

        // Update status
        application.setStatus(
                ApplicationStatus.WITHDRAWN
        );

        // Update timestamp
        LocalDateTime now =
                LocalDateTime.now();

        application.setUpdatedAt(now);

        // Save updated application
        JobApplication savedApplication =
                applicationRepository.save(
                        application
                );

        // ---------------------------------------------------------
        // Create status history record
        // ---------------------------------------------------------

        ApplicationStatusHistory history =
                new ApplicationStatusHistory();

        history.setApplication(
                savedApplication
        );

        history.setStatus(
                ApplicationStatus.WITHDRAWN
        );

        history.setChangedAt(now);

        historyRepository.save(history);
    }


    // =========================================================
    // Get application summary for a student
    // =========================================================

    public ApplicationSummaryDTO getApplicationSummary(
            Long studentId) {

        List<JobApplication> applications =
                applicationRepository
                        .findByStudentId(studentId);

        int applied = 0;
        int shortlisted = 0;
        int interview = 0;
        int selected = 0;
        int rejected = 0;

        for (JobApplication application :
                applications) {

            switch (application.getStatus()) {

                case APPLIED:
                    applied++;
                    break;

                case SHORTLISTED:
                    shortlisted++;
                    break;

                case INTERVIEW:
                    interview++;
                    break;

                case SELECTED:
                    selected++;
                    break;

                case REJECTED:
                    rejected++;
                    break;

                case WITHDRAWN:
                    break;
            }
        }

        return new ApplicationSummaryDTO(
                applications.size(),
                applied,
                shortlisted,
                interview,
                selected,
                rejected
        );
    }
    public List<ApplicationStatusHistory>
    getApplicationHistory(Long applicationId) {

        getApplicationById(applicationId);

        return historyRepository
                .findByApplicationIdOrderByChangedAtAsc(
                        applicationId
                );
    }
}