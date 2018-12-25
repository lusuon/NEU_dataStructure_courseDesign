package goods.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IResultSetHandler<T> {
    T handle(ResultSet rs) throws SQLException;
}
