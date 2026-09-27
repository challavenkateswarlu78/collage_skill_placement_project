package com.collage.skillplacementportal.dto.recommendation;

public class RecommendationDTO {

    private Long problemId;
    private String title;
    private String platform;
    private String topic;
    private String difficulty;
    private Integer points;
    private String problemUrl;

    public RecommendationDTO() {
    }

    public RecommendationDTO(
            Long problemId,
            String title,
            String platform,
            String topic,
            String difficulty,
            Integer points,
            String problemUrl) {

        this.problemId = problemId;
        this.title = title;
        this.platform = platform;
        this.topic = topic;
        this.difficulty = difficulty;
        this.points = points;
        this.problemUrl = problemUrl;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getProblemUrl() {
        return problemUrl;
    }

    public void setProblemUrl(String problemUrl) {
        this.problemUrl = problemUrl;
    }
}