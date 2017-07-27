package com.exam.common.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by LX on 2017/7/27.
 */
@Entity
@Table(name = "exam_grade", schema = "exam", catalog = "")
@IdClass(ExamGradeEntityPK.class)
public class ExamGradeEntity {
    private String examineeId;
    private String examinationId;
    private Integer grade;
    private String examinationState;
    private Timestamp examinationTime;
    private String examinationInfo;

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

    @Basic
    @Column(name = "grade", nullable = true)
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "examination_state", nullable = true, length = 2)
    public String getExaminationState() {
        return examinationState;
    }

    public void setExaminationState(String examinationState) {
        this.examinationState = examinationState;
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

        ExamGradeEntity that = (ExamGradeEntity) o;

        if (examineeId != null ? !examineeId.equals(that.examineeId) : that.examineeId != null) return false;
        if (examinationId != null ? !examinationId.equals(that.examinationId) : that.examinationId != null)
            return false;
        if (grade != null ? !grade.equals(that.grade) : that.grade != null) return false;
        if (examinationState != null ? !examinationState.equals(that.examinationState) : that.examinationState != null)
            return false;
        if (examinationTime != null ? !examinationTime.equals(that.examinationTime) : that.examinationTime != null)
            return false;
        if (examinationInfo != null ? !examinationInfo.equals(that.examinationInfo) : that.examinationInfo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = examineeId != null ? examineeId.hashCode() : 0;
        result = 31 * result + (examinationId != null ? examinationId.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (examinationState != null ? examinationState.hashCode() : 0);
        result = 31 * result + (examinationTime != null ? examinationTime.hashCode() : 0);
        result = 31 * result + (examinationInfo != null ? examinationInfo.hashCode() : 0);
        return result;
    }
}
