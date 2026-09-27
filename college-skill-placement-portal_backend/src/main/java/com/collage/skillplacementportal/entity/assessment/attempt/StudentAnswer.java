package com.collage.skillplacementportal.entity.assessment.attempt;

import com.collage.skillplacementportal.entity.assessment.question.Question;
import jakarta.persistence.*;

@Entity
@Table(
        name = "student_answers",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"attempt_id", "question_id"}
                )
        }
)
public class StudentAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "attempt_id", nullable = false)
    private AssessmentAttempt attempt;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(length = 2000)
    private String selectedAnswer;

    private Boolean correct;

    private Integer marksObtained;

    private Boolean codingCompleted;

    public StudentAnswer(Boolean codingCompleted) {
        this.codingCompleted = codingCompleted;
    }
    public void setCodingCompleted(Boolean codingCompleted) {
        this.codingCompleted = codingCompleted;
    }

    public StudentAnswer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssessmentAttempt getAttempt() {
        return attempt;
    }

    public void setAttempt(AssessmentAttempt attempt) {
        this.attempt = attempt;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Integer getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(Integer marksObtained) {
        this.marksObtained = marksObtained;
    }
}