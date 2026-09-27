package com.collage.skillplacementportal.service.skill;

import com.collage.skillplacementportal.dto.skill.SkillGapDTO;
import com.collage.skillplacementportal.entity.skill.Skill;
import com.collage.skillplacementportal.entity.skill.StudentSkill;
import com.collage.skillplacementportal.repository.skill.StudentSkillRepository;
import com.collage.skillplacementportal.repository.student.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillGapService {

    private final StudentRepository studentRepository;
    private final StudentSkillRepository studentSkillRepository;

    public SkillGapService(
            StudentRepository studentRepository,
            StudentSkillRepository studentSkillRepository) {

        this.studentRepository = studentRepository;
        this.studentSkillRepository = studentSkillRepository;
    }

    public List<SkillGapDTO> getSkillGaps(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException(
                    "Student not found with id: " + studentId
            );
        }

        List<StudentSkill> studentSkills =
                studentSkillRepository.findByStudentId(
                        studentId
                );

        List<SkillGapDTO> result =
                new ArrayList<>();

        for (StudentSkill studentSkill : studentSkills) {

            Skill skill = studentSkill.getSkill();

            double level =
                    studentSkill.getSkillLevel();

            String status;
            String recommendation;

            if (level >= 80) {

                status = "STRONG";

                recommendation =
                        "Continue advanced "
                                + skill.getName()
                                + " practice";

            } else if (level >= 60) {

                status = "GOOD";

                recommendation =
                        "Practice advanced "
                                + skill.getName()
                                + " concepts";

            } else if (level >= 40) {

                status = "NEEDS_IMPROVEMENT";

                recommendation =
                        "Practice "
                                + skill.getName()
                                + " regularly";

            } else {

                status = "WEAK";

                recommendation =
                        "Start with "
                                + skill.getName()
                                + " fundamentals";
            }

            result.add(
                    new SkillGapDTO(
                            skill.getId(),
                            skill.getName(),
                            level,
                            status,
                            recommendation
                    )
            );
        }

        return result;
    }
    public List<SkillGapDTO> getWeakSkills(Long studentId) {

        List<SkillGapDTO> allSkills =
                getSkillGaps(studentId);

        List<SkillGapDTO> weakSkills =
                new ArrayList<>();

        for (SkillGapDTO skill : allSkills) {

            if (skill.getCurrentLevel() < 60) {
                weakSkills.add(skill);
            }
        }

        return weakSkills;
    }
}