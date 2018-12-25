package goods.util;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

@WebFilter(filterName = "PicFilter",urlPatterns = "/upload.do")
public class PicFilter implements Filter {
    ArrayList<String> allow = new ArrayList<String>();

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //初始化上传白名单
        String[] exts = {"jpg","png","bmp","jpeg"};
        Collections.addAll(allow, exts);

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        //过滤开始
        boolean allow_upload = false;
        System.out.println("Filtering");
        for(Part part:request.getParts()){
            if (part.getName().equals("file")){
                //获取拓展名
                String ext_name = part.getContentType().split("/")[1];
                if(part.getSize()==0) {
                    //过滤空文件
                    out.print("<script language='javascript'>alert('Nothing uploaded.');window.location.href='Goods.jsp';</script>");
                }else if(!allow.contains(ext_name)){
                    //过滤不在白名单内的文件
                    System.out.println("Not pic");
                    out.print("<script language='javascript'>alert('Only jpg/bmp/png are allowed.');window.location.href='Goods.jsp';</script>");
                }else{
                    //通过过滤，准备传到upload.do的参数
                    allow_upload = true;

                    request.setAttribute("allow_upload",allow_upload);
                    request.setAttribute("input",part);
                    request.setAttribute("ext_name",ext_name);
                }
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
