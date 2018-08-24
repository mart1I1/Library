package database.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger(ConnectionPool.class.getName());

    private static ConnectionPool connectionPool = null;
    private static BlockingQueue<Connection> pool;

    private ConnectionPool(int poolSize) {
        DBConnector dbConnector = new MySqlConnector();
        pool = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++)
            pool.add(dbConnector.getConnection());

        logger.info("Connection pool created");
    }

    public static ConnectionPool getInstance() {
        if (connectionPool == null)
            connectionPool = new ConnectionPool(10);
        return connectionPool;
    }

    public Connection getConnection()
    {
        try {
            return pool.poll(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return null;
        }
    }

    public void releaseConnection(Connection connection) {
        pool.add(connection);
    }
}
