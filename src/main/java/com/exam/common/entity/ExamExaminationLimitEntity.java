package com.exam.common.entity;

import javax.persistence.*;

/**
 * Created by LX on 2017/7/20.
 */
@Entity
@Table(name = "exam_examination_limit", schema = "exam", catalog = "")
@IdClass(ExamExaminationLimitEntityPK.class)
public class ExamExaminationLimitEntity {
    private String examinationId;
    private String areaId;
    private String isAllow;

    @Id
    @Column(name = "examination_ID", nullable = false, length = 10)
    public String getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(String examinationId) {
        this.examinationId = examinationId;
    }

    @Id
    @Column(name = "area_ID", nullable = false, length = 2)
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Basic
    @Column(name = "isAllow", nullable = true, length = 2)
    public String getIsAllow() {
        return isAllow;
    }

    public void setIsAllow(String isAllow) {
        this.isAllow = isAllow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExamExaminationLimitEntity that = (ExamExaminationLimitEntity) o;

        if (examinationId != null ? !examinationId.equals(that.examinationId) : that.examinationId != null)
            return false;
        if (areaId != null ? !areaId.equals(that.areaId) : that.areaId != null) return false;
        if (isAllow != null ? !isAllow.equals(that.isAllow) : that.isAllow != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = examinationId != null ? examinationId.hashCode() : 0;
        result = 31 * result + (areaId != null ? areaId.hashCode() : 0);
        result = 31 * result + (isAllow != null ? isAllow.hashCode() : 0);
        return result;
    }
}
