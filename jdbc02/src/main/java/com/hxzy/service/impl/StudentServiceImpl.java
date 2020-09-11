package com.hxzy.service.impl;

import com.hxzy.bean.Student;
import com.hxzy.dao.StudentDao;
import com.hxzy.dao.impl.StudentDaoImpl;
import com.hxzy.service.StudentService;

import java.util.List;

/**
 * @author nick
 * @description
 * @date create in 2020/9/10
 */
public class StudentServiceImpl implements StudentService {

    //业务逻辑层中调用数据访问层的同名方法实现对数据库的访问
    private StudentDao dao = new StudentDaoImpl();

    @Override
    public int save(Student student) {
        return dao.save(student);
    }

    @Override
    public int remove(int id) {
        return dao.remove(id);
    }

    @Override
    public int update(Student student) {
        return dao.update(student);
    }

    @Override
    public List<Student> queryAll() {
        return dao.queryAll();
    }

    @Override
    public Student findById(int id) {
        return dao.findById(id);
    }
}
