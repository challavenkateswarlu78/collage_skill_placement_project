package com.collage.skillplacementportal.repository.student;

import com.collage.skillplacementportal.entity.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student ,Long> {
    List<Student> findByNameContainingIgnoreCase(String name);

    List<Student> findByEmailContainingIgnoreCase(String email);

    List<Student> findByRollNumberContainingIgnoreCase(String rollNumber);
}
