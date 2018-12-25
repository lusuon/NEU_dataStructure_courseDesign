package goods.DAO;

import goods.model.GoodsBean;

import java.util.List;

public interface IGoodsDAO {
    public void save(GoodsBean good);
    public void update(GoodsBean good);
    public void delete(String id);
    public GoodsBean getSimple(String id);
    public List<GoodsBean> list();
}
