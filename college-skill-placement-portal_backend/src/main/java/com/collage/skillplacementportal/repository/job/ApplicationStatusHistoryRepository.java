package com.collage.skillplacementportal.repository.job;

import com.collage.skillplacementportal.entity.job.ApplicationStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationStatusHistoryRepository
        extends JpaRepository<
        ApplicationStatusHistory,
        Long> {

    List<ApplicationStatusHistory>
    findByApplicationIdOrderByChangedAtAsc(
            Long applicationId
    );
}