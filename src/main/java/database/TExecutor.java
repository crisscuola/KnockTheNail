package database;



import java.sql.*;

public class TExecutor {
    public <T> T execQuery(Connection connection,
                           String query,
                           TResultHandler<T> handler)
            throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        T value = handler.handle(result);
        result.close();
        stmt.close();

        return value;
    }

    public void execUpdate(Connection connection, String update) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(update);
        statement.close();
    }

    public void execUpdate(Connection connection, String query, String[] parameters) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        int i = 0;
        for (String parameter : parameters) {
            i++;
            statement.setString(i, parameter);
        }
        statement.executeUpdate();
        statement.close();
    }

    public <T> T execQuery(Connection connection, String query, String[] parameters, TResultHandler<T> handler)
            throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        int i = 0;
        for (String parameter : parameters) {
            i++;
            statement.setString(i, parameter);
        }
        ResultSet result = statement.executeQuery();
        T value = handler.handle(result);
        result.close();
        statement.close();
        return value;
    }
}
