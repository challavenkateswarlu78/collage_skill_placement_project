package com.collage.skillplacementportal.dto.admin;

public class TopStudentDTO {

    private Long studentId;

    private String studentName;

    private String department;

    private Double cgpa;

    public TopStudentDTO() {
    }

    public TopStudentDTO(
            Long studentId,
            String studentName,
            String department,
            Double cgpa) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.department = department;
        this.cgpa = cgpa;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }
}