package com.exam.service.Manager;

import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.AnswerLogDao;
import com.exam.common.dao.ExamPaperDao;
import com.exam.common.dao.ExaminationDao;
import com.exam.common.entity.ExamAnswerLogEntity;
import com.exam.common.entity.ExamExaminationEntity;
import com.exam.common.entity.ExamExaminationPaperEntity;
import com.exam.common.util.Assert;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.comparators.ComparatorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by LX on 2017/8/8.
 * 错题管理，正确率分析
 */
@RestController
@RequestMapping("/errorQuestionManager")
public class ErrorQuestionController {
    @Autowired
    ExamPaperDao examPaperDao;
    @Autowired
    AnswerLogDao answerLogDao;
    @Autowired
    ExaminationDao examinationDao;
    private Logger logger = LoggerFactory.getLogger(ErrorQuestionController.class);

    @GetMapping
    public Response errorQuestion(@RequestParam(defaultValue = "") String examinationId) {
        Assert.isFalse("".equals(examinationId) || examinationId.length() > 10, ErrorCode.EXAM_ID_ERROR);//断言
//            if("".equals(examinationId)||examinationId.length()>10){
//                return Response.error(ErrorCode.EXAM_ID_ERROR);
//            }
            /**
             * 1.判断考试统计时间是否为考试结束后
             * 2.未结束，统计并保存数据库，保存统计时间
             * 3.结束，直接读取数据
             */
            ExamExaminationEntity examinationEntity=examinationDao.findById(examinationId);
            if(examinationEntity==null){
                return Response.error(ErrorCode.EXAM_ID_ERROR);
            }

            Timestamp statisticsTime=examinationEntity.getExaminationStatistics(); //统计时间
            Timestamp endTime=examinationEntity.getExaminationEnd(); //结束时间
            Timestamp nowTime=new Timestamp(System.currentTimeMillis());
            if(statisticsTime==null||statisticsTime.getTime()<endTime.getTime()+15 * 60 * 1000){
                statistics(examinationId,2.0);
                // 保存统计时间
                examinationEntity.setExaminationStatistics(nowTime);
                examinationDao.update(examinationEntity);
            }


        //移除大于1的未使用试题
        List<ExamExaminationPaperEntity> entities = examPaperDao.findBy("examinationId", examinationId, false);
        Iterator<ExamExaminationPaperEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            ExamExaminationPaperEntity entity = iterator.next();
            if (entity.getAccuracy() > 1) {
                iterator.remove();
            }
        }
        //升序
        Comparator mycmp2 = ComparableComparator.getInstance();
        mycmp2 = ComparatorUtils.nullHighComparator(mycmp2); //允许null
        // 声明要排序的对象的属性，并指明所使用的排序规则，如果不指明，则用默认排序
        ArrayList<Object> sortFields = new ArrayList<Object>();
        sortFields.add(new BeanComparator("accuracy", mycmp2));
        // 创建一个排序链
        ComparatorChain multiSort = new ComparatorChain(sortFields);
        // 开始真正的排序，按照先主，后副的规则
        Collections.sort(entities, multiSort);
        return Response.ok(entities);
    }


    /**
     * 根据考试Id获取试题列表，遍历试题列表，
     * 根据考试号试题号获取所有考生该考试该试题答题情况，遍历并统计
     *
     * @param examinationId
     * @param accuracy
     */
    public void statistics(String examinationId, double accuracy) {
        List<ExamExaminationPaperEntity> paperEntities = examPaperDao.findByexam(examinationId);
        Assert.listExist(paperEntities, ErrorCode.NO_SUCH_EXAM);
        if (paperEntities != null && paperEntities.size() != 0) {
            for (ExamExaminationPaperEntity paper : paperEntities) {
                List<ExamAnswerLogEntity> answerLogEntities = answerLogDao.findByExamination(examinationId, paper.getQuestionId());
                if (answerLogEntities != null) {
                    int sumcount = answerLogEntities.size();  //所有考生该考试该试题答题情况
                    int rightcount = 0;
                    double t = accuracy;
                    for (ExamAnswerLogEntity answerLogEntity : answerLogEntities) {
                        if (answerLogEntity.getScoreReal() != 0) {
                            rightcount++;
                        }
                    }
                    if (sumcount != 0) {
                        t = (double) rightcount / sumcount;
                    }
                    examPaperDao.saveAccuracy(examinationId, paper.getQuestionId(), t);//统计完成后保存正确率
                }
            }
        }
    }
}
