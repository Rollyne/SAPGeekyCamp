package UniSystem.Services;

import UniSystem.Entities.Faculty;
import UniSystem.Entities.Student;
import UniSystem.Repositories.FacultiesRepository;
import UniSystem.Repositories.UnitOfWork;
import UniSystem.Services.Tools.ExecutionResult;
import UniSystem.Services.Tools.Messages;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Supplier;

public class FacultiesService extends CrudService<Faculty> {
    public FacultiesService(Supplier<? extends Faculty> entityConstructor, String entityPlural) throws SQLException {
        super(entityConstructor, entityPlural);
    }

    public ExecutionResult<List<Student>> getStudents(int facultyId){

        ExecutionResult<List<Student>> execution = new ExecutionResult<>();

        try{
            FacultiesRepository repo = new UnitOfWork().getFacultyRepository();
            repo.getStudents(facultyId);

            execution.succeeded = true;
            execution.result = repo.getStudents(facultyId);

        } catch (SQLException e) {
            execution.message = Messages.InternalServerError();
        }

        return execution;
    }
}
