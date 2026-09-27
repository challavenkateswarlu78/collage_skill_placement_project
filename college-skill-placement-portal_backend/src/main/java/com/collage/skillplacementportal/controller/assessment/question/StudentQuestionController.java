package com.collage.skillplacementportal.controller.assessment.question;

import com.collage.skillplacementportal.dto.assessment.question.StudentQuestionDTO;
import com.collage.skillplacementportal.service.assessment.question.StudentQuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/questions")
public class StudentQuestionController {

    private final StudentQuestionService studentQuestionService;

    public StudentQuestionController(
            StudentQuestionService studentQuestionService) {

        this.studentQuestionService = studentQuestionService;
    }

    @GetMapping("/assessment/{assessmentId}")
    public List<StudentQuestionDTO> getQuestionsForStudent(
            @PathVariable Long assessmentId) {

        return studentQuestionService
                .getQuestionsForStudent(assessmentId);
    }
}