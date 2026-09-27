package com.collage.skillplacementportal.repository.job;

import com.collage.skillplacementportal.entity.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository
        extends JpaRepository<Job, Long> {

    List<Job> findByActiveTrue();

    List<Job> findByCompanyIgnoreCase(
            String company
    );

    List<Job> findByLocationIgnoreCase(
            String location
    );

    List<Job> findByJobTypeIgnoreCase(
            String jobType
    );
    List<Job> findByTitleContainingIgnoreCase(String title);

    List<Job> findByCompanyContainingIgnoreCase(String company);
}
