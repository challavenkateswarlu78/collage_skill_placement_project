package com.collage.skillplacementportal.dto.admin;

public class SkillAnalyticsDTO {

    private Long skillId;
    private String skillName;
    private long studentCount;
    private double averageSkillLevel;

    public SkillAnalyticsDTO() {
    }

    public SkillAnalyticsDTO(
            Long skillId,
            String skillName,
            long studentCount,
            double averageSkillLevel) {

        this.skillId = skillId;
        this.skillName = skillName;
        this.studentCount = studentCount;
        this.averageSkillLevel = averageSkillLevel;
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

    public long getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(long studentCount) {
        this.studentCount = studentCount;
    }

    public double getAverageSkillLevel() {
        return averageSkillLevel;
    }

    public void setAverageSkillLevel(double averageSkillLevel) {
        this.averageSkillLevel = averageSkillLevel;
    }
}