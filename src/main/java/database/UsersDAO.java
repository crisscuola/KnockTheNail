package database;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.*;


public class UsersDAO {

    private Connection connection;

    public UsersDAO(Connection connection) {
        this.connection = connection;
    }

    public UsersDataSet get(long id) {
        TExecutor exec = new TExecutor();
        try {
            return exec.execQuery(connection, "select * from users where id=" + id, result -> {
                result.next();
                return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3), result.getInt(4), result.getInt(5));
            });
        } catch (SQLException e){
            e.printStackTrace();
        }
        return  null;
    }

    public UsersDataSet get(String name) {
        TExecutor exec = new TExecutor();
        try {
            return exec.execQuery(connection, "select * from users where name=" + name, result -> {
                result.next();
                return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3), result.getInt(4), result.getInt(5));
            });
        } catch (SQLException e){
            e.printStackTrace();
        }
        return  null;
    }

    public void createUser(String name, String password) {
        TExecutor exec = new TExecutor();
        final String query = "insert into users (name, password) values(?,?)";
        final String[] params = {name, password};
        try {
            exec.execUpdate(connection, query, params);
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.err.println("User " + name + " is already exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isUserExist(String name) {
        TExecutor exec = new TExecutor();
        final String query = "select name from users where name = ?";
        final String[] parameters = {name};
        try {
            return exec.execQuery(connection, query, parameters, result -> result.isBeforeFirst());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkPassword(String name, String password) {
        TExecutor exec = new TExecutor();
        final String query = "select name from users where name = ? and password = ?";
        final  String[] parameters = {name,password};
        try {
            return exec.execQuery(connection, query, parameters, result -> result.isBeforeFirst());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getUsersCount() {
        final String query = "select count(*) from users";
        TExecutor executor = new TExecutor();
        int usersCount = 0;
        try {
            usersCount = executor.execQuery(connection, query, result -> {
                result.next();
                return result.getInt(1);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersCount;
    }

}
