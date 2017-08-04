package com.exam.common.other;

import java.sql.Timestamp;

/**
 * Created by LX on 2017/8/4.
 */
public class AllGrade {
    private String examineeId;
    private int grade;
    private Timestamp examinationTime;
    private String name;
    private String phone;
    private String areaName;

    public String getExamineeId() {
        return examineeId;
    }

    public void setExamineeId(String examineeId) {
        this.examineeId = examineeId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Timestamp getExaminationTime() {
        return examinationTime;
    }

    public void setExaminationTime(Timestamp examinationTime) {
        this.examinationTime = examinationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
