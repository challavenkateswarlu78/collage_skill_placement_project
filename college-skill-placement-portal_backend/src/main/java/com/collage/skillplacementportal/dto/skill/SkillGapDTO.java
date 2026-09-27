package com.collage.skillplacementportal.dto.skill;

public class SkillGapDTO {

    private Long skillId;

    private String skillName;

    private double currentLevel;

    private String status;

    private String recommendation;


    public SkillGapDTO() {
    }

    public SkillGapDTO(
            Long skillId,
            String skillName,
            double currentLevel,
            String status,
            String recommendation) {

        this.skillId = skillId;
        this.skillName = skillName;
        this.currentLevel = currentLevel;
        this.status = status;
        this.recommendation = recommendation;
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

    public double getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(double currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}