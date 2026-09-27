package com.collage.skillplacementportal.service.student;

import com.collage.skillplacementportal.dto.student.StudentRequestDTO;
import com.collage.skillplacementportal.entity.student.Student;
import com.collage.skillplacementportal.exception.StudentNotFoundException;
import com.collage.skillplacementportal.repository.student.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Create student
    public Student createStudent(StudentRequestDTO dto) {

        Student student = new Student();

        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setRollNumber(dto.getRollNumber());
        student.setDepartment(dto.getDepartment());
        student.setYear(dto.getYear());
        student.setCgpa(dto.getCgpa());

        return studentRepository.save(student);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get student by ID
    public Student getStudentById(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with id: " + id
                        )
                );
    }

    // Update student
    public Student updateStudent(Long id, StudentRequestDTO dto) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with id: " + id
                        )
                );

        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setRollNumber(dto.getRollNumber());
        student.setDepartment(dto.getDepartment());
        student.setYear(dto.getYear());
        student.setCgpa(dto.getCgpa());

        return studentRepository.save(student);
    }

    // Delete student
    public void deleteStudent(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with id: " + id
                        )
                );

        studentRepository.delete(student);
    }
}