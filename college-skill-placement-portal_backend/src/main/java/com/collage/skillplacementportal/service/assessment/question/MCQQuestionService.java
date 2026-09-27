package com.collage.skillplacementportal.service.assessment.question;

import com.collage.skillplacementportal.entity.assessment.question.MCQQuestion;
import com.collage.skillplacementportal.entity.assessment.question.Question;
import com.collage.skillplacementportal.repository.assessment.MCQQuestionRepository;
import com.collage.skillplacementportal.repository.assessment.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MCQQuestionService {

    private final MCQQuestionRepository mcqQuestionRepository;
    private final QuestionRepository questionRepository;

    public MCQQuestionService(
            MCQQuestionRepository mcqQuestionRepository,
            QuestionRepository questionRepository) {

        this.mcqQuestionRepository = mcqQuestionRepository;
        this.questionRepository = questionRepository;
    }

    // Create MCQ details for an existing question
    public MCQQuestion createMCQ(
            MCQQuestion mcqQuestion,
            Long questionId) {

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question not found with id: " + questionId
                        )
                );

        if (mcqQuestionRepository
                .findByQuestionId(questionId)
                .isPresent()) {

            throw new RuntimeException(
                    "MCQ details already exist for this question"
            );
        }

        mcqQuestion.setQuestion(question);

        return mcqQuestionRepository.save(mcqQuestion);
    }

    // Get MCQ by ID
    public MCQQuestion getMCQById(Long id) {

        return mcqQuestionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "MCQ not found with id: " + id
                        )
                );
    }

    // Get MCQ by Question ID
    public MCQQuestion getMCQByQuestionId(Long questionId) {

        return mcqQuestionRepository
                .findByQuestionId(questionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "MCQ details not found for question id: "
                                        + questionId
                        )
                );
    }

    // Get all MCQs
    public List<MCQQuestion> getAllMCQs() {
        return mcqQuestionRepository.findAll();
    }

    // Update MCQ
    public MCQQuestion updateMCQ(
            Long id,
            MCQQuestion mcqDetails) {

        MCQQuestion mcqQuestion = mcqQuestionRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "MCQ not found with id: " + id
                        )
                );

        mcqQuestion.setOptionA(mcqDetails.getOptionA());
        mcqQuestion.setOptionB(mcqDetails.getOptionB());
        mcqQuestion.setOptionC(mcqDetails.getOptionC());
        mcqQuestion.setOptionD(mcqDetails.getOptionD());
        mcqQuestion.setCorrectAnswer(
                mcqDetails.getCorrectAnswer()
        );

        return mcqQuestionRepository.save(mcqQuestion);
    }

    // Delete MCQ
    public void deleteMCQ(Long id) {

        if (!mcqQuestionRepository.existsById(id)) {
            throw new RuntimeException(
                    "MCQ not found with id: " + id
            );
        }

        mcqQuestionRepository.deleteById(id);
    }
}