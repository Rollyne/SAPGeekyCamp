package UniSystem.Controllers;

import UniSystem.Entities.Faculty;
import UniSystem.Entities.Student;
import UniSystem.Repositories.UnitOfWork;
import UniSystem.Services.CrudService;
import UniSystem.Services.FacultiesService;
import UniSystem.Services.Tools.ExecutionResult;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class FacultiesController extends CrudController<Faculty> {
    FacultiesController() throws SQLException {
    }

    @Override
    protected void setupService() throws SQLException {
        this.service = new CrudService(new UnitOfWork().getFacultyRepository());
    }

    @Override
    protected Supplier<? extends Faculty> setupEntityConstructor() {
        return Faculty::new;
    }

    @Override
    public int details() throws SQLException, IllegalAccessException {
        int id = super.details();
        Scanner s = new Scanner(System.in);
        while(true) {
            System.out.println("1. List students");
            System.out.println("0. Exit");

            int option = Integer.parseInt(s.nextLine());

            switch (option) {
                case 1:
                    listStudents(id);
                    break;
                case 0:
                    return id;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }
    }

    private void listStudents(int id) throws SQLException {
        FacultiesService specService = new FacultiesService(Faculty::new, "faculties");
        ExecutionResult<List<Student>> execution = specService.getStudents(id);
        if(execution.result.size() <= 0){
            System.out.println("Nothing to show.");
            return;
        }

        for(Student student : execution.result){
            System.out.println("_______");
            System.out.printf("Id: %s\n", student.getId());
            System.out.printf("First name: %s\n", student.getFirstName());
            System.out.printf("Last name: %s\n", student.getLastName());
        }
    }
}
