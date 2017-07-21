import com.exam.Application;
import com.exam.common.dao.ExaminationDao;
import com.exam.service.ExamInfoController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DaoTest {

    String userid = "213124128";

    @Autowired
    ExaminationDao creditDao;

    @Autowired
    ExamInfoController examInfoController;

    @Test
    public void get(){
        System.out.println(creditDao.findAll().size());
    }

    @Test
    public void test2(){
        System.out.println(examInfoController.findExam("111"));
    }
}
