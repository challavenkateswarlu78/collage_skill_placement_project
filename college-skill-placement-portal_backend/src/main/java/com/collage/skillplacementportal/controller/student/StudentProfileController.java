package com.collage.skillplacementportal.controller.student;

import com.collage.skillplacementportal.entity.student.Student;
import com.collage.skillplacementportal.repository.student.StudentRepository;
import com.collage.skillplacementportal.service.security.AuthorizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentProfileController {

    private final StudentRepository studentRepository;
    private final AuthorizationService authorizationService;

    public StudentProfileController(
            StudentRepository studentRepository,
            AuthorizationService authorizationService) {

        this.studentRepository = studentRepository;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/me")
    public Student getMyProfile() {

        Long studentId =
                authorizationService.getCurrentStudentId();

        return studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student profile not found"
                        )
                );
    }
}