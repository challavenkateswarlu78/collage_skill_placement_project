package com.collage.skillplacementportal.dto.dsa;

public class LeaderboardDTO {

    private int rank;

    private Long studentId;

    private String studentName;

    private int solvedProblems;

    private int totalPoints;

    public LeaderboardDTO() {
    }

    public LeaderboardDTO(
            int rank,
            Long studentId,
            String studentName,
            int solvedProblems,
            int totalPoints) {

        this.rank = rank;
        this.studentId = studentId;
        this.studentName = studentName;
        this.solvedProblems = solvedProblems;
        this.totalPoints = totalPoints;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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
}