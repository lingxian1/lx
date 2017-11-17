package com.exam.common.other;

/**
 * Created by LX on 2017/8/7.
 * 区域成绩信息
 */
public class GradeArea {
    private String areaName;
    private String examinationId;
    private Number examineeCount;
    private Number gradeAvg;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(String examinationId) {
        this.examinationId = examinationId;
    }

    public Number getExamineeCount() {
        return examineeCount;
    }

    public void setExamineeCount(Number examineeCount) {
        this.examineeCount = examineeCount;
    }

    public Number getGradeAvg() {
        return gradeAvg;
    }

    public void setGradeAvg(Number gradeAvg) {
        this.gradeAvg = gradeAvg;
    }
}
