package com.exam.service.Manager;

import com.exam.common.EasyToken.EasyToken;
import com.exam.common.EasyToken.Token;
import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.QuestionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by LX on 2017/7/28.
 */
@RestController
@RequestMapping("/questionsManager")
public class QuestionManager {
    @Autowired
    QuestionDao questionDao;

    private Logger logger = LoggerFactory.getLogger(QuestionManager.class);

    @GetMapping
    public Response getUser(@RequestParam(defaultValue = "") String userId,
                            @RequestParam(defaultValue = "")String token,
                            @RequestParam(defaultValue = "")String info){
        logger.info(userId);
        logger.info(token);
        logger.info(info);
        Token token1=new Token(userId,token);
        String status=new EasyToken().checkToken(token1);
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            return Response.ok(questionDao.findQuestionClass(info));
        }
    }


    @PostMapping("/handle")
    public String login(
            @RequestParam(defaultValue = "") String oper,
            @RequestParam(defaultValue = "") String id,
            @RequestParam(defaultValue = "") String questionId,
            @RequestParam(defaultValue = "") String questionText,
            @RequestParam(defaultValue = "") String questionType,
            @RequestParam(defaultValue = "") String questionChooseA,
            @RequestParam(defaultValue = "") String questionChooseB,
            @RequestParam(defaultValue = "") String questionChooseC,
            @RequestParam(defaultValue = "") String questionChooseD,
            @RequestParam(defaultValue = "") String questionAnswer,
            @RequestParam(defaultValue = "") String questionClassification,
            @RequestParam(defaultValue = "") String questionOther){

        logger.info("id-"+id);
        logger.info("questionId-"+questionId);
        logger.info(questionText);
        logger.info(questionType);
        logger.info(questionChooseA+questionChooseB+questionChooseC+questionChooseD);
        logger.info(questionAnswer+"");
        logger.info(questionClassification+"");
        logger.info(questionOther+"");
        logger.info(oper);
        boolean state=false;
        return "ss";
//        switch(oper){
//            case "add":
//                state=addUser(name,phone,areaId,sex);
//                break;
//            case "del":
//                System.out.println("ss");
//                state=delUser(id);
//                break;
//            case "edit":
//                state=editUser(examineeId,name,phone,areaId,sex);
//                break;
//            default:
//        }
//        if(state){
//            return "操作成功";
//        }else {
//            return "操作失败，字段为空";
//        }
//    }
//
//    /**
//     * 更新用户
//     * @param examineeId
//     * @param name
//     * @param phone
//     * @param areaId
//     * @param sex
//     * @return
//     */
//    private boolean editUser(String examineeId,String name,String phone,String areaId,String sex) {
//        if("".equals(phone)||phone.length()>11||"".equals(name)){
//            return false;
//        }
//        if(!examineeDao.updateById(examineeId,name,phone,areaId,sex)){
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 删除用户
//     * @param id
//     * @return
//     */
//    private boolean delUser(String id) {
//        if(!examineeDao.deleteById(id)){
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 添加用户
//     * @param name
//     * @param phone
//     * @param areaId
//     * @param sex
//     * @return
//     */
//    private boolean addUser(String name,String phone,String areaId,String sex) {
//        if("".equals(phone)||phone.length()>11||"".equals(name)){
//            return false;
//        }
//        ExamExamineeEntity examinee=new ExamExamineeEntity();
//        examinee.setExamineeId(examineeDao.newUsersId());
//        examinee.setName(name);
//        examinee.setPassword("123456");
//        examinee.setPhone(phone);
//        examinee.setIdentity("1");
//        examinee.setSex(sex);
//        examinee.setAreaId(areaId);
//        examineeDao.save(examinee);
//        return true;
    }
}

