package com.exam.common.dao;

import com.exam.common.entity.SysUserEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LX on 2017/7/27.
 * 管理员登陆
 */
@Component
@Repository
public class SysUserDao extends AbstractDao<SysUserEntity>{
    /**
     * 关键字段查询
     * @param str
     * @param value
     * @return
     */
    public SysUserEntity findByStr(String str, String value){
        List<SysUserEntity> list = super.findBy(str, value);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 新的Id
     * @return
     */
    public String newUsersId() {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery l = session.createSQLQuery("SELECT MAX(user_ID) FROM sys_user");
        if(l.list().get(0)==null){
            return "10000"; //初始值返回
        }else{
            String id = (String) l.list().get(0);
            Integer idd = Integer.valueOf(id);
            String newid = String.valueOf(idd + 1);
            return newid;
        }
    }
}
