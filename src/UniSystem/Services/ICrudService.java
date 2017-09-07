package UniSystem.Services;

import UniSystem.Entities.Identificatable;
import UniSystem.Services.Tools.ExecutionInfo;
import UniSystem.Services.Tools.ExecutionResult;

import java.util.List;

public interface ICrudService<TEntity extends Identificatable> {
    ExecutionInfo add(TEntity item);

    ExecutionResult<TEntity> getById(int id);

    ExecutionResult<List<TEntity>> getAll();

    ExecutionInfo update(TEntity item);

    ExecutionInfo delete(int id);

    String getPluralFormOfEntity();
}
