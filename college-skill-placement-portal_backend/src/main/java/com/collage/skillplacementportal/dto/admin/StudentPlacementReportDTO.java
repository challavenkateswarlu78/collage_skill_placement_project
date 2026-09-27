package com.collage.skillplacementportal.dto.admin;

public class StudentPlacementReportDTO {

    private Long studentId;
    private String studentName;
    private String rollNumber;
    private String department;
    private Double cgpa;
    private boolean placed;

    public StudentPlacementReportDTO() {
    }

    public StudentPlacementReportDTO(
            Long studentId,
            String studentName,
            String rollNumber,
            String department,
            Double cgpa,
            boolean placed) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.rollNumber = rollNumber;
        this.department = department;
        this.cgpa = cgpa;
        this.placed = placed;
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

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
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

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }
}
