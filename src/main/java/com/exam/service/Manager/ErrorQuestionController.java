package com.exam.service.Manager;

import com.exam.common.EasyToken.EasyToken;
import com.exam.common.EasyToken.Token;
import com.exam.common.ErrorCode;
import com.exam.common.Response;
import com.exam.common.dao.AnswerLogDao;
import com.exam.common.dao.ExamPaperDao;
import com.exam.common.entity.ExamAnswerLogEntity;
import com.exam.common.entity.ExamExaminationPaperEntity;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.comparators.ComparatorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by LX on 2017/8/8.
 * 错题管理，正确率分析
 */
@RestController
@RequestMapping("/errorQuestion")
public class ErrorQuestionController {
    @Autowired
    ExamPaperDao examPaperDao;
    @Autowired
    AnswerLogDao answerLogDao;
    private Logger logger = LoggerFactory.getLogger(ErrorQuestionController.class);

    @GetMapping
    public Response errorQuestion(@CookieValue(value = "token", defaultValue = "") String token,
                                  @CookieValue(value = "userId", defaultValue = "") String uid,
                                  @RequestParam(defaultValue = "") String examinationId){
        String status=new EasyToken().checkToken(new Token(uid,token));
        if(status.equals("TIMEOUT")){
            return Response.error(ErrorCode.SYS_LOGIN_TIMEOUT);
        }else if(status.equals("ERROR")){
            return Response.error(ErrorCode.USER_ERROR);
        }else {
            /**
             * 根据考试Id获取试题列表，遍历试题列表，
             * 根据考试号试题号获取所有考生该考试该试题答题情况，遍历并统计
             */
            List<ExamExaminationPaperEntity> paperEntities=examPaperDao.findByexam(examinationId);
            if(paperEntities!=null&&paperEntities.size()!=0){
                for(ExamExaminationPaperEntity paper :paperEntities){
                    List<ExamAnswerLogEntity> answerLogEntities=answerLogDao.findByExamination(examinationId,paper.getQuestionId());
                    if(answerLogEntities!=null){
                        int sumcount=answerLogEntities.size();  //所有考生该考试该试题答题情况
                        int rightcount=0;
                        double accuracy=2;
                        for(ExamAnswerLogEntity answerLogEntity:answerLogEntities){
                            if(answerLogEntity.getScoreReal()!=0){
                                rightcount++;
                            }
                        }
                        if(sumcount!=0){
                            accuracy=(double) rightcount/sumcount;
                        }
                        examPaperDao.saveAccuracy(examinationId,paper.getQuestionId(),accuracy);//统计完成后保存正确率
                    }
                }
            }
            List<ExamExaminationPaperEntity> entities= examPaperDao.findBy("examinationId",examinationId,false);
            Iterator<ExamExaminationPaperEntity> iterator=entities.iterator();
            while (iterator.hasNext()){
                ExamExaminationPaperEntity entity=iterator.next();
                if(entity.getAccuracy()>1){
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
    }
}
