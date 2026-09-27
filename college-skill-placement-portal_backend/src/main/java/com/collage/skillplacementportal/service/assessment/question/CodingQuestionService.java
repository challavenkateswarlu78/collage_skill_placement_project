package com.collage.skillplacementportal.service.assessment.question;

import com.collage.skillplacementportal.entity.assessment.question.CodingQuestion;
import com.collage.skillplacementportal.entity.assessment.question.Question;
import com.collage.skillplacementportal.repository.assessment.CodingQuestionRepository;
import com.collage.skillplacementportal.repository.assessment.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodingQuestionService {

    private final CodingQuestionRepository codingQuestionRepository;
    private final QuestionRepository questionRepository;

    public CodingQuestionService(
            CodingQuestionRepository codingQuestionRepository,
            QuestionRepository questionRepository) {

        this.codingQuestionRepository = codingQuestionRepository;
        this.questionRepository = questionRepository;
    }

    // Create coding question details
    public CodingQuestion createCodingQuestion(
            CodingQuestion codingQuestion,
            Long questionId) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question not found with id: " + questionId
                        )
                );

        if (codingQuestionRepository
                .findByQuestionId(questionId)
                .isPresent()) {

            throw new RuntimeException(
                    "Coding details already exist for this question"
            );
        }

        codingQuestion.setQuestion(question);

        return codingQuestionRepository.save(codingQuestion);
    }

    // Get all coding questions
    public List<CodingQuestion> getAllCodingQuestions() {
        return codingQuestionRepository.findAll();
    }

    // Get coding question by ID
    public CodingQuestion getCodingQuestionById(Long id) {

        return codingQuestionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Coding question not found with id: " + id
                        )
                );
    }

    // Get coding details by Question ID
    public CodingQuestion getByQuestionId(Long questionId) {

        return codingQuestionRepository
                .findByQuestionId(questionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Coding details not found for question id: "
                                        + questionId
                        )
                );
    }

    // Update coding question
    public CodingQuestion updateCodingQuestion(
            Long id,
            CodingQuestion details) {

        CodingQuestion codingQuestion =
                codingQuestionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Coding question not found with id: "
                                                + id
                                )
                        );

        codingQuestion.setProblemTitle(
                details.getProblemTitle()
        );

        codingQuestion.setProblemDescription(
                details.getProblemDescription()
        );

        codingQuestion.setConstraints(
                details.getConstraints()
        );

        codingQuestion.setInputFormat(
                details.getInputFormat()
        );

        codingQuestion.setOutputFormat(
                details.getOutputFormat()
        );

        codingQuestion.setPlatform(
                details.getPlatform()
        );

        codingQuestion.setExternalUrl(
                details.getExternalUrl()
        );

        return codingQuestionRepository.save(codingQuestion);
    }

    // Delete coding question
    public void deleteCodingQuestion(Long id) {

        if (!codingQuestionRepository.existsById(id)) {
            throw new RuntimeException(
                    "Coding question not found with id: " + id
            );
        }

        codingQuestionRepository.deleteById(id);
    }
}