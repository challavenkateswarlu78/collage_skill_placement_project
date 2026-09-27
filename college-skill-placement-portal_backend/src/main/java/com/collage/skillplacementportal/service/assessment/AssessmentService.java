package com.collage.skillplacementportal.service.assessment;

import com.collage.skillplacementportal.entity.assessment.Assessment;
import com.collage.skillplacementportal.entity.skill.Skill;
import com.collage.skillplacementportal.repository.assessment.AssessmentRepository;
import com.collage.skillplacementportal.repository.skill.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final SkillRepository skillRepository;

    public AssessmentService(
            AssessmentRepository assessmentRepository,
            SkillRepository skillRepository) {

        this.assessmentRepository = assessmentRepository;
        this.skillRepository = skillRepository;
    }

    // Create assessment
    public Assessment createAssessment(
            Assessment assessment,
            Long skillId) {

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Skill not found with id: " + skillId
                        )
                );

        assessment.setSkill(skill);

        return assessmentRepository.save(assessment);
    }

    // Get all assessments
    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    // Get assessment by ID
    public Assessment getAssessmentById(Long id) {

        return assessmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Assessment not found with id: " + id
                        )
                );
    }

    // Update assessment
    public Assessment updateAssessment(
            Long id,
            Assessment assessmentDetails,
            Long skillId) {

        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Assessment not found with id: " + id
                        )
                );

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Skill not found with id: " + skillId
                        )
                );

        assessment.setTitle(assessmentDetails.getTitle());
        assessment.setDescription(assessmentDetails.getDescription());
        assessment.setTotalMarks(assessmentDetails.getTotalMarks());
        assessment.setSkill(skill);

        return assessmentRepository.save(assessment);
    }

    // Delete assessment
    public void deleteAssessment(Long id) {

        if (!assessmentRepository.existsById(id)) {
            throw new RuntimeException(
                    "Assessment not found with id: " + id
            );
        }

        assessmentRepository.deleteById(id);
    }
}