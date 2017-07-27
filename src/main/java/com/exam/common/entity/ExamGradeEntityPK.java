package com.exam.common.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by LX on 2017/7/27.
 */
public class ExamGradeEntityPK implements Serializable {
    private String examineeId;
    private String examinationId;

    @Column(name = "examinee_ID", nullable = false, length = 10)
    @Id
    public String getExamineeId() {
        return examineeId;
    }

    public void setExamineeId(String examineeId) {
        this.examineeId = examineeId;
    }

    @Column(name = "examination_ID", nullable = false, length = 10)
    @Id
    public String getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(String examinationId) {
        this.examinationId = examinationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExamGradeEntityPK that = (ExamGradeEntityPK) o;

        if (examineeId != null ? !examineeId.equals(that.examineeId) : that.examineeId != null) return false;
        if (examinationId != null ? !examinationId.equals(that.examinationId) : that.examinationId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = examineeId != null ? examineeId.hashCode() : 0;
        result = 31 * result + (examinationId != null ? examinationId.hashCode() : 0);
        return result;
    }
}
