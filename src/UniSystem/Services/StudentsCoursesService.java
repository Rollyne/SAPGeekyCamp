package UniSystem.Services;

import UniSystem.Entities.Course;
import UniSystem.Repositories.StudentsCoursesRepository;
import UniSystem.Repositories.UnitOfWork;
import UniSystem.Services.Tools.ExecutionInfo;
import UniSystem.Services.Tools.ExecutionResult;
import UniSystem.Services.Tools.Messages;

import java.sql.SQLException;
import java.util.List;

public class StudentsCoursesService {
    private StudentsCoursesRepository repository;
    public StudentsCoursesService() throws SQLException {
        this.repository = new UnitOfWork().getStudentsCoursesRepository();
    }

    public ExecutionInfo enroll(int studentId, int courseId) throws SQLException {
        ExecutionInfo info = new ExecutionInfo();

        try{
            if(this.repository.isEnrolled(studentId, courseId)) {
                info.message = Messages.AllreadyEnrolledOrNoCourse();
            }
            this.repository.enroll(studentId, courseId);
            info.message = Messages.SuccessfullyEnrolled();
            info.succeeded = true;
        } catch(SQLException e){
            info.message = Messages.InternalServerError();
        }

        return info;
    }

    public ExecutionResult<List<Course>> getCourses(int studentId) {
        ExecutionResult execution = new ExecutionResult();

        try{
            List<Course> items = this.repository.getStudentCourses(studentId);
            execution.result = items;
            execution.succeeded = true;
        } catch(SQLException e){
            execution.message = Messages.InternalServerError();
        }

        return execution;
    }
}
