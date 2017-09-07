package UniSystem;

import java.sql.Connection;

public interface IDbContext {
    Connection getConnection();
}
