package UniSystem.Controllers;

import UniSystem.Entities.Course;
import UniSystem.Repositories.UnitOfWork;
import UniSystem.Services.CrudService;

import java.sql.SQLException;
import java.util.function.Supplier;

public class CoursesController extends Controller<Course> {
    CoursesController() throws SQLException {
    }

    @Override
    protected void setupService() throws SQLException {
        this.service = new CrudService(new UnitOfWork().getCourseRepository());
    }

    @Override
    protected Supplier<? extends Course> setupEntityConstructor() {
        return Course::new;
    }
}
