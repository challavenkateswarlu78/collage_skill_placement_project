package com.collage.skillplacementportal.dto.dsa;

public class DSAProgressDTO {

    private Long studentId;

    private String studentName;

    private int solvedProblems;

    private int totalPoints;

    private int currentStreak;

    public DSAProgressDTO() {
    }

    public DSAProgressDTO(
            Long studentId,
            String studentName,
            int solvedProblems,
            int totalPoints,
            int currentStreak) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.solvedProblems = solvedProblems;
        this.totalPoints = totalPoints;
        this.currentStreak = currentStreak;
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

    public int getSolvedProblems() {
        return solvedProblems;
    }

    public void setSolvedProblems(int solvedProblems) {
        this.solvedProblems = solvedProblems;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }
}