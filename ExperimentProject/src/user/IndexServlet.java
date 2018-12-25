package user;

import goods.util.JDBCUtil;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet(
        urlPatterns={"/customer.do"}
)
public class IndexServlet extends javax.servlet.http.HttpServlet {
    /**
     *
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws IOException
     *
     * 由于强制要求使用一个Servlet实现增删改查四功能，因此使用Switch-Case语句，接收传入的模式参数，判断行为。其中，模式选择参数通过隐藏域传入。
     * 使用HashMap存储传入参数，以实现在部分情况下，过滤会修改数据库的对应字段为空的空值提交。
     * 使用StringBuilder构造SQL语句，
     * 使用了简单js语句以提示用户异常输入。
     *
     */

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out=response.getWriter();
        Connection conn = null;
        HashMap<String,String> paprameters = new HashMap<String,String>();
        Statement stmt = null;
        String sql; // sql语句
        ResultSet rs = null;
        paprameters.put("id",request.getParameter("id")) ;
        paprameters.put("name",request.getParameter("name")) ;
        paprameters.put("gender", request.getParameter("gender"));
        paprameters.put("job",request.getParameter("job")) ;
        paprameters.put("education",request.getParameter("education")) ;
        paprameters.put("home",request.getParameter("home")) ;
        //获取模式
        String mode = request.getParameter("mode");

        try {
            conn = JDBCUtil.getConn();
            stmt = conn.createStatement();
            StringBuilder sbsql = new StringBuilder();
            System.out.println("receiving mode");
            switch(mode) {
                case "add":
                    System.out.println("Adding");
                    if(request.getParameter("id").equals("")) out.print("<script language='javascript'>alert('ID missed');window.location.href='CustomerAdd.jsp';</script>");
                    rs = stmt.executeQuery("SELECT * FROM customer_info WHERE id =\""+request.getParameter("id")+"\"");
                    System.out.println("SELECT * FROM customer_info WHERE id ="+request.getParameter("id"));
                    if(rs.next()){
                        out.print("<script language='javascript'>alert('ID duplicate');window.location.href='CustomerAdd.jsp';</script>");
                    }else{
                        sbsql.append("INSERT INTO customer_info ").append("VALUES ('").append(paprameters.get("id")+"','").append(paprameters.get("name")+"','").append(paprameters.get("gender")+"','").append(paprameters.get("job")+"','").append(paprameters.get("education")+"','").append(paprameters.get("home")).append("')");
                        System.out.println(sbsql.toString());
                        stmt.executeUpdate(sbsql.toString());
                        System.out.println("Add success.");
                        request.getRequestDispatcher("CustomerList.jsp").forward(request,response);
                    }
                    break;
                case "modify":
                    sbsql.append("UPDATE customer_info SET ");
                    if(paprameters != null  && paprameters.get("id") != null ) {
                        for (Map.Entry<String, String> entry : paprameters.entrySet()) {
                            if(entry.getValue() != null || !entry.getValue().equals(""))
                                sbsql.append(entry.getKey()+"='"+entry.getValue()+"',");
                        }
                        sbsql.deleteCharAt(sbsql.length()-1);//移除sql语句最末的逗号
                        sbsql.append(" WHERE id = '").append(paprameters.get("id")+"'");
                        System.out.println(sbsql.toString());
                        stmt.executeUpdate(sbsql.toString());
                        System.out.println("Modify success.");
                        request.getRequestDispatcher("CustomerList.jsp").forward(request,response);
                    }else{
                        request.getRequestDispatcher("CustomerAdd.jsp").forward(request,response);
                    }
                    break;
                case "delete":
                    System.out.println("delete start");
                    sql = "DELETE FROM customer_info WHERE id='" + paprameters.get("id")+"'";

                    System.out.println("sql initialized");
                    stmt.executeUpdate(sql);
                    System.out.println("delete complete");
                    request.getRequestDispatcher("CustomerList.jsp").forward(request,response);
                    break;
                case "search" :
                    sbsql.append("SELECT * FROM customer_info WHERE ");
                    boolean searchDB = false;
                    if(!request.getParameter("id").equals("")){
                        sbsql.append("id='"+request.getParameter("id")+"'");
                        searchDB = true;
                        System.out.println("detect id input");
                    }else if(!request.getParameter("name").equals("")) {
                        searchDB = true;
                        sbsql.append("name LIKE '%" + request.getParameter("name")+"%'");
                        System.out.println("detect name input");
                    }else{
                        sbsql = new StringBuilder();
                        sbsql.append("SELECT * FROM customer_info");
                    }
                    System.out.println(sbsql.toString());
                    rs =stmt.executeQuery(sbsql.toString());
                    request.setAttribute("result",rs);
                    request.setAttribute("searchDB",searchDB);
                    request.getRequestDispatcher("CustomerList.jsp").forward(request,response);
                    break;
                    default: System.out.println("Do nothing.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) JDBCUtil.close(conn,stmt,rs);
        }


    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("received get request");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    }
}
