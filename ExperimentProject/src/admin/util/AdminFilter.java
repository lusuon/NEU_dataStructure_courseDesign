package admin.util;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(
        filterName = "AdminFilter",
        urlPatterns = "/Goods.jsp"
)

public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request =  (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        Cookie[] Cookies = request.getCookies();
        Boolean logged = false;
        for(Cookie c : Cookies){
            if(c.getName().equals("admin")) logged = true;
        }
        if (request.getSession().getAttribute("id")!=null) logged = true;


        if(!logged){
            System.out.println("Detect non-login attempt.");
            out.print("<script language='javascript'>alert('Plaese log in');window.location.href='index.jsp';</script>");
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
