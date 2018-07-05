package com.exam.common.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * V1.0
 * Created by LX on 2018/7/4.
 * 参考自https://blog.csdn.net/wzwenhuan/article/details/18838821
 */
public class ExcelUtil {
    /**
     * 读取Excel并包装为目标类对象list
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
                    Class<?> fieldType=field.getType();
//                    if(field.getGenericType().toString().equals("class java.lang.Integer")){
//                        field.set(o,Integer.parseInt(value));
//                    }else {
//                        field.set(o,value);
//                    }
                    if (String.class == fieldType) {
                        field.set(o, String.valueOf(value));
                    } else if ((Integer.TYPE == fieldType)
                            || (Integer.class == fieldType)) {
                        field.set(o, Integer.parseInt(value.toString()));
                    } else if ((Long.TYPE == fieldType)
                            || (Long.class == fieldType)) {
                        field.set(o, Long.valueOf(value.toString()));
                    } else if ((Float.TYPE == fieldType)
                            || (Float.class == fieldType)) {
                        field.set(o, Float.valueOf(value.toString()));
                    } else if ((Short.TYPE == fieldType)
                            || (Short.class == fieldType)) {
                        field.set(o, Short.valueOf(value.toString()));
                    } else if ((Double.TYPE == fieldType)
                            || (Double.class == fieldType)) {
                        field.set(o, Double.valueOf(value.toString()));
                    } else if (Character.TYPE == fieldType) {
                        if ((value!= null) && (value.toString().length() > 0)) {
                            field.set(o, Character
                                    .valueOf(value.toString().charAt(0)));
                        }
                    }else if(Date.class==fieldType){
                        field.set(o, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.toString()));
                    }else{
                        field.set(o, value);
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

    public static String exportExcel(String fileName, List<?> values, LinkedHashMap<String, String> fieldMap, String filePath) throws Exception{
        if (fileName == null || fileName == "") {
            fileName="temp";
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(fileName);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        fillSheet(sheet, values, fieldMap, style);//数据填充
        //文件输出
        File fileDir = new File(filePath);
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(filePath + "/" + fileName + ".xls");
        wb.write(fileOutputStream);
        fileOutputStream.close();
//        JSONObject reData = new JSONObject();
//        reData.put("url", filePath + "\\" + fileName + ".xls");
//        reData.put("fileName", fileName);
//        return reData;
        return filePath + "\\" + fileName + ".xls";
    }

    /**
     * 向工作表中填充数据
     * @param sheet    excel的工作表名称
     * @param list     数据源
     * @param fieldMap 中英文字段对应关系的Map
     * @param style    表格中的格式
     * @throws Exception 异常
     */
    public static void fillSheet(HSSFSheet sheet, List<?> list, LinkedHashMap<String, String> fieldMap, HSSFCellStyle style) throws Exception {
        // 定义存放英文字段名和中文字段名的数组
        String[] enFields = new String[fieldMap.size()];
        String[] cnFields = new String[fieldMap.size()];

        // 填充数组
        int count = 0;
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            enFields[count] = entry.getKey();
            cnFields[count] = entry.getValue();
            count++;
        }

        //在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);

        // 填充表头
        for (int i = 0; i < cnFields.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(cnFields[i]);
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
        }

        // 填充内容
        for (int index = 0; index < list.size(); index++) {
            row = sheet.createRow(index + 1);
            // 获取单个对象
            Object item = list.get(index);
            for (int i = 0; i < enFields.length; i++) {
                Object objValue = getFieldValueByNameSequence(enFields[i], item);
                String fieldValue = objValue == null ? "" : objValue.toString();

                row.createCell(i).setCellValue(fieldValue);
            }
        }
    }
    /**
     * 根据字段名获取字段对象
     * @param fieldName 字段名
     * @param clazz     包含该字段的类
     * @return 字段
     */
    private static Field getFieldByName(String fieldName, Class<?> clazz) {
        Field[] selfFields = clazz.getDeclaredFields();
        for (Field field : selfFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && superClazz != Object.class) {
            return getFieldByName(fieldName, superClazz);
        }
        return null;
    }

    /**
     * 根据字段名获取字段值
     * @param fieldName 字段名
     * @param o         对象
     * @return 字段值
     * @throws Exception 异常
     */
    private static Object getFieldValueByName(String fieldName, Object o) throws Exception {
        Object value = null;
        Field field = getFieldByName(fieldName, o.getClass());
        if (field != null) {
            field.setAccessible(true);
            value = field.get(o);
        } else {
            throw new Exception(o.getClass().getSimpleName() + "类不存在字段名 " + fieldName);
        }
        return value;
    }

    /**
     * 根据带路径或不带路径的属性名获取属性值,即接受简单属性名，
     * 如userName等，又接受带路径的属性名，如student.department.name等
     * @param fieldNameSequence 带路径的属性名或简单属性名
     * @param o                 对象
     * @return 属性值
     * @throws Exception 异常
     */
    private static Object getFieldValueByNameSequence(String fieldNameSequence, Object o) throws Exception {
        Object value = null;
        String[] attributes = fieldNameSequence.split("\\.");
        if (attributes.length == 1) {
            value = getFieldValueByName(fieldNameSequence, o);
        } else {
            Object fieldObj = getFieldValueByName(attributes[0], o);
            String subFieldNameSequence = fieldNameSequence.substring(fieldNameSequence.indexOf(".") + 1);
            value = getFieldValueByNameSequence(subFieldNameSequence, fieldObj);
        }
        //日期格式化
        if (value instanceof Date) {
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            value = date.format(value);
        }
        return value;
    }
}
