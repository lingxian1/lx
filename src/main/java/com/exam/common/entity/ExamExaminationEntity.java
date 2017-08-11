package com.exam.common.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by LX on 2017/7/27.
 */
@Entity
@Table(name = "exam_examination", schema = "exam", catalog = "")
public class ExamExaminationEntity {
    private String examinationId;
    private String examinationName;
    private Integer answerTime;
    private String examinationType;
    private Integer questionCount;
    private Integer examinationScoreAll;
    private Timestamp examinationStart;
    private Timestamp examinationEnd;
    private Integer examineeCount;
    private String examinationInfo;
    private String isDel;
    private Integer signalCount;
    private Integer multipleCount;
    private Integer judgementCount;

    @Id
    @Column(name = "examination_ID", nullable = false, length = 10)
    public String getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(String examinationId) {
        this.examinationId = examinationId;
    }

    @Basic
    @Column(name = "examination_name", nullable = true, length = 255)
    public String getExaminationName() {
        return examinationName;
    }

    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }

    @Basic
    @Column(name = "answer_time", nullable = true)
    public Integer getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Integer answerTime) {
        this.answerTime = answerTime;
    }

    @Basic
    @Column(name = "examination_type", nullable = true, length = 10)
    public String getExaminationType() {
        return examinationType;
    }

    public void setExaminationType(String examinationType) {
        this.examinationType = examinationType;
    }

    @Basic
    @Column(name = "question_count", nullable = true)
    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    @Basic
    @Column(name = "examination_score_all", nullable = true)
    public Integer getExaminationScoreAll() {
        return examinationScoreAll;
    }

    public void setExaminationScoreAll(Integer examinationScoreAll) {
        this.examinationScoreAll = examinationScoreAll;
    }

    @Basic
    @Column(name = "examination_start", nullable = true)
    public Timestamp getExaminationStart() {
        return examinationStart;
    }

    public void setExaminationStart(Timestamp examinationStart) {
        this.examinationStart = examinationStart;
    }

    @Basic
    @Column(name = "examination_end", nullable = true)
    public Timestamp getExaminationEnd() {
        return examinationEnd;
    }

    public void setExaminationEnd(Timestamp examinationEnd) {
        this.examinationEnd = examinationEnd;
    }

    @Basic
    @Column(name = "examinee_count", nullable = true)
    public Integer getExamineeCount() {
        return examineeCount;
    }

    public void setExamineeCount(Integer examineeCount) {
        this.examineeCount = examineeCount;
    }

    @Basic
    @Column(name = "examination_info", nullable = true, length = 255)
    public String getExaminationInfo() {
        return examinationInfo;
    }

    public void setExaminationInfo(String examinationInfo) {
        this.examinationInfo = examinationInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExamExaminationEntity that = (ExamExaminationEntity) o;

        if (examinationId != null ? !examinationId.equals(that.examinationId) : that.examinationId != null)
            return false;
        if (examinationName != null ? !examinationName.equals(that.examinationName) : that.examinationName != null)
            return false;
        if (answerTime != null ? !answerTime.equals(that.answerTime) : that.answerTime != null) return false;
        if (examinationType != null ? !examinationType.equals(that.examinationType) : that.examinationType != null)
            return false;
        if (questionCount != null ? !questionCount.equals(that.questionCount) : that.questionCount != null)
            return false;
        if (examinationScoreAll != null ? !examinationScoreAll.equals(that.examinationScoreAll) : that.examinationScoreAll != null)
            return false;
        if (examinationStart != null ? !examinationStart.equals(that.examinationStart) : that.examinationStart != null)
            return false;
        if (examinationEnd != null ? !examinationEnd.equals(that.examinationEnd) : that.examinationEnd != null)
            return false;
        if (examineeCount != null ? !examineeCount.equals(that.examineeCount) : that.examineeCount != null)
            return false;
        if (examinationInfo != null ? !examinationInfo.equals(that.examinationInfo) : that.examinationInfo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = examinationId != null ? examinationId.hashCode() : 0;
        result = 31 * result + (examinationName != null ? examinationName.hashCode() : 0);
        result = 31 * result + (answerTime != null ? answerTime.hashCode() : 0);
        result = 31 * result + (examinationType != null ? examinationType.hashCode() : 0);
        result = 31 * result + (questionCount != null ? questionCount.hashCode() : 0);
        result = 31 * result + (examinationScoreAll != null ? examinationScoreAll.hashCode() : 0);
        result = 31 * result + (examinationStart != null ? examinationStart.hashCode() : 0);
        result = 31 * result + (examinationEnd != null ? examinationEnd.hashCode() : 0);
        result = 31 * result + (examineeCount != null ? examineeCount.hashCode() : 0);
        result = 31 * result + (examinationInfo != null ? examinationInfo.hashCode() : 0);
        return result;
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
    @Column(name = "signal_count", nullable = true)
    public Integer getSignalCount() {
        return signalCount;
    }

    public void setSignalCount(Integer signalCount) {
        this.signalCount = signalCount;
    }

    @Basic
    @Column(name = "multiple_count", nullable = true)
    public Integer getMultipleCount() {
        return multipleCount;
    }

    public void setMultipleCount(Integer multipleCount) {
        this.multipleCount = multipleCount;
    }

    @Basic
    @Column(name = "judgement_count", nullable = true)
    public Integer getJudgementCount() {
        return judgementCount;
    }

    public void setJudgementCount(Integer judgementCount) {
        this.judgementCount = judgementCount;
    }
}
