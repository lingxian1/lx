package com.exam.common.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by LX on 2017/7/27.
 */
@Entity
@Table(name = "exam_answer_log", schema = "exam", catalog = "")
@IdClass(ExamAnswerLogEntityPK.class)
public class ExamAnswerLogEntity {
    private String examineeId;
    private String examinationId;
    private String questionId;
    private String examineeAnswer;
    private Timestamp submitTime;
    private Integer scoreReal;

    @Id
    @Column(name = "examinee_ID", nullable = false, length = 10)
    public String getExamineeId() {
        return examineeId;
    }

    public void setExamineeId(String examineeId) {
        this.examineeId = examineeId;
    }

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
    @Column(name = "examinee_answer", nullable = true, length = 10)
    public String getExamineeAnswer() {
        return examineeAnswer;
    }

    public void setExamineeAnswer(String examineeAnswer) {
        this.examineeAnswer = examineeAnswer;
    }

    @Basic
    @Column(name = "submit_time", nullable = true)
    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    @Basic
    @Column(name = "score_real", nullable = true)
    public Integer getScoreReal() {
        return scoreReal;
    }

    public void setScoreReal(Integer scoreReal) {
        this.scoreReal = scoreReal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExamAnswerLogEntity that = (ExamAnswerLogEntity) o;

        if (examineeId != null ? !examineeId.equals(that.examineeId) : that.examineeId != null) return false;
        if (examinationId != null ? !examinationId.equals(that.examinationId) : that.examinationId != null)
            return false;
        if (questionId != null ? !questionId.equals(that.questionId) : that.questionId != null) return false;
        if (examineeAnswer != null ? !examineeAnswer.equals(that.examineeAnswer) : that.examineeAnswer != null)
            return false;
        if (submitTime != null ? !submitTime.equals(that.submitTime) : that.submitTime != null) return false;
        if (scoreReal != null ? !scoreReal.equals(that.scoreReal) : that.scoreReal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = examineeId != null ? examineeId.hashCode() : 0;
        result = 31 * result + (examinationId != null ? examinationId.hashCode() : 0);
        result = 31 * result + (questionId != null ? questionId.hashCode() : 0);
        result = 31 * result + (examineeAnswer != null ? examineeAnswer.hashCode() : 0);
        result = 31 * result + (submitTime != null ? submitTime.hashCode() : 0);
        result = 31 * result + (scoreReal != null ? scoreReal.hashCode() : 0);
        return result;
    }
}
