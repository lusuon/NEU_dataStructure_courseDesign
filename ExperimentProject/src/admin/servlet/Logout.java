package admin.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "admin.servlet.Logout",urlPatterns = "/logout.do")
public class Logout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     *
     * 对是否勾选自动登录的两种情况设计了对应的处理模式
     * 有则对Cookie进行删除处理——setMaxAge（0），之后重定向
     * 否则直接销毁Session再重定向
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName());
            if (cookie.getName().equals("admin")) {
                System.out.println("cookie get");
                cookie.setMaxAge(0);
                response.addCookie(cookie);//教训，删除cookie必须加入response才有效
                response.sendRedirect("index.jsp");
                return;
            }
        }
        System.out.println("Not auto log in,redirect directly.");
        request.getSession().invalidate();
        response.sendRedirect("index.jsp");
    }
}