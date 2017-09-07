package UniSystem.Controllers;

import UniSystem.Entities.Course;
import UniSystem.Entities.Student;
import UniSystem.Repositories.UnitOfWork;
import UniSystem.Services.CrudService;
import UniSystem.Services.StudentsCoursesService;
import UniSystem.Services.Tools.ExecutionInfo;
import UniSystem.Services.Tools.ExecutionResult;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class StudentsController extends CrudController<Student> {
    StudentsController() throws SQLException {
    }

    @Override
    protected void setupService() throws SQLException {
        this.service = new CrudService(new UnitOfWork().getStudentRepository());
    }

    @Override
    protected Supplier<? extends Student> setupEntityConstructor() {
        return Student::new;
    }

    @Override
    public int details() throws IllegalAccessException, SQLException {
        int id = super.details();
        if(id < 0){
            return -1;
        }
        Scanner s = new Scanner(System.in);
        while(true){
            System.out.println("1. Enroll to course");
            System.out.println("2. List courses");
            System.out.println("0. Exit");

            int option = Integer.parseInt(s.nextLine());

            switch(option){
                case 1:
                    enroll(id);
                    break;
                case 2:
                    listCourses(id);
                    break;
                case 0:
                    return id;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }
    }

    private void enroll(int id) throws SQLException {
        System.out.println("Course id: ");
        Scanner s = new Scanner(System.in);
        int courseId = Integer.parseInt(s.nextLine());
        ExecutionInfo info = new StudentsCoursesService().enroll(id, courseId);
        System.out.println(info.message);
    }
    private void listCourses(int id) throws SQLException {
        ExecutionResult<List<Course>> execution = new StudentsCoursesService().getCourses(id);
        if(execution.result.size() <= 0){
            System.out.println("Nothing to show.");
            return;
        }

        for(Course course : execution.result){
            System.out.println("_______");
            System.out.printf("Id: %s\n", course.getId());
            System.out.printf("Name: %s\n", course.getName());
            System.out.printf("Credits: %s\n", course.getCredits());
        }
    }
}
