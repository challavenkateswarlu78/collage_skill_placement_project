package com.collage.skillplacementportal.controller.skill;

import com.collage.skillplacementportal.entity.skill.StudentSkill;
import com.collage.skillplacementportal.service.security.AuthorizationService;
import com.collage.skillplacementportal.service.student.StudentSkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentSkillController {

    private final StudentSkillService studentSkillService;
    private final AuthorizationService authorizationService;
    public StudentSkillController(StudentSkillService studentSkillService,
                                  AuthorizationService authorizationService) {
        this.studentSkillService = studentSkillService;
        this.authorizationService = authorizationService;
    }

    // Add or update a skill for a student
    @PostMapping("/{studentId}/skills")
    public StudentSkill addOrUpdateSkill(
            @PathVariable Long studentId,
            @RequestParam Long skillId,
            @RequestParam Double skillLevel) {

        if (!authorizationService
                .isStudentOwner(studentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot modify another student's skills"
            );
        }

        return studentSkillService.addOrUpdateSkill(
                studentId,
                skillId,
                skillLevel
        );
    }

    // Get all skills of a student
    @GetMapping("/{studentId}/skills")
    public List<StudentSkill> getStudentSkills(
            @PathVariable Long studentId) {

        if (!authorizationService
                .isStudentOwner(studentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another student's skills"
            );
        }

        return studentSkillService
                .getStudentSkills(studentId);
    }

    // Delete a student skill
    @DeleteMapping("/skills/{studentSkillId}")
    public ResponseEntity<Void> deleteStudentSkill(
            @PathVariable Long studentSkillId) {

        Long currentStudentId =
                authorizationService
                        .getCurrentUser()
                        .getStudentId();

        studentSkillService.deleteStudentSkill(
                studentSkillId,
                currentStudentId
        );

        return ResponseEntity.noContent().build();
    }
    @PutMapping(
            "/student/{studentId}/skill/{skillId}/assessment"
    )
    public ResponseEntity<StudentSkill> updateFromAssessment(
            @PathVariable Long studentId,
            @PathVariable Long skillId,
            @RequestParam int assessmentPercentage) {

        if (!authorizationService
                .isStudentOwner(studentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot modify another student's skill"
            );
        }

        try {

            return ResponseEntity.ok(
                    studentSkillService
                            .updateSkillFromAssessment(
                                    studentId,
                                    skillId,
                                    assessmentPercentage
                            )
            );

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }
}
