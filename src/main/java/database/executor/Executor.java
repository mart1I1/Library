package database.executor;

import database.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Executor {

    public int execUpdate(String update, List<?> params) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(update);
        prepare(preparedStatement, params);
        int resultCount = preparedStatement.executeUpdate();
        preparedStatement.close();
        ConnectionPool.getInstance().releaseConnection(connection);
        return resultCount;
    }

    public <T> T execQuery(String query, List<?> params, ResultHandler<T> handler) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        prepare(preparedStatement, params);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        T value = handler.handle(resultSet);
        resultSet.close();
        preparedStatement.close();
        ConnectionPool.getInstance().releaseConnection(connection);
        return value;
    }

    private void prepare(PreparedStatement preparedStatement, List<?> params) throws SQLException {
        for (int i = 0; i < params.size(); i++)
            preparedStatement.setObject(i + 1, params.get(i));
    }

}
