package com.exam.service.Manager;

import com.exam.common.EasyToken.EasyToken;
import com.exam.common.EasyToken.Token;
import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.AreaDao;
import com.exam.common.dao.ExamineeDao;
import com.exam.common.dao.GradeDao;
import com.exam.common.entity.ExamAreaEntity;
import com.exam.common.entity.ExamExamineeEntity;
import com.exam.common.entity.ExamGradeEntity;
import com.exam.common.other.AllGrade;
import com.exam.common.other.GradeArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by LX on 2017/8/4.
 * 考试成绩管理
 */
@RestController
@RequestMapping("/grademanager")
public class GradeManagerController {
    @Autowired
    GradeDao gradeDao;
    @Autowired
    ExamineeDao examineeDao;
    @Autowired
    AreaDao areaDao;
    private Logger logger = LoggerFactory.getLogger(GradeManagerController.class);

    /**
     * 某场考试所有成绩
     * @param token
     * @param uid
     * @param examinationId
     * @return
     */
    @GetMapping("/all")
    public Response gradeAll(@CookieValue(value = "token", defaultValue = "") String token,
                             @CookieValue(value = "userId", defaultValue = "") String uid,
                             @RequestParam(defaultValue = "") String examinationId){
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            if("".equals(examinationId)){
                return Response.error(ErrorCode.EXAM_ID_ERROR);
            }
            List<AllGrade> list =new ArrayList<>();
            List<ExamGradeEntity> entities=gradeDao.findGradeForExam(examinationId);
            Iterator<ExamGradeEntity> iterator =entities.iterator();
            while (iterator.hasNext()){
                ExamGradeEntity examGradeEntity=iterator.next();
                String examineeId=examGradeEntity.getExamineeId();
                ExamExamineeEntity examineeEntity=examineeDao.findById(examineeId);
                AllGrade allGrade=new AllGrade();
                allGrade.setExamineeId(examineeId);
                allGrade.setGrade(examGradeEntity.getGrade());
                allGrade.setExaminationTime(examGradeEntity.getExaminationTime());
                allGrade.setPhone(examineeEntity.getPhone());
                allGrade.setName(examineeEntity.getName());
                allGrade.setAreaName(areaDao.findById(examineeEntity.getAreaId()).getAreaName());
                list.add(allGrade);
            }
            return Response.ok(list);
        }
    }

    /**
     * 区域统计--考试成绩管理
     * @param token
     * @param uid
     * @param examinationId
     * @return
     */
    @GetMapping("/area")
    public Response getArea(@CookieValue(value = "token", defaultValue = "") String token,
                            @CookieValue(value = "userId", defaultValue = "") String uid,
                            @RequestParam(defaultValue = "") String examinationId){
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            List<ExamAreaEntity> areaIds=areaDao.findAll();
            if(areaIds==null){
                return Response.error();
            }
            /**
             * 根据区域找出对应区域考生 遍历 通过查找成绩表对应列 统计平均分
             */
            List<GradeArea> list = new ArrayList<>();
            for(int i=0;i<areaIds.size();i++) {
                List<ExamExamineeEntity> entities = examineeDao.findBy("areaId", areaIds.get(i).getAreaId());
                if (entities == null) {
                    return Response.error();
                }
                int sumGrade=0;
                int count=0;
                //计算平均分
                for (ExamExamineeEntity examinee : entities) {
                    ExamGradeEntity gradeEntity = gradeDao.findGrade(examinee.getExamineeId(), examinationId);
                    if (!gradeEntity.getExaminationState().equals("is null")) {
                        sumGrade+=gradeEntity.getGrade();
                        count++;
                    }
                }

                GradeArea gradeArea=new GradeArea();
                gradeArea.setAreaName(areaIds.get(i).getAreaName());
                gradeArea.setExaminationId(examinationId);
                gradeArea.setExamineeCount(count);
                if(count==0){
                    gradeArea.setGradeAvg(0);
                }else{
                    gradeArea.setGradeAvg((double) sumGrade/count);
                }
                list.add(gradeArea);
            }
            return Response.ok(list);
        }
    }
}
