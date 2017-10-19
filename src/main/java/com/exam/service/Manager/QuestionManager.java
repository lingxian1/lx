package com.exam.service.Manager;

import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.QuestionDao;
import com.exam.common.entity.ExamQuestionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by LX on 2017/7/28.
 * 试题管理
 */
@RestController
@RequestMapping("/questionsManager")
public class QuestionManager {
    @Autowired
    QuestionDao questionDao;

    private Logger logger = LoggerFactory.getLogger(QuestionManager.class);

    /**
     * 试题分类获取
     * @param info
     * @return
     */
    @GetMapping
    public Response getUser(@RequestParam(defaultValue = "")String info){
//        logger.info(userId);
//        logger.info(token);
//        logger.info(str);
//        logger.info(info);

            if(info.length()>20){
                return Response.error(ErrorCode.QUESTION_TYPE_LENGTH_ERROR);
            }
             List<ExamQuestionEntity> entities= questionDao.findQuestionClass("questionClassification",info);
             if(entities==null){
                 return Response.ok("该类型不存在");
             }
            return Response.ok(entities);

    }

    /**
     * 获取分类数据
     * @return
     */
    @GetMapping("/classification")
    public Response getQuestionClass(){
        return Response.ok(questionDao.questionClass());
    }
    /**
     * 处理增删改请求
     * @param oper
     * @param id
     * @param questionId
     * @param questionText
     * @param questionType
     * @param questionChooseA
     * @param questionChooseB
     * @param questionChooseC
     * @param questionChooseD
     * @param questionAnswer
     * @param questionClassification
     * @param questionOther
     * @return
     */
    @PostMapping("/handle")
    public Response login(
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


            int count = 2;
            boolean state = false;
            if (!"".equals(questionChooseC)) {
                count++;
            }
            if (!"".equals(questionChooseD)) {
                count++;
            }

            switch (oper) {
                case "add":
                    state = addQuestion(questionText, questionType, count, questionChooseA, questionChooseB,
                            questionChooseC, questionChooseD, questionAnswer, questionClassification, questionOther);
                    break;
                case "del":
                    state = delQuestion(id);
                    break;
                case "edit":
                    state = editQuestion(questionId, questionText, questionType, count, questionChooseA, questionChooseB,
                            questionChooseC, questionChooseD, questionAnswer, questionClassification, questionOther);
                    break;
                default:
            }
            if (state) {
                return Response.ok("操作成功");
            } else {
                return Response.error();
            }
        }


    /**
     * 编辑试题
     * @param questionId
     * @param questionText
     * @param questionType
     * @param count
     * @param questionChooseA
     * @param questionChooseB
     * @param questionChooseC
     * @param questionChooseD
     * @param questionAnswer
     * @param questionClassification
     * @param questionOther
     * @return
     */
    private boolean editQuestion(String questionId,String questionText,String questionType,int count,
                                 String questionChooseA,String questionChooseB,String questionChooseC,String questionChooseD,
                                 String questionAnswer,String questionClassification,String questionOther) {
        if(!questionDao.updateById(questionId,questionText,questionType,count,questionChooseA,
                questionChooseB,questionChooseC,questionChooseD,questionAnswer,questionClassification,
                questionOther)){
            return false;
        }
        return true;
    }

    /**
     * 删除试题
     * @param id
     * @return
     */
    private boolean delQuestion(String id) {
        if(!questionDao.deleteById(id)){
            return false;
        }
        return true;
    }

    /**
     * 添加试题
     * @param questionText
     * @param questionType
     * @param questionChooseCount
     * @param questionChooseA
     * @param questionChooseB
     * @param questionChooseC
     * @param questionChooseD
     * @param questionAnswer
     * @param questionClassification
     * @param questionOther
     * @return
     */
    private boolean addQuestion(String questionText,String questionType,int questionChooseCount,
                                String questionChooseA,String questionChooseB,String questionChooseC,String questionChooseD,
                                String questionAnswer,String questionClassification,String questionOther) {
        ExamQuestionEntity questionEntity=new ExamQuestionEntity();
        String id= questionDao.newQuestionId();
        questionEntity.setQuestionId(id);
        questionEntity.setQuestionText(questionText);
        questionEntity.setQuestionType(questionType);
        questionEntity.setQuestionChooseCount(questionChooseCount);
        questionEntity.setQuestionChooseA(questionChooseA);
        questionEntity.setQuestionChooseB(questionChooseB);
        questionEntity.setQuestionChooseC(questionChooseC);
        questionEntity.setQuestionChooseD(questionChooseD);
        questionEntity.setQuestionAnswer(questionAnswer);
        questionEntity.setQuestionClassification(questionClassification);
        questionEntity.setIsDel("00");
        questionEntity.setQuestionCreateTime(new Timestamp(System.currentTimeMillis()));
        questionEntity.setQuestionOther(questionOther);
        questionDao.save(questionEntity);
        return true;
    }

    /**
     * 根据试题Id返回试题详情--错题管理
     * @param questionId
     * @return
     */
    @GetMapping("aQuestion")
    public Response aQuestion(@RequestParam(defaultValue = "") String questionId){
            return Response.ok(questionDao.findById(questionId));
    }
}

