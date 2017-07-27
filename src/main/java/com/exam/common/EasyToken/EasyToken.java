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
 */
public class EasyToken implements Runnable {
    public static Map<Token,Long> tokens=new HashMap<>();
    final static long outTimes=7200000;//毫秒
//    final static long outTimes=1;//毫秒
    private Logger logger = LoggerFactory.getLogger(EasyToken.class);

    //检测移除过期token
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

    //登陆获取
    public Token getToken(String uid,String ups){
        String tokenStr= Md5Utils.stringMD5(uid+ups+System.currentTimeMillis());
        Token token=new Token(uid,tokenStr);
        tokens.put(token,System.currentTimeMillis()+outTimes);
        new Thread(this).start();
        return token;
    }

    //请求验证
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
