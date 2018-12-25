package goods.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import goods.util.JDBCUtil;

@MultipartConfig
@WebServlet(name = "uploadServlet",urlPatterns = "/upload.do")
public class uploadServlet extends HttpServlet {
    ArrayList<String> allow = new ArrayList<String>();
    private static final long serialVersionUID = 123L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        boolean allow_upload = (boolean)request.getAttribute("allow_upload");

        //确保事务：先更新数据库，后保存文件，保存文件失败就数据库事务回滚
        if(allow_upload) {
            PrintWriter out = response.getWriter();
            //获取连接池
            Connection conn = null;
            PreparedStatement ps =null;
            //获取上传文件的信息
            Part input = (Part)request.getAttribute("input");
            String ext_name = (String)request.getAttribute("ext_name");


            // 拼接文件名
            String name = request.getParameter("fileName") + "." + ext_name;

            try {
                //获取连接
                conn = JDBCUtil.getConn();
                //设置为手动提交事务
                conn.setAutoCommit(false);
                Statement stmt = conn.createStatement();
                //设置语句
                String sql = "UPDATE goods SET path = ? WHERE ID = ? ";
                ps = conn.prepareStatement(sql);
                ps.setString(1,"upload/"+name);
                ps.setString(2,request.getParameter("fileName"));
                System.out.println(ps.toString());

                //执行语句
                ps.executeUpdate();

                // 获取文件的存放目录，无则创建
                String realPath = request.getServletContext().getRealPath("./") + "upload";
                File file = new File(realPath);
                if (!file.exists()) {
                    file.mkdirs();
                }

                // 输出流写入存储
                InputStream inputStream = input.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(new File(file, name));
                int len = -1;
                byte[] bytes = new byte[1024];
                while ((len = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                }

                //文件存储完毕，提交事务
                conn.commit();

                // 关闭资源
                outputStream.close();
                inputStream.close();

               // 删除临时文件
                input.delete();
                out.print("<script language='javascript'>alert('Upload successfully.');window.location.href='Goods.jsp';</script>");
                } catch (Exception e) {
                    e.printStackTrace();
                    try{
                        conn.rollback();
                    }catch (SQLException e1){
                        e1.printStackTrace();
                    }

                } finally {
                    JDBCUtil.close(conn, ps, null);
                }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Receive the get request,dispatch to do post.");
        doPost(request,response);
    }
}
