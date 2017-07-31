package com.exam.service.Manager;

import com.exam.common.dao.QuestionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LX on 2017/7/31.
 * 考试管理
 */
@RestController
@RequestMapping("/examManager")
public class ExamManager {

    @Autowired
    QuestionDao questionDao;

    private Logger logger = LoggerFactory.getLogger(QuestionManager.class);

    @PostMapping
    public void create(){
        //创建考试
    }
}
