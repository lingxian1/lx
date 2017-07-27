package com.exam.common.other;

import java.sql.Timestamp;

/**
 * Created by LX on 2017/7/25.
 * 考试成绩信息 entity
 */
public class GradeMessage {
    private String examineeId;
    private String examinationId;
    private Integer grade;
    private String examinationState;
    private String examinationName;
    private Timestamp examinationEnd;
    private Integer examinationScoreAll;
    public String getExamineeId() {
        return examineeId;
    }

    public void setExamineeId(String examineeId) {
        this.examineeId = examineeId;
    }

    public String getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(String examinationId) {
        this.examinationId = examinationId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getExaminationState() {
        return examinationState;
    }

    public void setExaminationState(String examinationState) {
        this.examinationState = examinationState;
    }

    public String getExaminationName() {
        return examinationName;
    }

    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }

    public Timestamp getExaminationEnd() {
        return examinationEnd;
    }

    public void setExaminationEnd(Timestamp examinationEnd) {
        this.examinationEnd = examinationEnd;
    }

    public Integer getExaminationScoreAll() {
        return examinationScoreAll;
    }

    public void setExaminationScoreAll(Integer examinationScoreAll) {
        this.examinationScoreAll = examinationScoreAll;
    }

    @Override
    public String toString() {
        return "GradeMessage{" +
                "examineeId='" + examineeId + '\'' +
                ", examinationId='" + examinationId + '\'' +
                ", grade=" + grade +
                ", examinationState='" + examinationState + '\'' +
                ", examinationName='" + examinationName + '\'' +
                ", examinationEnd=" + examinationEnd +
                ", examinationScoreAll=" + examinationScoreAll +
                '}';
    }
}
