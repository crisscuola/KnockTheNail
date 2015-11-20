package dao;

import dataSets.UsersDataSet;
import executor.TExecutor;
import handlers.TResultHandler;

import java.sql.*;


public class UsersDAO {

    private Connection connection;

    public UsersDAO(Connection connection) {
        this.connection = connection;
    }

    public UsersDataSet get(long id) throws SQLException {
        TExecutor exec = new TExecutor();
        return exec.execQuery(connection, "select * from users where id=" + id, result -> {
            result.next();
            return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3));
        });
    }
    

}
