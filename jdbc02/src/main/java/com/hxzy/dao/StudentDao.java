package com.hxzy.dao;

import com.hxzy.bean.Student;

import java.util.List;

/**
 * @author nick
 * @description
 * @date create in 2020/9/10
 */
public interface StudentDao {
    /**
     * 向数据库插入学生的对象
     * @param student
     * @return
     */
    int save(Student student);

    /**
     * 根据学号删除学生
     * @param id
     * @return
     */
    int remove(int id);

    /**
     * 根据学号修改学生信息
     * @param student
     * @return
     */
    int update(Student student);

    /**
     * 查询数据库students表中的所有数据
     * 将每行数据封装为Student对象并添加到集合
     * @return 所有学生对象的集合
     */
    List<Student> queryAll();

    /**
     * 根据学号查询学生数据
     * @param id 被查询的学号
     * @return 学生的对象
     */
    Student findById(int id);
}
