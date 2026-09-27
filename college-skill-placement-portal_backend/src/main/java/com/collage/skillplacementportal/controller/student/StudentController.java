package com.collage.skillplacementportal.controller.student;

import com.collage.skillplacementportal.dto.student.StudentRequestDTO;
import com.collage.skillplacementportal.entity.student.Student;
import com.collage.skillplacementportal.repository.student.StudentRepository;
import com.collage.skillplacementportal.service.student.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService,
                             StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }
    @PostMapping
    public Student createStudent(@Valid @RequestBody StudentRequestDTO dto) {
        return studentService.createStudent(dto);
    }
    // Get all students

    @GetMapping
    public ResponseEntity<Page<Student>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Student> students =
                studentRepository.findAll(pageable);

        return ResponseEntity.ok(students);
    }

    // Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {

        Student student = studentService.getStudentById(id);

        return ResponseEntity.ok(student);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
           @Valid @RequestBody StudentRequestDTO dto) {

        try {
            return ResponseEntity.ok(
                    studentService.updateStudent(id, dto)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(
            @RequestParam String keyword) {

        List<Student> students =
                studentRepository.findByNameContainingIgnoreCase(keyword);

        students.addAll(
                studentRepository.findByEmailContainingIgnoreCase(keyword)
        );

        students.addAll(
                studentRepository.findByRollNumberContainingIgnoreCase(keyword)
        );

        return ResponseEntity.ok(students);
    }

}
