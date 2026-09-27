package com.collage.skillplacementportal.repository.dsa;

import com.collage.skillplacementportal.entity.dsa.DSAProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DSAProblemRepository
        extends JpaRepository<DSAProblem, Long> {

    List<DSAProblem> findByPlatform(String platform);

    List<DSAProblem> findByTopic(String topic);

    List<DSAProblem> findByDifficulty(String difficulty);

    List<DSAProblem> findByDailyProblemTrue();

    List<DSAProblem> findByPlatformAndDifficulty(
            String platform,
            String difficulty
    );
    List<DSAProblem> findByDailyDate(LocalDate dailyDate);
}