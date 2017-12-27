package com.exam.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by LX on 2017/8/14.
 * 随机数工具 
 */
public class RandomUtil {
    /**
     * 产生num个[x--y)之间的随机数
     * @param x
     * @param y
     * @param num
     * @return
     */
   public static List<Integer> getRandom(int x, int y, int num) {
        List<Integer> a = new ArrayList();
        //数据检测
        if(num>y-x||num<0){
            System.out.println("error");
            return new ArrayList<>();
        }

        for(int i=x;i<y;i++){
           a.add(i);
        }
        Collections.shuffle(a);
        for(int j=a.size()-1;j>=num;j--){
            a.remove(j);
        }
//        for (Integer t:a
//            ) {
//           System.out.println(t);
//        }
        return a;
    }
}
