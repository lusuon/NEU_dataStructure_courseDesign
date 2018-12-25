package goods.util;

import goods.model.GoodsBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodResultSetHandler implements IResultSetHandler {
    @Override
    public List<GoodsBean> handle(ResultSet rs) throws SQLException {
        List<GoodsBean> list=new ArrayList<GoodsBean>();
        while(rs.next()){
            GoodsBean pro = new GoodsBean();
            pro.setId(rs.getString("id"));
            pro.setName(rs.getString("name"));
            pro.setFactory(rs.getString("factory"));
            pro.setCategory(rs.getString("category"));
            pro.setType(rs.getString("type"));
            pro.setOrigin(rs.getString("origin"));
            pro.setDescription(rs.getString("description"));
            pro.setPath(rs.getString("path"));
            list.add(pro);
        }
        return list;
    }
}
