package test3;

import java.sql.Connection;

/**
 * @author nick
 * @description
 * @date create in 2020/9/7
 */
public class TestMultConnection {
    public static void main(String[] args) {
        test2();
    }

    /**
     * 每次获取连接都从连接池中获取
     */
    private static void test1(){
        //测试在单线程环境中多次获取连接看是否为同一个Connection对象
        Connection conn1 = JdbcUtils.getConnection();
        Connection conn2 = JdbcUtils.getConnection();
        Connection conn3 = JdbcUtils.getConnection();
        Connection conn4 = JdbcUtils.getConnection();
        Connection conn5 = JdbcUtils.getConnection();
        System.out.println(conn1);
        System.out.println(conn2);
        System.out.println(conn3);
        System.out.println(conn4);
        System.out.println(conn5);
    }

    /**
     * 在同一个线程中多次获取Connection
     */
    private static void test2(){
        Connection conn1 = TransActionJdbcUtils.getInstance().getConnection();
        Connection conn2 = TransActionJdbcUtils.getInstance().getConnection();
        Connection conn3 = TransActionJdbcUtils.getInstance().getConnection();
        Connection conn4 = TransActionJdbcUtils.getInstance().getConnection();
        Connection conn5 = TransActionJdbcUtils.getInstance().getConnection();
        System.out.println(conn1);
        System.out.println(conn2);
        System.out.println(conn3);
        System.out.println(conn4);
        System.out.println(conn5);
    }
}
