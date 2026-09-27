package com.collage.skillplacementportal.service.assessment.attempt;

import com.collage.skillplacementportal.dto.assessment.AssessmentResultDTO;
import com.collage.skillplacementportal.dto.assessment.AssessmentSummaryDTO;
import com.collage.skillplacementportal.entity.assessment.Assessment;
import com.collage.skillplacementportal.entity.assessment.attempt.AssessmentAttempt;
import com.collage.skillplacementportal.entity.assessment.question.Question;
import com.collage.skillplacementportal.entity.student.Student;
import com.collage.skillplacementportal.entity.assessment.attempt.StudentAnswer;
import com.collage.skillplacementportal.repository.assessment.AssessmentAttemptRepository;
import com.collage.skillplacementportal.repository.assessment.AssessmentRepository;
import com.collage.skillplacementportal.repository.assessment.QuestionRepository;
import com.collage.skillplacementportal.repository.assessment.StudentAnswerRepository;
import com.collage.skillplacementportal.repository.student.StudentRepository;

import com.collage.skillplacementportal.service.student.StudentSkillService;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import com.collage.skillplacementportal.service.notification.NotificationService;

@Service
public class AssessmentAttemptService {

    private final AssessmentAttemptRepository attemptRepository;
    private final StudentRepository studentRepository;
    private final AssessmentRepository assessmentRepository;
    private final StudentAnswerRepository studentAnswerRepository;
    private final StudentSkillService studentSkillService;
    private final QuestionRepository questionRepository;
    private final NotificationService notificationService;

    public AssessmentAttemptService(AssessmentAttemptRepository attemptRepository,
                                    StudentRepository studentRepository,
                                    AssessmentRepository assessmentRepository,
                                    StudentAnswerRepository studentAnswerRepository,
                                    StudentSkillService studentSkillService,
                                    QuestionRepository questionRepository,
                                    NotificationService notificationService) {
        this.attemptRepository = attemptRepository;
        this.studentRepository = studentRepository;
        this.assessmentRepository = assessmentRepository;
        this.studentAnswerRepository = studentAnswerRepository;
        this.studentSkillService = studentSkillService;
        this.questionRepository = questionRepository;
        this.notificationService = notificationService;
    }

    // Start assessment
    public AssessmentAttempt startAssessment(
            Long studentId,
            Long assessmentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with id: " + studentId
                        )
                );

        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Assessment not found with id: " + assessmentId
                        )
                );

        // Check whether student already has an active attempt
        if (attemptRepository
                .findByStudentIdAndAssessmentIdAndStatus(
                        studentId,
                        assessmentId,
                        "IN_PROGRESS"
                )
                .isPresent()) {

            throw new RuntimeException(
                    "Student already has an active attempt"
            );
        }

        AssessmentAttempt attempt = new AssessmentAttempt();

        attempt.setStudent(student);
        attempt.setAssessment(assessment);
        attempt.setStartedAt(LocalDateTime.now());
        attempt.setStatus("IN_PROGRESS");
        attempt.setTotalMarks(assessment.getTotalMarks());
        attempt.setScore(0);

        return attemptRepository.save(attempt);
    }

    // Get attempt by ID
    public AssessmentAttempt getAttemptById(Long id) {

        return attemptRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Assessment attempt not found with id: "
                                        + id
                        )
                );
    }

    // Get all attempts of a student
    public List<AssessmentAttempt> getStudentAttempts(
            Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException(
                    "Student not found with id: " + studentId
            );
        }

        return attemptRepository.findByStudentId(studentId);
    }

    // Get all attempts of an assessment
    public List<AssessmentAttempt> getAssessmentAttempts(
            Long assessmentId) {

        if (!assessmentRepository.existsById(assessmentId)) {
            throw new RuntimeException(
                    "Assessment not found with id: " + assessmentId
            );
        }

        return attemptRepository.findByAssessmentId(assessmentId);
    }

    // Submit assessment and calculate final score
    public AssessmentAttempt submitAssessment(Long attemptId) {

        AssessmentAttempt attempt = attemptRepository
                .findById(attemptId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Assessment attempt not found with id: "
                                        + attemptId
                        )
                );

        if (!"IN_PROGRESS".equals(attempt.getStatus())) {
            throw new RuntimeException(
                    "Assessment has already been submitted"
            );
        }

        List<Question> questions =
                questionRepository.findByAssessmentId(
                        attempt.getAssessment().getId()
                );

        List<StudentAnswer> answers =
                studentAnswerRepository.findByAttemptId(attemptId);

        if (answers.size() < questions.size()) {

            throw new RuntimeException(
                    "Please answer all questions before submitting the assessment"
            );
        }

        double totalScore = 0;

        for (StudentAnswer answer : answers) {

            if (answer.getMarksObtained() != null) {
                totalScore += answer.getMarksObtained();
            }
        }

        if (attempt.getTotalMarks() != null
                && totalScore > attempt.getTotalMarks()) {

            totalScore = attempt.getTotalMarks();
        }

        attempt.setScore((int) totalScore);

        double percentage = 0;

        if (attempt.getTotalMarks() != null
                && attempt.getTotalMarks() > 0) {

            percentage =
                    (totalScore / attempt.getTotalMarks()) * 100;
        }

        attempt.setSubmittedAt(LocalDateTime.now());
        attempt.setStatus("SUBMITTED");

        Assessment assessment = attempt.getAssessment();

        Long skillId = assessment.getSkill().getId();
        Long studentId = attempt.getStudent().getId();

        studentSkillService.updateSkillFromAssessment(
                studentId,
                skillId,
                percentage
        );

        AssessmentAttempt savedAttempt =
                attemptRepository.save(attempt);

// ---------------------------------------------------------
// Create assessment completion notification
// ---------------------------------------------------------

        String title = "Assessment Completed";

        String message =
                "Your assessment "
                        + assessment.getTitle()
                        + " has been completed successfully. "
                        + "Your score is "
                        + percentage
                        + "%.";

        notificationService.createNotification(
                studentId,
                title,
                message,
                "ASSESSMENT_COMPLETED"
        );

        return savedAttempt;
    }
    public AssessmentResultDTO getAssessmentResult(Long attemptId) {

        AssessmentAttempt attempt =
                attemptRepository.findById(attemptId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Assessment attempt not found with id: "
                                                + attemptId
                                )
                        );
        if (!"SUBMITTED".equals(attempt.getStatus())) {

            throw new RuntimeException(
                    "Assessment has not been submitted yet"
            );
        }

        double percentage = 0;

        if (attempt.getTotalMarks() != null
                && attempt.getTotalMarks() > 0
                && attempt.getScore() != null) {

            percentage =
                    ((double) attempt.getScore()
                            / attempt.getTotalMarks()) * 100;
        }

        return new AssessmentResultDTO(
                attempt.getId(),
                attempt.getAssessment().getId(),
                attempt.getStatus(),
                attempt.getScore(),
                attempt.getTotalMarks(),
                percentage,
                attempt.getSubmittedAt()
        );
    }
    public AssessmentSummaryDTO getAssessmentSummary(
            Long studentId) {

        if (!studentRepository.existsById(studentId)) {

            throw new RuntimeException(
                    "Student not found with id: " + studentId
            );
        }

        List<AssessmentAttempt> attempts =
                attemptRepository.findByStudentId(studentId);

        int totalAssessmentsTaken = attempts.size();

        int completedAssessments = 0;

        double totalPercentage = 0;

        int completedCount = 0;

        for (AssessmentAttempt attempt : attempts) {

            if ("SUBMITTED".equals(attempt.getStatus())) {

                completedAssessments++;

                if (attempt.getScore() != null
                        && attempt.getTotalMarks() != null
                        && attempt.getTotalMarks() > 0) {

                    double percentage =
                            ((double) attempt.getScore()
                                    / attempt.getTotalMarks()) * 100;

                    totalPercentage += percentage;

                    completedCount++;
                }
            }
        }

        double averageScore = 0;

        if (completedCount > 0) {

            averageScore =
                    totalPercentage / completedCount;
        }

        return new AssessmentSummaryDTO(
                totalAssessmentsTaken,
                completedAssessments,
                averageScore
        );
    }
}