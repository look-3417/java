package test3;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author nick
 * @description
 * @date create in 2020/9/7
 */
public class TestDataSource {
    private static final String JDBC_PROPERTIES = "jdbc.properties";
    public static void main(String[] args) {
        //根据相对路径，将jdbc.properties转换为字节输入流
        InputStream resourceAsStream = TestDataSource.class.getClassLoader().getResourceAsStream(JDBC_PROPERTIES);
        //System.out.println(resourceAsStream != null);

        /*
        //将字节输入流转换为字符输入流，方便后续的读取
        InputStreamReader reader = new InputStreamReader(resourceAsStream);
        //通过字符缓存流读取字符输入流中的数据
        BufferedReader bufferedReader = new BufferedReader(reader);

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            bufferedReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        */

        try {
            //一个空的Properties集合
            Properties properties = new Properties();
            //为Properties集合加载配置信息
            properties.load(resourceAsStream);
            //System.out.println(properties);

            //建立数据库连接池
            DruidDataSource dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

            System.out.println(dataSource != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
