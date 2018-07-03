package com.exam.common.entity;

import javax.persistence.*;

/**
 * Created by LX on 2018/7/3.
 */
@Entity
@Table(name = "exam_examinee", schema = "exam", catalog = "")
public class ExamExamineeEntity {
    private String examineeId;
    private String name;
    private String phone;
    private String areaId;
    private String identity;
    private String password;
    private String sex;
    private String salt;

    @Id
    @Column(name = "examinee_ID", nullable = false, length = 10)
    public String getExamineeId() {
        return examineeId;
    }

    public void setExamineeId(String examineeId) {
        this.examineeId = examineeId;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 11)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "area_ID", nullable = true, length = 2)
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Basic
    @Column(name = "identity", nullable = true, length = 1)
    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "sex", nullable = true, length = 1)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "salt", nullable = true, length = 32)
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExamExamineeEntity entity = (ExamExamineeEntity) o;

        if (examineeId != null ? !examineeId.equals(entity.examineeId) : entity.examineeId != null) return false;
        if (name != null ? !name.equals(entity.name) : entity.name != null) return false;
        if (phone != null ? !phone.equals(entity.phone) : entity.phone != null) return false;
        if (areaId != null ? !areaId.equals(entity.areaId) : entity.areaId != null) return false;
        if (identity != null ? !identity.equals(entity.identity) : entity.identity != null) return false;
        if (password != null ? !password.equals(entity.password) : entity.password != null) return false;
        if (sex != null ? !sex.equals(entity.sex) : entity.sex != null) return false;
        if (salt != null ? !salt.equals(entity.salt) : entity.salt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = examineeId != null ? examineeId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (areaId != null ? areaId.hashCode() : 0);
        result = 31 * result + (identity != null ? identity.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        return result;
    }
}
