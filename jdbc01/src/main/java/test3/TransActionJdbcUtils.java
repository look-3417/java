package test3;

import org.apache.commons.beanutils.BeanUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nick
 * @description
 * @date create in 2020/9/7
 */
public class TransActionJdbcUtils {
    private static TransActionJdbcUtils ourInstance = new TransActionJdbcUtils();

    public static TransActionJdbcUtils getInstance() {
        return ourInstance;
    }

    private TransActionJdbcUtils() {
    }

    //当前线程在得到connection以后缓存到本地线程变量
    private ThreadLocal<Connection> tl = new ThreadLocal<>();

    //得到数据库连接池的对象
    private DataSource dataSource = DataSourceUtil.getDataSource();

    //默认当前的连接不支持事务
    private boolean isTransactionConnection = false;

    /**
     * 建立数据库连接
     * @return
     */
    public Connection getConnection(){
        try {
            //获取连接前，首先从ThreadLocal中看是否存在
            Connection connection = tl.get();
            if (connection != null) {
                return connection;
            }

            //如果tl中没有connection，说明当前线程是第一次获取连接，从数据库连接池中获取一个连接
            connection = dataSource.getConnection();
            //将取出的连接保存到tl中
            tl.set(connection);

            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 开启事务的方法
     */
    public void beginTransaction() throws SQLException {
        Connection connection = this.getConnection();
        //关闭事务自动提交
        connection.setAutoCommit(false);
        this.isTransactionConnection = true;
    }

    /**
     * 关闭自动提交
     */
    public void endTransaction() throws SQLException {
        Connection connection = this.getConnection();
        connection.setAutoCommit(true);
        //关闭了事务
        this.isTransactionConnection = false;
        this.getConnection().close();
    }

    /**
     * 提交事务
     * @throws SQLException
     */
    public void commit() throws SQLException {
        Connection connection = this.getConnection();
        connection.commit();
    }

    /**
     * 回滚事务
     * @throws SQLException
     */
    public void rollback() throws SQLException {
        Connection connection = this.getConnection();
        connection.rollback();
    }

    /**
     * 关闭连接释放资源
     * 关闭的顺序为打开的逆序
     * @param connection 数据库连接对象
     * @param pstmt 预处理sql语句的对象
     * @param rs 执行sql语句后的结果集对象
     */
    public void closeAll(Connection connection, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                //如果开启了事务，则不关闭connection
                if (!isTransactionConnection){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通用的DML操作的方法
     * @param sql 执行增、删、改的sql语句
     * @param params sql语句中的参数列表,可变长度的参数列表，当做数组来使用
     * @return 数据库受影响的行数
     */
    public int executeUpdate(String sql,Object...params){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //建立和数据库的连接
            connection = getConnection();
            //预处理sql语句
            preparedStatement = connection.prepareStatement(sql);
            //为sql语句的参数绑定值
            initParams(preparedStatement, params);

            //执行sql语句得到结果
            int executeUpdate = preparedStatement.executeUpdate();
            //返回结果
            return executeUpdate;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //最后，不管程序是否出现异常，都必须关闭连接释放资源
            closeAll(connection,preparedStatement,null);
        }
        return -1;
    }

    public List<Map<String, Object>> executeQuery(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            initParams(pstmt, params);

            rs = pstmt.executeQuery();

            /*
             * 获取sql语句的元信息
             * 元信息包括：查了几个列，每个列叫什么
             * 将每个列的数据取出，组建为一个Map集合，键为列名，值为取出来的数据
             * */

            //获取SQL语句的元信息
            ResultSetMetaData metaData = rs.getMetaData();
            //循环查询列的个数次
            int columnCount = metaData.getColumnCount();

            //将所有的数据添加到List集合之内
            List<Map<String, Object>> list = new ArrayList<>();
            while (rs.next()) { //移动指针得到当前行的数据
                //将每行数据封装到一个map集合中
                Map<String, Object> bean = new HashMap<>();

                //经过一轮for循环后，为map集合赋值
                for (int i = 1; i <= columnCount ; i++) { //查了几个列，循环几次，每次循环依次取出每个列的列名和值
                    //获取列名
                    String columnLabel = metaData.getColumnLabel(i); //每次循环得到id,stuname,age,gender
                    //获取这个列对应的值
                    //Object value = rs.getObject(i);  //根据列号获取这一行的i对应的列的值
                    Object value = rs.getObject(columnLabel); //根据列名获取这一行的columnLabel列对应的值
                    //System.out.println("查询的列：《" + columnLabel + "》，这个列的值：《" + value +  "》");
                    bean.put(columnLabel, value);
                }
                //System.out.println("\n---------------------------");

                //将这一行数据添加到List集合
                list.add(bean);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pstmt,rs);
        }
        return null; //说明出现异常
    }

    /**
     * 查询单表的通用方法，本通用方法通过反射得到一个空的对象，并通过反射
     * 获取这个Class类中的属性，并通过Commons-bean-utils的第三发依赖为每个对象的属性赋值
     * 注意：Class对应的类必须提供无参构造方法，否则无法实例化空的对象
     *         sql语句查询的列的别名必须和T代表的类的属性名完全相同
     * @param claz 转换的类的类型，Class类型对象
     * @param sql
     * @param params
     * @param <T> 是一个泛型
     * @return
     */
    public <T> List<T> selectSingleTab(Class<T> claz, String sql, Object... params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            initParams(pstmt, params);

            rs = pstmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            List<T> list = new ArrayList<>();
            while (rs.next()) {
                //通过反射，调用Class对应类的无参构造方法
                T t = claz.newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    //得到列名，一定要注意和类的属性名相同，如果遇到表中的列名和类中的
                    //属性名不同时（区分大小写），则需要指定sql语句的列的别名
                    String columnLabel = metaData.getColumnLabel(i);

                    Object value = rs.getObject(i);

                    //通过第三方依赖，为对象t中的columnLabel属性赋值为value
                    BeanUtils.setProperty(t,columnLabel,value);
                }
                list.add(t);
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn,pstmt,rs);
        }
        return null; //说明出现异常
    }

    /**
     * 通用的，为增、删、该、查的PreparedStatement对应的sql语句参数绑定值
     * @param pstmt
     * @param params
     * @throws SQLException
     */
    private void initParams(PreparedStatement pstmt, Object[] params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
        }
    }
}
