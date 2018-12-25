package goods.DAO.impl;

import goods.DAO.IGoodsDAO;
import goods.model.GoodsBean;
import goods.util.GoodResultSetHandler;
import goods.util.JDBCTemplate;

import java.util.Iterator;
import java.util.List;

public class GoodsDAOImpl implements IGoodsDAO {

    @Override
    public void save(GoodsBean good) {
        String sql = "INSERT INTO goods "
                +" VALUES (?,?,?,?,?,?,?,?);";
        Object[] params = {
                good.getId(),
                good.getName(),
                good.getFactory(),
                good.getCategory(),
                good.getType(),
                good.getOrigin(),
                good.getDescription(),
                good.getPath()};
        JDBCTemplate.update(sql, params);
    }

    @Override
    public void update(GoodsBean good) {
        String sql = "UPDATE goods SET "
                + "name = ?,"
                + "factory = ?,"
                + "category = ?,"
                + "type= ?, "
                + "origin= ?, "
                + "description= ? "
                + "WHERE id =?";
        Object[] params = {
                good.getName(),
                good.getFactory(), 
                good.getCategory(), 
                good.getType(),
                good.getOrigin(), 
                good.getDescription(),
                good.getId() };
        JDBCTemplate.update(sql, params);

    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM goods WHERE id = ?";
        JDBCTemplate.update(sql, id);
    }

    @Override
    public GoodsBean getSimple(String id) {
        String sql = "SELECT * FROM goods WHERE id = ?";
        List<GoodsBean> list = (List<GoodsBean>) JDBCTemplate.query(sql, new GoodResultSetHandler(),id);
        GoodsBean result=list.size() == 1 ? list.get(0) : null;
        if(result!=null){
            System.out.println("result:"+result.toString());
        }
        return result;
    }

    @Override
    public List<GoodsBean> list() {
        String sql = "SELECT * FROM goods";
        List<GoodsBean> list = (List<GoodsBean>) JDBCTemplate.query(sql, new GoodResultSetHandler());
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            GoodsBean good = (GoodsBean) iterator.next();
            System.out.println("good:" + good.toString());
        }
        return list;
    }
}
