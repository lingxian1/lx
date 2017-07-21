package com.exam.common.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by LX on 2017/7/20.
 */
@Entity
@Table(name = "exam_question", schema = "exam", catalog = "")
public class ExamQuestionEntity {
    private String questionId;
    private String questionText;
    private String questionType;
    private Integer questionChooseCount;
    private String questionChooseA;
    private String questionChooseB;
    private String questionChooseC;
    private String questionChooseD;
    private String questionAnswer;
    private String questionClassification;
    private Timestamp questionCreateTime;
    private String isDel;
    private String questionOther;

    @Id
    @Column(name = "question_ID", nullable = false, length = 10)
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Basic
    @Column(name = "question_text", nullable = true, length = 255)
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Basic
    @Column(name = "question_type", nullable = true, length = 20)
    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    @Basic
    @Column(name = "question_choose_count", nullable = true)
    public Integer getQuestionChooseCount() {
        return questionChooseCount;
    }

    public void setQuestionChooseCount(Integer questionChooseCount) {
        this.questionChooseCount = questionChooseCount;
    }

    @Basic
    @Column(name = "question_chooseA", nullable = true, length = 255)
    public String getQuestionChooseA() {
        return questionChooseA;
    }

    public void setQuestionChooseA(String questionChooseA) {
        this.questionChooseA = questionChooseA;
    }

    @Basic
    @Column(name = "question_chooseB", nullable = true, length = 255)
    public String getQuestionChooseB() {
        return questionChooseB;
    }

    public void setQuestionChooseB(String questionChooseB) {
        this.questionChooseB = questionChooseB;
    }

    @Basic
    @Column(name = "question_chooseC", nullable = true, length = 255)
    public String getQuestionChooseC() {
        return questionChooseC;
    }

    public void setQuestionChooseC(String questionChooseC) {
        this.questionChooseC = questionChooseC;
    }

    @Basic
    @Column(name = "question_chooseD", nullable = true, length = 255)
    public String getQuestionChooseD() {
        return questionChooseD;
    }

    public void setQuestionChooseD(String questionChooseD) {
        this.questionChooseD = questionChooseD;
    }

    @Basic
    @Column(name = "question_answer", nullable = true, length = 10)
    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    @Basic
    @Column(name = "question_classification", nullable = true, length = 10)
    public String getQuestionClassification() {
        return questionClassification;
    }

    public void setQuestionClassification(String questionClassification) {
        this.questionClassification = questionClassification;
    }

    @Basic
    @Column(name = "question_create_time", nullable = true)
    public Timestamp getQuestionCreateTime() {
        return questionCreateTime;
    }

    public void setQuestionCreateTime(Timestamp questionCreateTime) {
        this.questionCreateTime = questionCreateTime;
    }

    @Basic
    @Column(name = "isDEL", nullable = true, length = 2)
    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    @Basic
    @Column(name = "question_other", nullable = true, length = 255)
    public String getQuestionOther() {
        return questionOther;
    }

    public void setQuestionOther(String questionOther) {
        this.questionOther = questionOther;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExamQuestionEntity that = (ExamQuestionEntity) o;

        if (questionId != null ? !questionId.equals(that.questionId) : that.questionId != null) return false;
        if (questionText != null ? !questionText.equals(that.questionText) : that.questionText != null) return false;
        if (questionType != null ? !questionType.equals(that.questionType) : that.questionType != null) return false;
        if (questionChooseCount != null ? !questionChooseCount.equals(that.questionChooseCount) : that.questionChooseCount != null)
            return false;
        if (questionChooseA != null ? !questionChooseA.equals(that.questionChooseA) : that.questionChooseA != null)
            return false;
        if (questionChooseB != null ? !questionChooseB.equals(that.questionChooseB) : that.questionChooseB != null)
            return false;
        if (questionChooseC != null ? !questionChooseC.equals(that.questionChooseC) : that.questionChooseC != null)
            return false;
        if (questionChooseD != null ? !questionChooseD.equals(that.questionChooseD) : that.questionChooseD != null)
            return false;
        if (questionAnswer != null ? !questionAnswer.equals(that.questionAnswer) : that.questionAnswer != null)
            return false;
        if (questionClassification != null ? !questionClassification.equals(that.questionClassification) : that.questionClassification != null)
            return false;
        if (questionCreateTime != null ? !questionCreateTime.equals(that.questionCreateTime) : that.questionCreateTime != null)
            return false;
        if (isDel != null ? !isDel.equals(that.isDel) : that.isDel != null) return false;
        if (questionOther != null ? !questionOther.equals(that.questionOther) : that.questionOther != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = questionId != null ? questionId.hashCode() : 0;
        result = 31 * result + (questionText != null ? questionText.hashCode() : 0);
        result = 31 * result + (questionType != null ? questionType.hashCode() : 0);
        result = 31 * result + (questionChooseCount != null ? questionChooseCount.hashCode() : 0);
        result = 31 * result + (questionChooseA != null ? questionChooseA.hashCode() : 0);
        result = 31 * result + (questionChooseB != null ? questionChooseB.hashCode() : 0);
        result = 31 * result + (questionChooseC != null ? questionChooseC.hashCode() : 0);
        result = 31 * result + (questionChooseD != null ? questionChooseD.hashCode() : 0);
        result = 31 * result + (questionAnswer != null ? questionAnswer.hashCode() : 0);
        result = 31 * result + (questionClassification != null ? questionClassification.hashCode() : 0);
        result = 31 * result + (questionCreateTime != null ? questionCreateTime.hashCode() : 0);
        result = 31 * result + (isDel != null ? isDel.hashCode() : 0);
        result = 31 * result + (questionOther != null ? questionOther.hashCode() : 0);
        return result;
    }
}
