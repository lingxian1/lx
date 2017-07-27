package com.exam.common.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by LX on 2017/7/27.
 */
@Entity
@Table(name = "exam_error_question", schema = "exam", catalog = "")
@IdClass(ExamErrorQuestionEntityPK.class)
public class ExamErrorQuestionEntity {
    private String examinationId;
    private String questionId;
    private Double accuracy;
    private Timestamp examinationTime;
    private String errorInfo;

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
    @Column(name = "accuracy", nullable = true, precision = 0)
    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    @Basic
    @Column(name = "examination_time", nullable = true)
    public Timestamp getExaminationTime() {
        return examinationTime;
    }

    public void setExaminationTime(Timestamp examinationTime) {
        this.examinationTime = examinationTime;
    }

    @Basic
    @Column(name = "error_info", nullable = true, length = 255)
    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExamErrorQuestionEntity that = (ExamErrorQuestionEntity) o;

        if (examinationId != null ? !examinationId.equals(that.examinationId) : that.examinationId != null)
            return false;
        if (questionId != null ? !questionId.equals(that.questionId) : that.questionId != null) return false;
        if (accuracy != null ? !accuracy.equals(that.accuracy) : that.accuracy != null) return false;
        if (examinationTime != null ? !examinationTime.equals(that.examinationTime) : that.examinationTime != null)
            return false;
        if (errorInfo != null ? !errorInfo.equals(that.errorInfo) : that.errorInfo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = examinationId != null ? examinationId.hashCode() : 0;
        result = 31 * result + (questionId != null ? questionId.hashCode() : 0);
        result = 31 * result + (accuracy != null ? accuracy.hashCode() : 0);
        result = 31 * result + (examinationTime != null ? examinationTime.hashCode() : 0);
        result = 31 * result + (errorInfo != null ? errorInfo.hashCode() : 0);
        return result;
    }
}
