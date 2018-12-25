package goods.servlet;

import goods.DAO.IGoodsDAO;
import goods.DAO.impl.GoodsDAOImpl;
import goods.model.GoodsBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GoodServlet",urlPatterns = "/goods.do")
public class GoodServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    IGoodsDAO goodsDAO;

    @Override
    public void init() throws ServletException {
        goodsDAO = new GoodsDAOImpl();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String cmd = request.getParameter("cmd");
        if ("delete".equals(cmd)) {
            this.delete(request, response);
        } else if ("edit".equals(cmd)) {
            this.edit(request, response);
        } else if ("save".equals(cmd)) {
            this.addOrUpdate(request, response);
        } else {
            this.list(request, response);
        }
    }



    /**
     * 获取全部
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<GoodsBean> goods = goodsDAO.list();
        // 控制页面跳转
        request.setAttribute("goods", goods);
        request.getRequestDispatcher("Goods.jsp").forward(request, response);
    }



    /**
     * 增加或者修改
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addOrUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.接收参数
        GoodsBean good = null;
        try {
            good = requset2Obj(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("添加失败");
            response.sendRedirect("Goods.jsp");
            return;
        }
        if(request.getParameter("add")!=null){
            System.out.println("adding");
            goodsDAO.save(good);
        }else{
            System.out.println(request.getParameter("add"));
            System.out.println("updating");
            goodsDAO.update(good);
        }
        response.sendRedirect("Goods.jsp");
    }



    /**
     * 编辑
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void edit(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        String id = request.getParameter("id");
        if(id!=null){
            try {
                GoodsBean good =goodsDAO.getSimple(id);
                //控制页面跳转
                request.setAttribute("good", good);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        request.getRequestDispatcher("Goods.jsp").forward(request, response);
    }



    /**
     * 删除
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void delete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        String id = request.getParameter("id");
        System.out.println(id);
        goodsDAO.delete(id);
        response.sendRedirect("Goods.jsp");
    }



    protected void query(){

    }


    private GoodsBean requset2Obj(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String name = request.getParameter("name");
        String factory = request.getParameter("factory");
        String category = request.getParameter("category");
        String type = request.getParameter("type");
        String origin = request.getParameter("origin");
        String description = request.getParameter("description");
        String path = request.getParameter("path");
        String id = request.getParameter("id");

        GoodsBean good = new GoodsBean();
        good.setName(name);
        good.setFactory(factory);
        good.setCategory(category);
        good.setType(type);
        good.setOrigin(origin);
        good.setDescription(description);
        good.setPath(path);
        if(hasLength(id)){
            good.setId(id);
        }
        System.out.println("增加："+good.toString());
        return good;
    }


    private boolean hasLength(String str){
        return str!=null&&!"".equals(str.trim());
    }
}
