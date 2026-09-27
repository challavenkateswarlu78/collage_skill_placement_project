package com.collage.skillplacementportal.service.assessment.attempt;

import com.collage.skillplacementportal.entity.assessment.attempt.AssessmentAttempt;
import com.collage.skillplacementportal.entity.assessment.attempt.StudentAnswer;
import com.collage.skillplacementportal.entity.assessment.question.MCQQuestion;
import com.collage.skillplacementportal.entity.assessment.question.Question;
import com.collage.skillplacementportal.entity.assessment.question.QuestionType;
import com.collage.skillplacementportal.repository.assessment.AssessmentAttemptRepository;
import com.collage.skillplacementportal.repository.assessment.MCQQuestionRepository;
import com.collage.skillplacementportal.repository.assessment.QuestionRepository;
import com.collage.skillplacementportal.repository.assessment.StudentAnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentAnswerService {

    private final StudentAnswerRepository studentAnswerRepository;
    private final AssessmentAttemptRepository attemptRepository;
    private final QuestionRepository questionRepository;
    private final MCQQuestionRepository mcqQuestionRepository;

    public StudentAnswerService(
            StudentAnswerRepository studentAnswerRepository,
            AssessmentAttemptRepository attemptRepository,
            QuestionRepository questionRepository,
            MCQQuestionRepository mcqQuestionRepository) {

        this.studentAnswerRepository = studentAnswerRepository;
        this.attemptRepository = attemptRepository;
        this.questionRepository = questionRepository;
        this.mcqQuestionRepository = mcqQuestionRepository;
    }

    // Submit an answer
    public StudentAnswer submitAnswer(
            Long attemptId,
            Long questionId,
            String selectedAnswer) {

        if (selectedAnswer == null
                || selectedAnswer.trim().isEmpty()) {

            throw new RuntimeException(
                    "Answer cannot be empty"
            );
        }

        AssessmentAttempt attempt = attemptRepository
                .findById(attemptId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Assessment attempt not found with id: "
                                        + attemptId
                        )
                );

        Question question = questionRepository
                .findById(questionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question not found with id: "
                                        + questionId
                        )
                );
        if (!question.getAssessment().getId()
                .equals(attempt.getAssessment().getId())) {

            throw new RuntimeException(
                    "Question does not belong to this assessment"
            );
        }

        if (!"IN_PROGRESS".equals(attempt.getStatus())) {
            throw new RuntimeException(
                    "Assessment attempt is already submitted"
            );
        }

        if (studentAnswerRepository
                .findByAttemptIdAndQuestionId(
                        attemptId,
                        questionId
                )
                .isPresent()) {

            throw new RuntimeException(
                    "Answer has already been submitted for this question"
            );
        }

        StudentAnswer studentAnswer =
                new StudentAnswer();

        studentAnswer.setAttempt(attempt);
        studentAnswer.setQuestion(question);
        studentAnswer.setSelectedAnswer(selectedAnswer);

        // MCQ evaluation
        if (question.getQuestionType() == QuestionType.MCQ) {

            MCQQuestion mcqQuestion =
                    mcqQuestionRepository
                            .findByQuestionId(questionId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "MCQ details not found"
                                    )
                            );

            boolean correct =
                    mcqQuestion
                            .getCorrectAnswer()
                            .equalsIgnoreCase(selectedAnswer);

            studentAnswer.setCorrect(correct);

            if (correct) {
                studentAnswer.setMarksObtained(
                        question.getMarks()
                );
            } else {
                studentAnswer.setMarksObtained(0);
            }

        } else {
            // Coding questions are handled separately later
            studentAnswer.setCorrect(null);
            studentAnswer.setMarksObtained(0);
        }

        return studentAnswerRepository.save(studentAnswer);
    }

    // Get all answers for an attempt
    public List<StudentAnswer> getAttemptAnswers(
            Long attemptId) {

        if (!attemptRepository.existsById(attemptId)) {
            throw new RuntimeException(
                    "Assessment attempt not found with id: "
                            + attemptId
            );
        }

        return studentAnswerRepository.findByAttemptId(
                attemptId
        );
    }

    // Get one answer
    public StudentAnswer getAnswerById(Long id) {

        return studentAnswerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student answer not found with id: "
                                        + id
                        )
                );
    }
    public StudentAnswer markCodingCompleted(
            Long attemptId,
            Long questionId) {

        AssessmentAttempt attempt =
                attemptRepository.findById(attemptId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Assessment attempt not found"
                                )
                        );

        Question question =
                questionRepository.findById(questionId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Question not found"
                                )
                        );

        if (!"IN_PROGRESS".equals(attempt.getStatus())) {
            throw new RuntimeException(
                    "Assessment attempt is already submitted"
            );
        }

        if (question.getQuestionType() != QuestionType.CODING) {
            throw new RuntimeException(
                    "This is not a coding question"
            );
        }

        StudentAnswer answer =
                studentAnswerRepository
                        .findByAttemptIdAndQuestionId(
                                attemptId,
                                questionId
                        )
                        .orElse(new StudentAnswer());

        answer.setAttempt(attempt);
        answer.setQuestion(question);
        answer.setCodingCompleted(true);
        answer.setCorrect(true);
        answer.setMarksObtained(question.getMarks());

        return studentAnswerRepository.save(answer);
    }
}
