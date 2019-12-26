package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import java.util.Properties;
import javax.sql.DataSource;
import java.sql.Connection;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * @author wen
 * @date 2019/12/23 0023-16:40
 */
public class DruidConnection {
    private static Properties properties = null;
    private static DataSource dataSource = null;
    private volatile static DruidConnection instatce = null;
    private Connection connection = null;

    //私有构造函数,防止实例化对象
    public DruidConnection() {

    }

    static {
        try {
            properties = new Properties();
            // 1.加载properties文件
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("druid.properties");

            // 2.加载输入流
            properties.load(is);

            // 3.获取数据源
            dataSource = getDatasource();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //用简单单例模式确保只返回一个链接对象
    public static  DruidConnection getInstace() {
        if(instatce == null) {
            synchronized (DruidConnection.class) {
                if(instatce == null) {
                    instatce = new DruidConnection();
                }
            }
        }
        return instatce;
    }

    // 返回一个数据源
    public DataSource getDataSource() {
        return dataSource;
    }

    // 返回一个链接
    public Connection getConnection() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // 加载数据源
    private static DataSource getDatasource() {
        DataSource source = null;
        try {
            source = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source;
    }
}
