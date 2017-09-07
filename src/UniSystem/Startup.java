package UniSystem;
import UniSystem.Controllers.RootController;

import java.sql.SQLException;

public class Startup {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        RootController.run();
    }
}
