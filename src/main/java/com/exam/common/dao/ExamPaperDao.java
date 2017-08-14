package com.exam.common.dao;

import com.exam.common.entity.ExamExaminationPaperEntity;
import com.exam.common.util.RandomUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by LX on 2017/7/24.
 * 每场考试的试题管理
 */
@Component
@Repository
public class ExamPaperDao extends AbstractDao<ExamExaminationPaperEntity>{
    @Autowired
    QuestionDao questionDao;
    /**
     * 根据考试号寻找题目 like--false
     * @param examinationId
     * @return
     */
    public List<ExamExaminationPaperEntity> findByexam(String examinationId){
        return findBy("examinationId",examinationId,false);
    }


    /**
     * 随机生成试题
     * s m j
     * 1.遍历试题数组 用3个list保存各分类的分布位置
     * 2.调用随机数工具进行随机抽取
     * 3.将抽取的结果放入结果list
     * @param examinationId
     * @param s  成分--单选
     * @param m  成分--多选
     * @param j  成分--判断
     * @return
     */
    public List<ExamExaminationPaperEntity> findByexamRandom(String examinationId,int s,int m,int j){
        List<ExamExaminationPaperEntity> entities=findByexam(examinationId);
        List<ExamExaminationPaperEntity> result=new ArrayList<>();
        List<Integer> lists=new ArrayList<>(); //记录单选题位置信息 比如list(0)->1,list(1)->3,list(2)->4
        List<Integer> listm=new ArrayList<>();
        List<Integer> listj=new ArrayList<>();
        for(int i=0;i<entities.size();i++){
            ExamExaminationPaperEntity entity=entities.get(i);
            String type=questionDao.findById(entity.getQuestionId()).getQuestionType();
            if("signal".equals(type)){
                lists.add(i);
            }else if("multiple".equals(type)){
                listm.add(i);
            }else if("judgement".equals(type)){
                listj.add(i);
            }
        }
        //获取随机数
        List<Integer> list1=RandomUtil.getRandom(0,lists.size(),s);
        List<Integer> list2=RandomUtil.getRandom(0,listm.size(),m);
        List<Integer> list3=RandomUtil.getRandom(0,listj.size(),j);
        //添加结果集
        for(Integer index:list1){
            result.add(entities.get(lists.get(index)));
        }
        for(Integer index:list2){
            result.add(entities.get(listm.get(index)));
        }
        for(Integer index:list3){
            result.add(entities.get(listj.get(index)));
        }
        return result;
    }
    /**
     * 保存正确率
     * @param examinationId
     * @param questionId
     * @param accuracy
     */
    public void saveAccuracy(String examinationId,String questionId,double accuracy){
        ExamExaminationPaperEntity entity=findScore(examinationId,questionId);
        if(entity!=null){
            entity.setAccuracy(accuracy);
            save(entity);
        }else{
            logger.info("saveAccuracy is null");
        }
    }
    /**
     * 某场考试已绑定题目数量
     * @param examinationId
     * @return
     */
    public int findByexamCount(String examinationId){
        int count=0;
        List<ExamExaminationPaperEntity> entities=findByexam(examinationId);
        if(entities!=null){
            count=entities.size();
        }
        return count;
    }

    /**
     * 获得某道题分值及正确率
     * @param examinationId
     * @param questionId
     * @return
     */
    public ExamExaminationPaperEntity findScore(String examinationId,String questionId){
        Map<String,String> str =new HashMap<>();
        str.put("examinationId",examinationId);
        str.put("questionId",questionId);
        ExamExaminationPaperEntity entity=findByIds(str);
        if(entity==null){
           logger.info("findScore:entity is null");
        }
        return entity;
    }

    /**
     * 某场考试当前绑定试题总分
     * @param examintionId
     * @return
     */
    public int sumScore(String examintionId){
        int sum=0;
        Session session = sessionFactory.getCurrentSession();
        StringBuilder builder = new StringBuilder();
        String sql = builder.append("SELECT SUM(score) FROM exam_examination_paper WHERE examination_ID='")
                .append(examintionId)
                .append("' ")
                .toString();
        SQLQuery l = session.createSQLQuery(sql);
        if(l.list().get(0)!=null){
            sum= new Integer(l.list().get(0).toString());
        }
        return sum;
    }

    /**
     * 根据考试号删除所有绑定试题
     * @param examinationId
     */
    public void deleteAllQuestion(String examinationId){
        List<ExamExaminationPaperEntity> list =findBy("examinationId",examinationId);
        for(ExamExaminationPaperEntity entity:list){
            delete(entity);
        }
    }

}
