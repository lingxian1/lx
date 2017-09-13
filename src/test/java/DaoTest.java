import com.exam.Application;
import com.exam.common.dao.*;
import com.exam.common.util.RandomUtil;
import com.exam.service.ExamController;
import com.exam.service.ExamInfoController;
import com.exam.service.GradeController;
import com.exam.service.Manager.AdminLoginController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.List;


@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DaoTest{

    String userid = "213124128";

    @Autowired
    ExaminationDao creditDao;

    @Autowired
    ExamInfoController examInfoController;

    @Autowired
    ExamController examController;

    @Autowired
    GradeDao gradeDao;

    @Autowired
    QuestionDao questionDao;

    @Autowired
    AnswerLogDao answerLogDao;

    @Autowired
    ExaminationDao examinationDao;
    @Autowired
    GradeController gradeController;
    @Autowired
    AdminLoginController adminLoginController;

    @Autowired
    ExamPaperDao examPaperDao;
    @Autowired
    ExamineeDao examineeDao;
    @Test
    public void get(){
        System.out.println(creditDao.findAll().size());
    }

//    @Test
//    public void test2(){
//        System.out.println(examInfoController.findExam("111"));
//    }

//    @Test
//    public void test3(){
//        System.out.println(examController.findExamQuestion("123456","2017070701"));
//    }
//
//    @Test
//    public void test4(){
//        System.out.println(gradeDao.findGrade("11111","22222").getGrade());
//    }

    @Test
    public void test5(){
        System.out.println(answerLogDao.getGrade("1000012356","2017081001"));
        System.out.println(answerLogDao.getGrade("1000012356","20170810001"));
    }

//    @Test
//    public void test6(){
//        System.out.println(gradeController.findGrades("1000012347"));
//    }

//    @Test
//    public void test7(){
//        System.out.println(new EasyToken().createToken("123456","123456"));
////        System.out.println(new EasyToken().getToken("1235663","123456"));
//        System.out.println(new EasyToken().checkToken(new Token("123456","123456")));
//    }
//
//    @Test
//    public void test8(){
//        System.out.println(new EasyToken().createToken("10001","123456"));
////        System.out.println(new EasyToken().getToken("1235663","123456"));
//        System.out.println(new EasyToken().checkToken(new Token("10001","123456")));
//        System.out.println(new EasyToken().createToken("10002","123456"));
//        System.out.println(new EasyToken().checkToken(new Token("10001","123456")));
//    }
//
//    @Test
//    public void test9(){
//        System.out.println(adminLoginController.AdminLogin("10000000","5555555"));
//        System.out.println(adminLoginController.AdminLogin("10001","5555555"));
//        System.out.println(adminLoginController.AdminLogin("10002","123456"));
//    }
//
//    @Test
//    public void test10(){
//        System.out.println(examinationDao.getNewId());
//    }
//
//    @Test
//    public void test11(){
//        System.out.println(examPaperDao.findByexamCount("2017070701"));
//        System.out.println(examPaperDao.findByexamCount("2017070702"));
//    }
//
    @Test
    public void test12(){
        System.out.println(examPaperDao.sumScore("2017081001"));
        System.out.println(examPaperDao.sumScore("2017070702"));
    }
//
//    @Test
//    public void test13(){
//        Iterator<ExamQuestionEntity> iterator= questionDao.findQuestions("",0,"all").iterator();
//        while (iterator.hasNext()){
//            ExamQuestionEntity temp=iterator.next();
//            System.out.println(temp.getQuestionId());
//        }

//        System.out.println(examPaperDao.sumScore("2017070702"));
//    }

    @Test
    public void test14(){
//        if(examinationDao.findById("2017081001").getJudgementCount()==null){
//            System.out.println("it is null");
//        }
//        Integer d=null;
//        System.out.println(d);
//       List<ExamExaminationPaperEntity> list= examPaperDao.findByexamRandom("2017081002",3,1,0);
//       for(ExamExaminationPaperEntity entity:list){
//           System.out.println(entity.getExaminationId()+"  "+entity.getQuestionId()+"---"+entity.getScore());
//       }
        for(int j=0;j<10;j++){
            List<Integer> list= RandomUtil.getRandom(0,2,1);
            for(Integer i:list){
                System.out.println(i);
            }
        }

        for(int j=0;j<10;j++){
            List<Integer> list= RandomUtil.getRandom(1,2,1);
            for(Integer i:list){
                System.out.println(i);
            }
        }

        for(int j=0;j<10;j++){
            List<Integer> list= RandomUtil.getRandom(0,3,2);
            for(Integer i:list){
                System.out.println(i);
            }
        }

        for(int j=0;j<10;j++){
            List<Integer> list= RandomUtil.getRandom(1,7,2);
            for(Integer i:list){
                System.out.println(i);
            }
        }
        List<Integer> list= RandomUtil.getRandom(1,1,-1);
        for(Integer i:list){
            System.out.println(i);
        }
        List<Integer> list2= RandomUtil.getRandom(1,1,0);
        for(Integer i:list2){
            System.out.println(i);
        }
    }
    @Test
    public void test15(){
        questionDao.questionClass();
    }

    @Test
    public void test16() {
        Timestamp nowtime=new Timestamp(System.currentTimeMillis());

        System.out.println(nowtime.getTime());

    }
}


