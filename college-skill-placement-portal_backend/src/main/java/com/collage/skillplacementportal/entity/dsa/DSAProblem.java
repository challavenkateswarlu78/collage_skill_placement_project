package com.collage.skillplacementportal.entity.dsa;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "dsa_problems")
public class DSAProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String platform;

    @Column(nullable = false, length = 1000)
    private String problemUrl;

    private String topic;

    private String difficulty;

    private Integer points;

    private Boolean dailyProblem;

    private LocalDate dailyDate;
    public DSAProblem() {
    }
    public LocalDate getDailyDate() {
        return dailyDate;
    }
    public void setDailyDate(LocalDate dailyDate) {
        this.dailyDate = dailyDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getProblemUrl() {
        return problemUrl;
    }

    public void setProblemUrl(String problemUrl) {
        this.problemUrl = problemUrl;
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

    public Boolean getDailyProblem() {
        return dailyProblem;
    }

    public void setDailyProblem(Boolean dailyProblem) {
        this.dailyProblem = dailyProblem;
    }
}