package UniSystem.Repositories;

import UniSystem.Entities.Course;
import UniSystem.Entities.Faculty;
import UniSystem.Entities.Student;
import UniSystem.IDbContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FacultyRepository extends Repository<Faculty> {
    public FacultyRepository(IDbContext context, Supplier<? extends Faculty> entityConstructor, String pluralFormOfEntity) {
        super(context, entityConstructor, pluralFormOfEntity);
    }

    public List<Student> getStudents(int facultyId) throws SQLException {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT * FROM students WHERE facultyid = ?");

        PreparedStatement cmd = context.getConnection().prepareStatement(sql.toString());
        cmd.setInt(1, facultyId);
        ResultSet rs = cmd.executeQuery();

        List<Student> students = new ArrayList<>();

        while(rs.next()){
            Student student = new Student();
            student.setId(rs.getInt("id"));
            student.setFirstName(rs.getString("firstname"));
            student.setLastName(rs.getString("lastname"));

            students.add(student);
        }

        return students;
    }
}
