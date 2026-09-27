package com.collage.skillplacementportal.service.assessment.question;

import com.collage.skillplacementportal.dto.assessment.question.StudentQuestionDTO;
import com.collage.skillplacementportal.entity.assessment.question.CodingQuestion;
import com.collage.skillplacementportal.entity.assessment.question.MCQQuestion;
import com.collage.skillplacementportal.entity.assessment.question.Question;
import com.collage.skillplacementportal.repository.assessment.CodingQuestionRepository;
import com.collage.skillplacementportal.repository.assessment.MCQQuestionRepository;
import com.collage.skillplacementportal.repository.assessment.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentQuestionService {

    private final QuestionRepository questionRepository;
    private final MCQQuestionRepository mcqQuestionRepository;
    private final CodingQuestionRepository codingQuestionRepository;

    public StudentQuestionService(
            QuestionRepository questionRepository,
            MCQQuestionRepository mcqQuestionRepository,
            CodingQuestionRepository codingQuestionRepository) {

        this.questionRepository = questionRepository;
        this.mcqQuestionRepository = mcqQuestionRepository;
        this.codingQuestionRepository = codingQuestionRepository;
    }

    // Get questions for student
    public List<StudentQuestionDTO> getQuestionsForStudent(
            Long assessmentId) {

        // Get all questions belonging to this assessment
        List<Question> questions =
                questionRepository.findByAssessmentId(
                        assessmentId
                );

        // List that will contain student-safe question data
        List<StudentQuestionDTO> result =
                new ArrayList<>();

        // Process every question
        for (Question question : questions) {

            StudentQuestionDTO dto =
                    new StudentQuestionDTO();

            // Common question details
            dto.setQuestionId(question.getId());

            dto.setQuestionText(
                    question.getQuestionText()
            );

            dto.setQuestionType(
                    question.getQuestionType().name()
            );

            dto.setMarks(question.getMarks());

            dto.setDifficulty(
                    question.getDifficulty()
            );


            // =====================================================
            // MCQ QUESTION
            // =====================================================

            if (question.getQuestionType()
                    .name()
                    .equals("MCQ")) {

                MCQQuestion mcq =
                        mcqQuestionRepository
                                .findByQuestionId(
                                        question.getId()
                                )
                                .orElse(null);

                if (mcq != null) {

                    dto.setOptionA(
                            mcq.getOptionA()
                    );

                    dto.setOptionB(
                            mcq.getOptionB()
                    );

                    dto.setOptionC(
                            mcq.getOptionC()
                    );

                    dto.setOptionD(
                            mcq.getOptionD()
                    );
                }
            }


            // =====================================================
            // CODING QUESTION
            // =====================================================

            if (question.getQuestionType()
                    .name()
                    .equals("CODING")) {

                CodingQuestion coding =
                        codingQuestionRepository
                                .findByQuestionId(
                                        question.getId()
                                )
                                .orElse(null);

                if (coding != null) {

                    dto.setProblemTitle(
                            coding.getProblemTitle()
                    );

                    dto.setProblemDescription(
                            coding.getProblemDescription()
                    );

                    dto.setConstraints(
                            coding.getConstraints()
                    );

                    dto.setInputFormat(
                            coding.getInputFormat()
                    );

                    dto.setOutputFormat(
                            coding.getOutputFormat()
                    );

                    dto.setPlatform(
                            coding.getPlatform()
                    );

                    dto.setExternalUrl(
                            coding.getExternalUrl()
                    );
                }
            }


            // Add completed DTO to result
            result.add(dto);
        }

        return result;
    }
}