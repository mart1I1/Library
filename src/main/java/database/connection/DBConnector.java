package database.connection;

import java.sql.Connection;

public interface DBConnector {
    Connection getConnection();
}
