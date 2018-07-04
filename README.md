# ExamManagerSystem
### 1.27 20170914
  1.返回上一题功能
  
  2.全局异常捕获
### 1.28 20170917
  1.管理页及考试页信息排序
  
  2.管理页中增加区域成绩快速查询（点击对应考试的小图标）
### 1.29 20170919
  1.成绩页面排序
   
  2.增加提交试卷的空值判断，解决IOS浏览器下使用BUG 
### 1.30-1.32 20171020
  1.重构身份验证模块
   
  2.修复认证提权BUG
  
 ### 1.36  20171031
  1.更新数据库表结构
  ```
  alter table exam_examination add examination_statistics datetime
  ```
 ### 1.37-1.38 20171117
  1.优化错题统计和区域统计逻辑，提高统计效率
 
 ### 1.39-1.40 20171227
   1.优化随机抽题算法，提高效率
   
   2.调整部分注释
 ### 1.41-1.43 20180703
   1.修改一个错误拼写
  ```
  ALTER TABLE exam_examination CHANGE signal_count single_count INT
  UPDATE exam_question SET question_type =REPLACE(question_type,'signal','single') where question_type = 'signal'
  ```
   2.强化登陆密码保存并提供修改密码功能
   ```
   ALTER TABLE exam_examinee MODIFY password VARCHAR(32)
   ALTER TABLE exam_examinee add salt varchar(32)
   UPDATE exam_examinee SET password = '281ab141a12f67f5238719cd876ce96e'
   UPDATE exam_examinee SET salt = 'e10adc3949ba59abbe56e057f20f883e'
   ```
   3.密码重置
 ### 1.44 20180704
   1.Excel导入工具