package com.hxzy.ui;

import com.hxzy.bean.Student;
import com.hxzy.service.StudentService;
import com.hxzy.service.impl.StudentServiceImpl;

import java.util.List;
import java.util.Scanner;

/**
 * @author nick
 * @description
 * @date create in 2020/9/3
 */
public class Test {

    private static Scanner input = new Scanner(System.in);
    //在UI层中调用service层来操作数据
    private static StudentService service = new StudentServiceImpl();

    public static void main(String[] args) {

        //testSave();

        //testDelete();
        //testUpdate();
        testQueryAll();
        //testFindById();
    }

    /**
     * 在控制台接收用户输入并完成插入到数据库
     */
    private static void testSave(){

        System.out.println("=========向系统插入学生信息=============");
        System.out.println("请输入学生姓名");
        String name = input.next();
        System.out.println("请输入年龄");
        int age = input.nextInt();
        System.out.println("请输入性别");
        String gender = input.next();

        Student student = new Student(999, name, age, gender);
        int save = service.save(student);
        System.out.println(save > 0 ? "插入成功": "插入失败");
    }

    private static void testDelete(){
        System.out.println("=========根据学号删除学生信息========");
        System.out.println("请输入学号");
        int id = input.nextInt();

        int remove = service.remove(id);
        System.out.println(remove > 0 ? "删除成功": "删除失败");
    }

    private static void testUpdate(){
        System.out.println("=========根据学号修改学生信息==========");
        System.out.println("请输入学号");
        int id = input.nextInt();
        System.out.println("请输入学生姓名");
        String name = input.next();
        System.out.println("请输入年龄");
        int age = input.nextInt();
        System.out.println("请输入性别");
        String gender = input.next();

        int update = service.update(new Student(id, name, age, gender));
        System.out.println(update > 0 ?"修改成功 " :"修改失败");
    }

    private static void testQueryAll(){
        List<Student> list = service.queryAll();
        list.stream().forEach(System.out::println);
    }

    private static void testFindById(){
        System.out.println("请输入ID");
        int id = input.nextInt();
        Student student = service.findById(id);
        System.out.println(student);

    }
}
