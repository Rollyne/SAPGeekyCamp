package UniSystem.Services;

import UniSystem.Entities.Identificatable;
import UniSystem.Repositories.IRepository;
import UniSystem.Repositories.Repository;
import UniSystem.Repositories.UnitOfWork;
import UniSystem.Services.Tools.ExecutionInfo;
import UniSystem.Services.Tools.ExecutionResult;
import UniSystem.Services.Tools.Messages;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;

public class CrudService<TEntity extends Identificatable> extends Service<TEntity> implements ICrudService<TEntity> {
    public CrudService(Supplier<? extends TEntity> entityConstructor, String entityPlural) throws SQLException {
        super(new UnitOfWork().getRepository(entityConstructor, entityPlural));
    }
    public CrudService(IRepository<TEntity> repository) throws SQLException {
        super(repository);
    }

    public String getPluralFormOfEntity(){
        return repository.getPluralFormOfEntity();
    }

    @Override
    public ExecutionInfo add(TEntity item){
        ExecutionInfo execution = new ExecutionInfo();
        try{
            repository.add(item);
            execution.message = Messages.SuccessfullyAdded();
            execution.succeeded = true;
        } catch (IllegalAccessException | SQLException e) {
            execution.message = Messages.InternalServerError();
        }

        return execution;
    }

    @Override
    public ExecutionResult<TEntity> getById(int id){
        ExecutionResult<TEntity> execution = new ExecutionResult<>();
        try{
            execution.result = repository.getById(id);
            execution.message = Messages.SuccessfullyFound();
            execution.succeeded = true;
        } catch (IllegalAccessException | SQLException e) {
            execution.message = Messages.InternalServerError();
        } catch(IndexOutOfBoundsException e){
            execution.message = Messages.NotFound();
        }

        return execution;
    }

    @Override
    public ExecutionResult<List<TEntity>> getAll(){
        ExecutionResult<List<TEntity>> execution = new ExecutionResult<>();
        try{
            execution.result = repository.getAll();
            execution.message = Messages.SuccessfullyFound();
            execution.succeeded = true;
        } catch (IllegalAccessException e) {
            execution.message = Messages.InternalServerError();
        } catch (SQLException e) {
            execution.message = Messages.NotFound();
        }

        return execution;
    }

    @Override
    public ExecutionInfo update(TEntity item){
        ExecutionInfo execution = new ExecutionInfo();
        try{
            repository.update(item);
            execution.message = Messages.SuccessfullyUpdated();
            execution.succeeded = true;
        } catch (IllegalAccessException | SQLException e) {
            execution.message = Messages.InternalServerError();
        }

        return execution;
    }

    @Override
    public ExecutionInfo delete(int id){
        ExecutionInfo execution = new ExecutionInfo();
        try{
            repository.delete(id);
            execution.message = Messages.SuccessfullyDeleted();
            execution.succeeded = true;
        } catch (SQLException e) {
            execution.message = Messages.InternalServerError();
        }

        return execution;
    }
}
