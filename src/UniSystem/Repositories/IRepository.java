package UniSystem.Repositories;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<TEntity> {
    TEntity getById(int id) throws SQLException, IllegalAccessException;
    List<TEntity> getAll() throws SQLException, IllegalAccessException;
    void add(TEntity item) throws SQLException, IllegalAccessException ;
    void update(TEntity item) throws IllegalAccessException, SQLException;
    void delete(int id) throws SQLException;
    String getPluralFormOfEntity();
}
