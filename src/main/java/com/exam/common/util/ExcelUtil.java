package com.exam.common.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * V0.5
 * Created by LX on 2018/7/4.
 */
public class ExcelUtil {
    /**
     * 读取Excel并包装为目标类list 目前仅支持Integer String
     * @param input 文件流
     * @param title 表头所在行 从0开始
     * @param start 开始扫描的行 从0开始
     * @param clazz 目标类
     * @return
     * @throws Exception
     */
    public static List<?> readExcel(InputStream input, int title, int start, Class<?> clazz) throws Exception  {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(input);
            Sheet sheet = wb.getSheetAt(0); // 获得第一个表单
            int totalRow = sheet.getLastRowNum();// 得到excel的总记录条数
            int columtotal = sheet.getRow(title).getPhysicalNumberOfCells();// 表头总共的列数
            List<String> titles = new ArrayList<>();// 保存表头所指向的属性名
            List<Object> objects = new ArrayList<>();// 返回对象
            for(int k=0;k<columtotal;k++){
                sheet.getRow(title).getCell(k).setCellType(Cell.CELL_TYPE_STRING);
                String name=sheet.getRow(title).getCell(k).getStringCellValue();
                titles.add(name);
            }
            System.out.println("总行数:" + totalRow + ",总列数:" + columtotal);
            for (int i = start; i <= totalRow; i++) {// 遍历行
                Object o = clazz.newInstance();// 1.新建目标对象
                for (int j = 0; j < columtotal; j++) {
                    sheet.getRow(i).getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                    String value=sheet.getRow(i).getCell(j).getStringCellValue();
                    //反射，2.根据顺序获取目标属性值3.赋值4.放入objects list中
                    Field field = clazz.getDeclaredField(titles.get(j));
                    field.setAccessible(true);
                    // 类型支持
                    if(field.getGenericType().toString().equals("class java.lang.Integer")){
                        field.set(o,Integer.parseInt(value));
                    }else {
                        field.set(o,value);
                    }
                }
                objects.add(o);
            }
            return objects;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex);
        }finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
