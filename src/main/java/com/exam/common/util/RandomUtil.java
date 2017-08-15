package com.exam.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        int index = 0;
        //防止死循环
        if(num>y-x){
            return new ArrayList<>();
        }
        while(index < num) {
            //产生x-y的随机数
            Random r = new Random();
            int temp = r.nextInt(y-x)+x ;
            //设置是否重复的标记变量为false
            boolean flag = false;
            for(int i =0; i<index;i++){
                if(temp == a.get(i)){
                    flag = true;
                    break;
                }
            }
            if(flag==false){
                a.add(temp);
                index++;
            }
        }
        return a;
    }
}
