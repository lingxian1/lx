package com.exam.common.entity;

import javax.persistence.*;

/**
 * Created by LX on 2017/7/20.
 */
@Entity
@Table(name = "exam_area", schema = "exam", catalog = "")
public class ExamAreaEntity {
    private String areaId;
    private String areaName;

    @Id
    @Column(name = "area_ID", nullable = false, length = 2)
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Basic
    @Column(name = "area_name", nullable = true, length = 20)
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExamAreaEntity that = (ExamAreaEntity) o;

        if (areaId != null ? !areaId.equals(that.areaId) : that.areaId != null) return false;
        if (areaName != null ? !areaName.equals(that.areaName) : that.areaName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = areaId != null ? areaId.hashCode() : 0;
        result = 31 * result + (areaName != null ? areaName.hashCode() : 0);
        return result;
    }
}
