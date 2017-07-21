package com.exam.common.entity;

import javax.persistence.*;

/**
 * Created by LX on 2017/7/20.
 */
@Entity
@Table(name = "exam_grade", schema = "exam", catalog = "")
@IdClass(ExamGradeEntityPK.class)
public class ExamGradeEntity {
    private String examineeId;
    private String examinationId;
    private Integer grade;
    private String examinationState;

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

        return true;
    }

    @Override
    public int hashCode() {
        int result = examineeId != null ? examineeId.hashCode() : 0;
        result = 31 * result + (examinationId != null ? examinationId.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (examinationState != null ? examinationState.hashCode() : 0);
        return result;
    }
}
