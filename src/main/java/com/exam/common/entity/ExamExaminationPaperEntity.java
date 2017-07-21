package com.exam.common.entity;

import javax.persistence.*;

/**
 * Created by LX on 2017/7/20.
 */
@Entity
@Table(name = "exam_examination_paper", schema = "exam", catalog = "")
@IdClass(ExamExaminationPaperEntityPK.class)
public class ExamExaminationPaperEntity {
    private String examinationId;
    private String questionId;
    private Integer score;
    private Double accuracy;

    @Id
    @Column(name = "examination_ID", nullable = false, length = 10)
    public String getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(String examinationId) {
        this.examinationId = examinationId;
    }

    @Id
    @Column(name = "question_ID", nullable = false, length = 10)
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Basic
    @Column(name = "score", nullable = true)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Column(name = "accuracy", nullable = true, precision = 0)
    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExamExaminationPaperEntity that = (ExamExaminationPaperEntity) o;

        if (examinationId != null ? !examinationId.equals(that.examinationId) : that.examinationId != null)
            return false;
        if (questionId != null ? !questionId.equals(that.questionId) : that.questionId != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        if (accuracy != null ? !accuracy.equals(that.accuracy) : that.accuracy != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = examinationId != null ? examinationId.hashCode() : 0;
        result = 31 * result + (questionId != null ? questionId.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (accuracy != null ? accuracy.hashCode() : 0);
        return result;
    }
}
