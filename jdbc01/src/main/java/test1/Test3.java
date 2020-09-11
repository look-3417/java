package test1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author nick
 * @description
 * @date create in 2020/9/3
 */
public class Test3 {
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
        //insert("吴焱燚",18,'男');
        delete(7);
    }

    private static void insert(String name,int age,char gender){
        try {
            //第二步：建立和数据库的连接
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            //第三步：得到预处理sql语句的对象
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students (stuname,age,gender) values (?,?,?)");

            //第四步：将sql语句中的每个参数进行绑定
            preparedStatement.setString(1,name); //将name参数和sql语句中第一个问号进行绑定
            preparedStatement.setInt(2,age); //将age参数和sql语句中第二个问号进行绑定
            preparedStatement.setString(3,Character.toString(gender)); //将gender参数和sql语句中第三个问号进行绑定

            //第五步：执行sql语句，并返回结果
            int row = preparedStatement.executeUpdate();//执行DML操作或者DDL操作，返回数据库受影响的行数

            if (row > 0) {
                System.out.println("插入成功");
            } else {
                System.out.println("插入失败");
            }

            //第六步：关闭连接，释放资源
            //关闭顺序为开启的逆序
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void delete(int id) {
        try {
            //第二步：建立和数据库的连接
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            //第三步：得到预处理sql语句的对象
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM students where id = ?");

            //第四步：将sql语句中的每个参数进行绑定
            preparedStatement.setInt(1,id);

            //第五步：执行sql语句，并返回结果
            int row = preparedStatement.executeUpdate();//执行DML操作或者DDL操作，返回数据库受影响的行数

            if (row > 0) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }

            //第六步：关闭连接，释放资源
            //关闭顺序为开启的逆序
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
