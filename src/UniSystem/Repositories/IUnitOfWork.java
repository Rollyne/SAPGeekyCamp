package UniSystem.Repositories;

import UniSystem.Entities.Course;
import UniSystem.Entities.Faculty;
import UniSystem.Entities.Identificatable;
import UniSystem.Entities.Student;

import java.sql.SQLException;
import java.util.function.Supplier;

public interface IUnitOfWork {
    Repository<Student> getStudentRepository() throws SQLException;

    Repository<Course> getCourseRepository() throws SQLException;

    Repository<Faculty> getFacultyRepository() throws SQLException;

    <TEntity extends Identificatable> Repository<TEntity> getRepository(Supplier<? extends TEntity> entityConstructor, String entityPlural) throws SQLException;
}