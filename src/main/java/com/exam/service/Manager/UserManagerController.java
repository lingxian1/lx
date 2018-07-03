package com.exam.service.Manager;

import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.ExamineeDao;
import com.exam.common.dao.SysUserDao;
import com.exam.common.entity.ExamExamineeEntity;
import com.exam.common.util.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * Created by LX on 2017/7/26.
 * 考生信息管理
 */
@RestController
@RequestMapping("/usersManager")
public class UserManagerController {
    @Autowired
    ExamineeDao examineeDao;

    @Autowired
    SysUserDao sysUserDao;

    private Logger logger = LoggerFactory.getLogger(UserManagerController.class);

    /**
     * 返回所有考生信息 考生信息管理
     *
     * @return
     */
    @GetMapping
    public Response getUser() {

        List<ExamExamineeEntity> examExaminationEntities = examineeDao.findAllMessage();
        Iterator<ExamExamineeEntity> iterator = examExaminationEntities.iterator();
        while (iterator.hasNext()) {
            ExamExamineeEntity examExamineeEntity = iterator.next();
            examExamineeEntity.setPassword("");
            if (examExamineeEntity.getIdentity().equals("2")) {
                iterator.remove();
            }
        }
        return Response.ok(examExaminationEntities);
    }

    /**
     * 编辑信息 考生信息管理
     *
     * @param oper
     * @param id
     * @param examineeId
     * @param name
     * @param phone
     * @param areaId
     * @param sex
     * @return
     */
    @PostMapping("/handle")
    public Response login(
            @RequestParam(defaultValue = "") String oper,
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String examineeId,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String phone,
            @RequestParam(defaultValue = "") String areaId,
            @RequestParam(defaultValue = "") String sex) {

        boolean state = false;
        switch (oper) {
            case "add":
                state = addUser(name, phone, areaId, sex);
                break;
            case "del":
                state = delUser(id);
                break;
            case "edit":
                state = editUser(examineeId, name, phone, areaId, sex);
                break;
            default:
        }
        if (state) {
            return Response.ok("操作成功");
        } else {
            return Response.error(ErrorCode.EXAM_PHONE_ERROR);
        }
    }


    /**
     * 更新用户
     *
     * @param examineeId
     * @param name
     * @param phone
     * @param areaId
     * @param sex
     * @return
     */
    private boolean editUser(String examineeId, String name, String phone, String areaId, String sex) {
        if ("".equals(phone) || phone.length() > 11 || "".equals(name)) {
            return false;
        }
        if (!examineeDao.updateById(examineeId, name, phone, areaId, sex)) {
            return false;
        }
        return true;
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    private boolean delUser(String id) {
        ExamExamineeEntity examineeEntity = examineeDao.findById(id);
        if (examineeEntity == null) {
            return false;
        }
        examineeEntity.setIdentity("2");
        examineeEntity.setPhone("此用户已删除");
        examineeDao.update(examineeEntity);
        return true;
    }

    /**
     * 添加用户
     *
     * @param name
     * @param phone
     * @param areaId
     * @param sex
     * @return
     */
    private boolean addUser(String name, String phone, String areaId, String sex) {
        if ("".equals(phone) || phone.length() > 11 || "".equals(name)) {
            return false;
        }
        if (!examineeDao.checkPhone(phone)) {
            return false;
        }
        ExamExamineeEntity examinee = new ExamExamineeEntity();
        examinee.setExamineeId(examineeDao.newUsersId());
        examinee.setName(name);
        String defaultSalt = Md5Utils.stringMD5("123456");
        examinee.setPassword(Md5Utils.stringMD5(defaultSalt+defaultSalt));
        examinee.setSalt(defaultSalt);
        examinee.setPhone(phone);
        examinee.setIdentity("1");
        examinee.setSex(sex);
        examinee.setAreaId(areaId);
        examineeDao.save(examinee);
        return true;
    }

}
