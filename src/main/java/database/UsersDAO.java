package database;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.ArrayList;


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
            return exec.execQuery(connection, "select * from users where name='" + name + "'", result -> {
                result.next();
                return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3), result.getInt(4), result.getInt(5));
            });
        } catch (SQLException e){
            System.out.println("User with name " + name + " doesn't exist");
            //e.printStackTrace();
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
        TExecutor exec = new TExecutor();
        int usersCount = 0;
        try {
            usersCount = exec.execQuery(connection, query, result -> {
                result.next();
                return result.getInt(1);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersCount;
    }

    public void incrementWons(long id) {
        final String query = "update users set wons=wons+1 where id =" + id;
        TExecutor exec = new TExecutor();
        try {
            exec.execUpdate(connection, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void incrementLoses(long id) {
        final String query = "update users set loses=loses+1 where id =" + id;
        TExecutor exec = new TExecutor();
        try {
            exec.execUpdate(connection, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        final String query = "truncate table users";
        TExecutor exec = new TExecutor();
        try {
            exec.execUpdate(connection, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<UsersDataSet> getUsersScoreboard(int limit){
        final String query = "select * from users order by wons desc, loses asc limit " + String.valueOf(limit);
        TExecutor exec = new TExecutor();
        ArrayList<UsersDataSet> usersDataSets = new ArrayList<>();
        try {
            return exec.execQuery(connection, query, result -> {
                while(true){
                    if (result.next()){
                            usersDataSets.add(new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3), result.getInt(4), result.getInt(5)));
                    } else {
                        break;
                    }
                }
                return usersDataSets;
            });
        } catch (SQLException e){
            //e.printStackTrace();
        }
        return  null;
    }

    public ArrayList<UsersDataSet> getUsersScoreboard(){
        final String query = "select * from users order by wons desc, loses asc";
        TExecutor exec = new TExecutor();
        ArrayList<UsersDataSet> usersDataSets = new ArrayList<>();
        try {
            return exec.execQuery(connection, query, result -> {
                while(true){
                    if (result.next()){
                        usersDataSets.add(new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3), result.getInt(4), result.getInt(5)));
                    } else {
                        break;
                    }
                }
                return usersDataSets;
            });
        } catch (SQLException e){
            //e.printStackTrace();
        }
        return  null;
    }


}
