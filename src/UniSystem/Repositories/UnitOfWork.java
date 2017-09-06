package UniSystem.Repositories;

import UniSystem.Entities.Course;
import UniSystem.Entities.Faculty;
import UniSystem.Entities.Identificatable;
import UniSystem.Entities.Student;
import UniSystem.UniSystemContext;

import java.sql.SQLException;
import java.util.function.Supplier;

public class UnitOfWork implements IUnitOfWork {
    public Repository<Student> getStudentRepository() throws SQLException {
        return this.getRepository(Student::new, "students");
    }
    public Repository<Course> getCourseRepository() throws SQLException {
        return this.getRepository(Course::new, "courses");
    }
    public Repository<Faculty> getFacultyRepository() throws SQLException {
        return this.getRepository(Faculty::new, "faculties");
    }
    public <TEntity extends Identificatable> Repository<TEntity> getRepository(Supplier<? extends TEntity> entityConstructor, String entityPlural) throws SQLException {
        return new Repository<TEntity>(new UniSystemContext(), entityConstructor, entityPlural);
    }
}
