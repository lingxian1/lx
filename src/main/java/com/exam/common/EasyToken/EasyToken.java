package com.exam.common.EasyToken;

import com.exam.common.util.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by LX on 2017/7/26.
 * 用于管理端请求验证
 * 惰性验证
 */
public class EasyToken implements Runnable {
    public static Map<Token,Long> tokens=new HashMap<>(); //非线程安全
    public static long checkTime=0;//检查点
    final static long blankTime=600000; //10分钟检查一次
    final static long outTimes=7200000;//毫秒
//    final static long outTimes=1;//毫秒
    private Logger logger = LoggerFactory.getLogger(EasyToken.class);

    /**
     * 检测移除过期token,新的登入产生时且超过检查点后在子线程触发
     */
    public void checkAll(){
        logger.info("token check now");
        Long nowTime=System.currentTimeMillis();
        Iterator<Map.Entry<Token,Long>> it=tokens.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<Token,Long> entry=it.next();
            String log=entry.getKey().getUid();
            Long outTime= entry.getValue().longValue();
            if(nowTime>outTime){
                it.remove();
                logger.info("a token was removed "+log);
            }
        }
    }

    /**
     * 登陆获取
     * @param uid
     * @param ups
     * @return
     */
    public Token createToken(String uid,String ups){
        long nowTime=System.currentTimeMillis();
        String tokenStr= Md5Utils.stringMD5(uid+ups+nowTime);
        Token token=new Token(uid,tokenStr);
        tokens.put(token,nowTime+outTimes);
        if(nowTime>checkTime){
//            System.out.println(checkTime);
            checkTime=nowTime+blankTime;
            new Thread(this).start();
        }

        return token;
    }

    /**
     * 请求验证
     * @param token
     * @return
     */
    public String checkToken(Token token){
        for (Map.Entry<Token, Long> entry : tokens.entrySet()) {
            if(entry.getKey().equals(token)){ //重写了equals
                Long outTime= entry.getValue().longValue();
                Long nowTime=System.currentTimeMillis();
                if(nowTime>outTime){
                    return "TIME_OUT";
                }
                return "TRUE";
            }
        }
       return "ERROR";
    }

    public void deleteToken(Token token){
    }

    @Override
    public void run() {
        checkAll();
    }
}
