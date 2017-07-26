package com.exam.common.EasyToken;

import com.exam.common.util.Md5Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LX on 2017/7/26.
 * 用于管理端请求验证
 */
public class EasyToken implements Runnable {
    public static Map<Token,Long> tokens=new HashMap<>();
    final static long outTime=7200000;//毫秒
    public void checkAll(){
        System.out.println("checkNow");
    }
    //
    public Token getToken(String uid,String ups){
        String tokenStr= Md5Utils.stringMD5(uid+ups+System.currentTimeMillis());
        Token token=new Token(uid,tokenStr);
        tokens.put(token,System.currentTimeMillis()+outTime);
        new Thread(this).start();
        return token;
    }

    public String checkToken(Token token){
        for (Map.Entry<Token, Long> entry : tokens.entrySet()) {
            if(entry.getKey().equals(token)){
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

    public void deleteToken(){

    }

    @Override
    public void run() {
        checkAll();
    }
}
