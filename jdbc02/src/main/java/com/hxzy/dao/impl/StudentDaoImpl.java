package com.hxzy.dao.impl;

import com.hxzy.bean.Student;
import com.hxzy.dao.StudentDao;
import com.hxzy.util.JdbcUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author nick
 * @description
 * @date create in 2020/9/10
 */
public class StudentDaoImpl implements StudentDao {
    @Override
    public int save(Student student) {
        String sql = "INSERT INTO students (stuname,age,gender) values (?,?,?)";
        Object[] params = {student.getStuName(),student.getAge(),student.getGender()};
        return JdbcUtils.executeUpdate(sql,params);
    }

    @Override
    public int remove(int id) {
        String sql = "DELETE FROM students where id = ?";
        return JdbcUtils.executeUpdate(sql,id);
    }

    @Override
    public int update(Student student) {
        String sql = "UPDATE students SET stuname=?,age=?,gender=? where id=?";
        Object[] params = {student.getStuName(),student.getAge(),student.getGender(),student.getId()};
        return JdbcUtils.executeUpdate(sql,params);
    }

    @Override
    public List<Student> queryAll() {
        String sql = "SELECT id,stuname,age,gender from students";
        List<Map<String, Object>> list = JdbcUtils.executeQuery(sql);

        List<Student> collect = list.stream().map(bean -> { //将每一个map集合转换为Student对象
            int id = (int) bean.get("id"); //强制类型转换，将map的值Object类型转换为int类型
            String name = (String) bean.get("stuname");
            int age = (int) bean.get("age");
            String gender = (String) bean.get("gender");
            return new Student(id, name, age, gender);
        }).collect(Collectors.toList());//终止Stream API的操作，将Stream流中的每个Student类型转换为集合
        return collect;
    }

    @Override
    public Student findById(int id) {
        String sql = "SELECT id,stuname stuName,age,gender from students where id = ?";
        List<Student> students = JdbcUtils.selectSingleTab(Student.class, sql, id);
        return students.size() > 0 ? students.get(0) : null;
    }
}
