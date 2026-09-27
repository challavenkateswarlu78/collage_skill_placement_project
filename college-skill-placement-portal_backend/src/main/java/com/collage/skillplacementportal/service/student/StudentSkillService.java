package com.collage.skillplacementportal.service.student;

import com.collage.skillplacementportal.entity.skill.Skill;
import com.collage.skillplacementportal.entity.student.Student;
import com.collage.skillplacementportal.entity.skill.StudentSkill;
import com.collage.skillplacementportal.exception.ResourceNotFoundException;
import com.collage.skillplacementportal.repository.skill.SkillRepository;
import com.collage.skillplacementportal.repository.student.StudentRepository;
import com.collage.skillplacementportal.repository.skill.StudentSkillRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentSkillService {

    private final StudentSkillRepository studentSkillRepository;
    private final StudentRepository studentRepository;
    private final SkillRepository skillRepository;

    public StudentSkillService(
            StudentSkillRepository studentSkillRepository,
            StudentRepository studentRepository,
            SkillRepository skillRepository) {

        this.studentSkillRepository = studentSkillRepository;
        this.studentRepository = studentRepository;
        this.skillRepository = skillRepository;
    }

    // Add or update a skill for a student
    public StudentSkill addOrUpdateSkill(
            Long studentId,
            Long skillId,
            Double skillLevel) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found with id: " + studentId
                        )
                );

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Skill not found with id: " + skillId
                        )
                );

        StudentSkill studentSkill =
                studentSkillRepository
                        .findByStudentIdAndSkillId(studentId, skillId)
                        .orElse(new StudentSkill());

        studentSkill.setStudent(student);
        studentSkill.setSkill(skill);
        studentSkill.setSkillLevel(skillLevel);

        return studentSkillRepository.save(studentSkill);
    }

    // Get all skills of a student
    public List<StudentSkill> getStudentSkills(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException(
                    "Student not found with id: " + studentId
            );
        }

        return studentSkillRepository.findByStudentId(studentId);
    }

    // Delete a student's skill
    public void deleteStudentSkill(
            Long studentSkillId,
            Long currentStudentId) {

        StudentSkill studentSkill =
                studentSkillRepository
                        .findById(studentSkillId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student skill not found with id: "
                                                + studentSkillId
                                )
                        );

        if (!studentSkill
                .getStudent()
                .getId()
                .equals(currentStudentId)) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot delete another student's skill"
            );
        }

        studentSkillRepository.delete(
                studentSkill
        );
    }
    public StudentSkill updateSkillFromAssessment(
            Long studentId,
            Long skillId,
            double assessmentPercentage) {

        StudentSkill studentSkill =
                studentSkillRepository
                        .findByStudentIdAndSkillId(
                                studentId,
                                skillId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student skill not found"
                                )
                        );

        double oldSkillLevel = studentSkill.getSkillLevel();

        double newSkillLevel =
                (oldSkillLevel * 0.40)
                        + (assessmentPercentage * 0.60);

        studentSkill.setSkillLevel(newSkillLevel);

        return studentSkillRepository.save(studentSkill);
    }
}