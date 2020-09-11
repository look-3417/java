package test3;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author nick
 * @description 得到数据库连接池的工具类
 * @date create in 2020/9/7
 */
public class DataSourceUtil {

    private static final String JDBC_PROPERTIES = "jdbc.properties";

    public static DataSource getDataSource(){
        try {
            //根据相对路径，将jdbc.properties转换为字节输入流
            InputStream resourceAsStream = DataSourceUtil.class.getClassLoader().getResourceAsStream(JDBC_PROPERTIES);
            //一个空的Properties集合
            Properties properties = new Properties();
            //为Properties集合加载配置信息
            properties.load(resourceAsStream);

            //建立数据库连接池
            return DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
