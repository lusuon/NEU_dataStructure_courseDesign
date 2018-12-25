package admin.servlet;

import goods.util.JDBCUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "admin.servlet.Login",
        urlPatterns = "/login.do")

public class Login extends HttpServlet {
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     *
     * 本servlet用于处理登录请求，除此之外通过Cookie实现自动登录、记录上次登录时间与次数。
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String pw = request.getParameter("password");
        String login_auto = request.getParameter("login");
        String sql = "";
        PrintWriter out =response.getWriter();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConn();
            stmt = conn.prepareStatement("SELECT * FROM admins WHERE id= ?");
            stmt.setString(1,id);
            System.out.println("logging sql: "+sql);
            rs = stmt.executeQuery(sql);
            //rs.next()判断执行的搜索用户语句是否得到结果，有则说明ID存在
            if(rs.next()){
                String pw_check = rs.getString("password");
                if(pw_check.equals(pw)){
                    System.out.println("pw correct");
                    request.getSession().setAttribute("id",request.getParameter("id"));

                    //如果用户勾选了自动登录，向响应加入Cookie，以便其下次登录。
                    if(login_auto!= null && login_auto.equals("auto")){
                        Cookie cookie_login = new Cookie("admin",id);
                        cookie_login.setMaxAge(7*24*60*60);
                        response.addCookie(cookie_login);
                        request.getSession().setAttribute("auto",true);
                    }
                    //使用Cookie 更新用户上次登录时间、登录次数
                    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd/hh:mm:ss");
                    String curTime=format.format(new Date());
                    Cookie last_login_time = new Cookie("last_login_time", curTime);
                    response.addCookie(last_login_time);
                    Boolean first_login = true;
                    for (Cookie cookie:request.getCookies()) {
                        if(cookie.getName().equals("login_times")){
                            first_login = false;
                            int times = Integer.parseInt(cookie.getValue())+1;
                            Cookie login_times = new Cookie(cookie.getName(),Integer.toString(times));
                            login_times.setMaxAge(7*24*60*60);
                            response.addCookie(login_times);
                        }
                    }
                    if(first_login) {
                        Cookie login_times = new Cookie("login_times","1");
                        login_times.setMaxAge(7*24*60*60);
                        response.addCookie(login_times);
                    }
                    last_login_time.setMaxAge(7*24*60*60);
                    //request.getRequestDispatcher("CustomerList.jsp").forward(request,response);
                    response.sendRedirect("Goods.jsp");
                }else{
                    //弹出提示，使用JS重定向
                    out.print("<script language='javascript'>alert('Wrong password.');window.location.href='index.jsp';</script>");
                }
            }else{
                out.print("<script language='javascript'>alert('ID not exist.');window.location.href='index.jsp';</script>");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //善后，关闭连接
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2){}
            if (conn != null) JDBCUtil.close(conn,stmt,rs);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
    }
}
