package com.exam.common.dao;

import com.exam.common.entity.ExamAreaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LX on 2017/8/4.
 * 区域
 */
@Component
@Repository
public class AreaDao extends AbstractDao<ExamAreaEntity>{
    private Logger logger = LoggerFactory.getLogger(AreaDao.class);

    /**
     * 根据区域名获取区域Id
     * @param name
     * @return
     */
    public ExamAreaEntity findbyName(String name){
        List<ExamAreaEntity> entities=findBy("areaName",name);
        if(entities!=null){
            return entities.get(0);
        }else{
            return new ExamAreaEntity();
        }
    }
}
