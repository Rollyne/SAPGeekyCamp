package UniSystem.Controllers;

import java.sql.SQLException;
import java.util.Scanner;

public class RootController {
    public static void run() throws SQLException, IllegalAccessException {
        System.out.println("--_- UniSystem -_--");
        Scanner s = new Scanner(System.in);
        while(true){
            System.out.println("Main Menu");
            System.out.println("1. Students Manager");
            System.out.println("2. Courses Manager");
            System.out.println("3. Faculties Manager");
            System.out.println("0. Exit");
            int input = s.nextInt();

            switch(input) {
                case 1:
                    CrudController studentsCont = new StudentsController();
                    studentsCont.run();
                    break;
                case 2:
                    CrudController coursesCont = new CoursesController();
                    coursesCont.run();
                    break;
                case 3:
                    CrudController facultiesCont = new FacultiesController();
                    facultiesCont.run();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid command!");
                    break;
            }
        }


    }
}
