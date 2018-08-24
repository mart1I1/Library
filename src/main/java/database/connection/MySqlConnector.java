package database.connection;

import com.mysql.cj.jdbc.Driver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnector implements DBConnector {

    private static final Logger logger = LogManager.getLogger(MySqlConnector.class.getName());

    private final static String DBName = "com.mysql.cj.jdbc.Driver";

    public MySqlConnector() {
        registerDriver();
    }

    private void registerDriver() {
        try {
            DriverManager.registerDriver((Driver) Class.forName(DBName).getConstructor().newInstance());
        } catch (SQLException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException |
                ClassNotFoundException |
                NoSuchMethodException e) {
            logger.error("registerDriver: " + e.getClass());
        }
    }

    @Override
    public Connection getConnection() {
        try {
            String url = "jdbc:mysql://" +
                    "localhost:" +
                    "3306/" +
                    "Library?" +
                    "useUnicode=true&" +
                    "characterEncoding=utf-8&" +
                    "serverTimezone=UTC&" +
                    "user=mart1I1&" +
                    "password=qwerty";
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("getConnection: " + e.getClass());
        }
        return null;
    }
}
