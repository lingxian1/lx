import com.exam.Application;
import com.exam.common.EasyToken.EasyToken;
import com.exam.common.EasyToken.Token;
import com.exam.common.dao.AnswerLogDao;
import com.exam.common.dao.ExaminationDao;
import com.exam.common.dao.GradeDao;
import com.exam.common.dao.QuestionDao;
import com.exam.service.Manager.AdminLoginController;
import com.exam.service.ExamController;
import com.exam.service.ExamInfoController;
import com.exam.service.GradeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


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
    @Test
    public void get(){
        System.out.println(creditDao.findAll().size());
    }

//    @Test
//    public void test2(){
//        System.out.println(examInfoController.findExam("111"));
//    }

    @Test
    public void test3(){
        System.out.println(examController.findExamQuestion("123456","2017070701"));
    }

    @Test
    public void test4(){
        System.out.println(gradeDao.findGrade("11111","22222").getGrade());
    }

    @Test
    public void test5(){
        System.out.println(answerLogDao.getGrade("1000012345","2017070701"));
    }

    @Test
    public void test6(){
        System.out.println(gradeController.findGrades("1000012347"));
    }

    @Test
    public void test7(){
        System.out.println(new EasyToken().createToken("123456","123456"));
//        System.out.println(new EasyToken().getToken("1235663","123456"));
        System.out.println(new EasyToken().checkToken(new Token("123456","123456")));
    }

    @Test
    public void test8(){
        System.out.println(new EasyToken().createToken("10001","123456"));
//        System.out.println(new EasyToken().getToken("1235663","123456"));
        System.out.println(new EasyToken().checkToken(new Token("10001","123456")));
        System.out.println(new EasyToken().createToken("10002","123456"));
        System.out.println(new EasyToken().checkToken(new Token("10001","123456")));
    }

    @Test
    public void test9(){
        System.out.println(adminLoginController.AdminLogin("10000000","5555555"));
        System.out.println(adminLoginController.AdminLogin("10001","5555555"));
        System.out.println(adminLoginController.AdminLogin("10002","123456"));
    }

    @Test
    public void test10(){
//        System.out.println(questionDao.findQuestionClass("A").size());
//        System.out.println(questionDao.findQuestionClass("BB").size());
//        System.out.println(questionDao.findQuestionClass("C").size());
//        System.out.println(questionDao.findQuestionClass("D"));
    }

}


