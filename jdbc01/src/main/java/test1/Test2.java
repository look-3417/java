package test1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author nick
 * @description
 * @date create in 2020/9/3
 */
public class Test2 {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/hxzy_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    //注册驱动
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //与数据库建立连接
        try {
            //java.sql.Connection接口由JDBC提供，由于和数据库建立连接
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(connection != null ? "数据库连接成功":"数据库连接失败");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
