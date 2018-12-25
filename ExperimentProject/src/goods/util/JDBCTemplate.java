package goods.util;

import java.sql.*;

public class JDBCTemplate {
    /**
     * DML
     * @param sql
     * @param params
     * @return
     */
    public static int update(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtil.getConn();
            ps = conn.prepareStatement(sql);
            System.out.println("sql:"+sql);
            // 设置占位参数
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i+1, params[i]);
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, ps, null);
        }
        return 0;
    }


    /**
     * DQL
     * @param sql
     * @param handler
     * @param params
     * @return
     */
    public static <T> T query(String sql, IResultSetHandler<T> handler, Object... params) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConn();
            ps = conn.prepareStatement(sql);
            // 设置占位参数
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i+1, params[i]);
            }
            rs = ps.executeQuery();
            return handler.handle(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, ps, null);
        }
        return null;
    }

}
