package test3.dataSourceTest;

import test3.JdbcUtils;
import test3.TransActionJdbcUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author nick
 * @description
 * @date create in 2020/9/3
 */
public class StudentInterfaceImpl implements StudentInterface {
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

    @Override
    public boolean transaction() {
        //1.向students表中插入数据
        //2.修改students表中的数据
        //3.删除student表中的数据
        //4.提交或回滚
        String insert = "INSERT INTO students (stuname,age,gender) values (?,?,?)";
        String update = "UPDATE students SET stuname=?,age=?,gender=? where id=?";
        String remove = "DELETE FROM students where id = ?";

        //开启事务
        TransActionJdbcUtils util = TransActionJdbcUtils.getInstance();
        try {
            util.beginTransaction();

            //执行插入
            util.executeUpdate(insert, "农夫山泉", 33, "男");
            System.out.println("=========执行插入操作成功===========");
            util.executeUpdate(update,"李世民", 133, "男",5);
            System.out.println("=========执行修改操作成功===========");
            util.executeUpdate(remove,4);
            System.out.println("=========执行删除操作成功===========");


            //上面的代码没有出现异常，必定说明都执行成功,提交事务
            util.commit();
            //util.rollback();
            System.out.println("提交事务");

            util.endTransaction();
            return true;
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("出现异常：" + e.getMessage());
            //回滚事务
            try {
                util.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }
}
