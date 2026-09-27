package com.collage.skillplacementportal.repository.skill;

import com.collage.skillplacementportal.entity.skill.StudentSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentSkillRepository extends JpaRepository<StudentSkill,Long> {

    List<StudentSkill> findByStudentId(Long studentId);

    List<StudentSkill> findBySkillId(Long skillId);

    Optional<StudentSkill> findByStudentIdAndSkillId(Long studentId, Long skillId);


}
