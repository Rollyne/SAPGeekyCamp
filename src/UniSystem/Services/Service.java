package UniSystem.Services;

import UniSystem.Entities.Identificatable;
import UniSystem.Repositories.IRepository;

import java.sql.SQLException;

public class Service<TEntity extends Identificatable> {
    protected IRepository<TEntity> repository;
    public Service(IRepository<TEntity> repository) throws SQLException {
        this.repository = repository;
    }
}
