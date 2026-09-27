package com.collage.skillplacementportal.dto.job;

public class JobSkillMatchDTO {

    private Long skillId;

    private String skillName;

    private double studentLevel;

    private double requiredLevel;

    private double skillMatchPercentage;

    private boolean matched;

    public JobSkillMatchDTO() {
    }

    public JobSkillMatchDTO(
            Long skillId,
            String skillName,
            double studentLevel,
            double requiredLevel,
            double skillMatchPercentage,
            boolean matched) {

        this.skillId = skillId;
        this.skillName = skillName;
        this.studentLevel = studentLevel;
        this.requiredLevel = requiredLevel;
        this.skillMatchPercentage =
                skillMatchPercentage;
        this.matched = matched;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public double getStudentLevel() {
        return studentLevel;
    }

    public void setStudentLevel(double studentLevel) {
        this.studentLevel = studentLevel;
    }

    public double getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(double requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public double getSkillMatchPercentage() {
        return skillMatchPercentage;
    }

    public void setSkillMatchPercentage(
            double skillMatchPercentage) {

        this.skillMatchPercentage =
                skillMatchPercentage;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }
}
