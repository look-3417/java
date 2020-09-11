package test1;

import test2.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nick
 * @description
 * @date create in 2020/9/3
 */
public class Test4 {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/hxzy_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    //第一步：注册驱动
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Student> students = queryAll();
        //students.stream().forEach(System.out::println);

        Student student = findById(7);
        System.out.println(student);
    }

    /**
     * 查询所有的数据
     */
    public static List<Student> queryAll(){
        try {
            //第二步：建立和数据库的连接
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            //第三步：预处理SQL语句
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,stuname,age,gender from students");

            //第四步：为SQL语句中的占位符绑定参数

            //第五步：执行SQL语句，得到查询结果
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Student> list = new ArrayList<>();
            //第六步：移动指针，依次取出结果集中的每行数据
            while (resultSet.next()) { //只要结果集中还有数据，指针就向前移动
                int id = resultSet.getInt(1); //获取当前指针下，sql查询语句中第一列的值
                //int id = resultSet.getInt("id");//根据列名来获取

                String name = resultSet.getString(2);
                //String name = resultSet.getString("stuname");

                int age = resultSet.getInt(3);

                String gender = resultSet.getString(4);

                //根据数据库查询的数据转换为一个java bean（java对象）
                Student student = new Student(id, name, age, gender);
                list.add(student);
            }

            //第七步：关闭连接，释放资源，关闭的顺序打开逆序
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据id查询
     * @param id
     */
    public static Student findById(int id) {
        try {
            //第二步：建立和数据库的连接
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            //第三步：预处理SQL语句
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,stuname,age,gender from students where id = ?");

            //第四步：为SQL语句中的占位符绑定参数
            preparedStatement.setInt(1,id);

            //第五步：执行SQL语句，得到查询结果
            ResultSet resultSet = preparedStatement.executeQuery();

            Student student = null;
            //第六步：移动指针，依次取出结果集中的每行数据
            if (resultSet.next()) { //只要结果集中还有数据，指针就向前移动
                int $id = resultSet.getInt(1); //获取当前指针下，sql查询语句中第一列的值
                //int id = resultSet.getInt("id");//根据列名来获取

                String name = resultSet.getString(2);
                //String name = resultSet.getString("stuname");

                int age = resultSet.getInt(3);

                String gender = resultSet.getString(4);

                //根据数据库查询的数据转换为一个java bean（java对象）
                student = new Student($id, name, age, gender);
            }

            //第七步：关闭连接，释放资源，关闭的顺序打开逆序
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
