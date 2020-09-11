package test3.dataSourceTest;

import java.util.List;
import java.util.Scanner;

/**
 * @author nick
 * @description
 * @date create in 2020/9/3
 */
public class Test {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        //testSave();

        //testDelete();
        //testUpdate();
        //testQueryAll();
        //testFindById();

        StudentInterface si = new StudentInterfaceImpl();
        boolean transaction = si.transaction();
        System.out.println(transaction);
    }

    /**
     * 在控制台接收用户输入并完成插入到数据库
     */
    private static void testSave(){
        StudentInterface si = new StudentInterfaceImpl();

        System.out.println("=========向系统插入学生信息=============");
        System.out.println("请输入学生姓名");
        String name = input.next();
        System.out.println("请输入年龄");
        int age = input.nextInt();
        System.out.println("请输入性别");
        String gender = input.next();

        Student student = new Student(999, name, age, gender);
        int save = si.save(student);
        System.out.println(save > 0 ? "插入成功": "插入失败");
    }

    private static void testDelete(){
        System.out.println("=========根据学号删除学生信息========");
        System.out.println("请输入学号");
        int id = input.nextInt();

        StudentInterface si = new StudentInterfaceImpl();
        int remove = si.remove(id);
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

        StudentInterface si = new StudentInterfaceImpl();
        int update = si.update(new Student(id, name, age, gender));
        System.out.println(update > 0 ?"修改成功 " :"修改失败");
    }

    private static void testQueryAll(){
        StudentInterface si = new StudentInterfaceImpl();
        List<Student> list = si.queryAll();
        list.stream().forEach(System.out::println);
    }

    private static void testFindById(){
        System.out.println("请输入ID");
        int id = input.nextInt();
        StudentInterface si = new StudentInterfaceImpl();
        Student student = si.findById(id);
        System.out.println(student);

    }
}
