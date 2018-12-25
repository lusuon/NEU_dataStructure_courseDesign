package goods.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {

    private static Properties properties = new Properties();
    static {
        // 在JdbcUtil的字节码被加载进JVM就执行，只是执行一次
        try {
            InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
            properties.load(inStream);
            Class.forName(properties.getProperty("driverClassName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取连接
     * @return
     */
    public static Connection getConn() {
        Connection conn = null;
        try {
            // 加载驱动
            conn = DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("userName"),
                    properties.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭资源
     *
     * @param conn
     * @param st
     * @param rs
     */
    public static void close(Connection conn, Statement st, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }
    }
}

