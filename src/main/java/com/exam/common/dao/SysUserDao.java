package com.exam.common.dao;

import com.exam.common.entity.SysUserEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by LX on 2017/7/27.
 */
@Component
@Repository
public class SysUserDao extends AbstractDao<SysUserEntity>{

    public String newUsersId() {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery l = session.createSQLQuery("SELECT MAX(user_ID) FROM sys_user");
        String id = (String) l.list().get(0);
        Integer idd = Integer.valueOf(id);
        String newid = String.valueOf(idd + 1);
        return newid;
    }
}
