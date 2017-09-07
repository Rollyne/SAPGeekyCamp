package UniSystem.Repositories;

import UniSystem.Entities.Course;
import UniSystem.IDbContext;
import UniSystem.UniSystemContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentsCoursesRepository {
    private IDbContext context;
    public StudentsCoursesRepository(IDbContext context){
        this.context = context;
    }

    public void enroll(int studentId, int courseId) throws SQLException {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO students_courses (studentId, courseId) VALUES (?, ?)");

        PreparedStatement cmd = context.getConnection().prepareStatement(sql.toString());
        cmd.setInt(1, studentId);
        cmd.setInt(2, courseId);
        cmd.execute();
    }

    public boolean isEnrolled(int studentId, int courseId) throws SQLException {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT COUNT(*) AS count FROM students_courses WHERE studentId = ? AND courseId = ?");
        PreparedStatement cmd = context.getConnection().prepareStatement(sql.toString());
        cmd.setInt(1, studentId);
        cmd.setInt(2, courseId);
        ResultSet rs = cmd.executeQuery();

        rs.next();
        return rs.getInt("count") > 0;
    }

    public List<Course> getStudentCourses(int studentId) throws SQLException {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT courses.id, courses.name, courses,credits FROM courses INNER JOIN students_courses ON students_courses.courseId = courses.id WHERE students_courses.studentId = ?");

        PreparedStatement cmd = context.getConnection().prepareStatement(sql.toString());
        cmd.setInt(1, studentId);
        ResultSet rs = cmd.executeQuery();

        List<Course> courses = new ArrayList<>();

        while(rs.next()){
            Course course = new Course();
            course.setId(rs.getInt("id"));
            course.setName(rs.getString("name"));
            course.setCredits(rs.getInt("credits"));

            courses.add(course);
        }

        return courses;
    }
}
