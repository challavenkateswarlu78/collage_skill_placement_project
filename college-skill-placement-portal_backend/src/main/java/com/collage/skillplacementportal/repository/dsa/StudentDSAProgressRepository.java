package com.collage.skillplacementportal.repository.dsa;

import com.collage.skillplacementportal.entity.dsa.StudentDSAProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentDSAProgressRepository
        extends JpaRepository<StudentDSAProgress, Long> {

    Optional<StudentDSAProgress> findByStudentIdAndProblemId(
            Long studentId,
            Long problemId
    );

    List<StudentDSAProgress> findByStudentId(
            Long studentId
    );

    List<StudentDSAProgress> findByStudentIdAndSolvedTrue(
            Long studentId
    );

    List<StudentDSAProgress> findByProblemId(
            Long problemId
    );
}
