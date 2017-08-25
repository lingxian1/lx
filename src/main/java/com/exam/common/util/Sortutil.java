package com.exam.common.util;

/**
 * Created by LX on 2017/8/25.
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;

//对list中的对象的成员变量进行排序
public class Sortutil {
    static int i=-1;  //顺序 1升序 -1降序
    public static void sorts(List<?> list, String s) {
        try {
            Class class1 = list.get(0).getClass(); // 获取一个非空list中 对象的类型信息
            String type = class1.getDeclaredField(s).getType().toString(); // 根据成员变量名获取变量类型
            // class1.getDeclaredField(s)获取Field对象
            String reg_s = ".*String.*"; // 用于匹配
            System.out.println(type);
            String t=s.replace(s.substring(0, 1), s.substring(0, 1).toUpperCase());
            Method method = class1.getMethod("get" + t); // 获取对应get方法
            // 以下执行比较 根据不同变量类型 支持String,int,long
            if (type.matches(reg_s)) {
                sortbystring(method, list);
            } else if (type.equals("int")||type.equals("class java.lang.Integer")) {
                sortbyint(method, list);
            } else if (type.equals("long")) {
                sortbylong(method, list);
            } else {
                System.out.println("not support type");
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void sortbystring(Method method, List<?> list) {
        list.sort(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                try {
                    return (((String) method.invoke(o1, new Object[] {}))
                            .compareTo((String) method.invoke(o2, new Object[] {})));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static void sortbyint(Method method, List<?> list) {
        list.sort(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                int value = 0;
                try {
                    Integer integer1 = (Integer) method.invoke(o1, new Object[] {});
                    Integer integer2 = (Integer) method.invoke(o2, new Object[] {});
                    value = integer1 - integer2;
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                if (value == 0) {
                    return 0;
                } else if (value > 0)
                    return i;
                else
                    return -i;
            }
        });
    }

    public static void sortbylong(Method method, List<?> list) {
        list.sort(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                long value = 0;
                try {
                    Long long1 = (Long) method.invoke(o1, new Object[] {});
                    Long long2 = (Long) method.invoke(o2, new Object[] {});
                    value = long1 - long2;
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                if (value == 0) {
                    return 0;
                } else if (value > 0)
                    return i;
                else
                    return -i;
            }
        });
    }

}
