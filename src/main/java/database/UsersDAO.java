package database;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;


public class UsersDAO {

    private Connection connection;

    private TExecutor exec = new TExecutor();

    public UsersDAO(@Nullable Connection connection) {
        this.connection = connection;
    }


    public UsersDataSet get(long id) throws SQLException {
        return exec.execQuery(connection, "select * from users where id=" + id, result -> {
            result.next();
            return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3), result.getInt(4), result.getInt(5));
        });
    }

    public UsersDataSet get(String name) throws SQLException {
        return exec.execQuery(connection, "select * from users where name='" + name + '\'', result -> {
            result.next();
            return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3), result.getInt(4), result.getInt(5));
        });
    }

    public void createUser(String name, String password) throws SQLException{
        final String query = "insert into users (name, password) values(?,?)";
        final String[] params = {name, password};
        exec.execUpdate(connection, query, params);
    }

    public boolean isUserExist(String name) throws SQLException {
        final String query = "select name from users where name = ?";
        final String[] parameters = {name};
        return exec.execQuery(connection, query, parameters, ResultSet::isBeforeFirst);
    }

    public boolean checkPassword(String name, String password) throws SQLException {
        final String query = "select name from users where name = ? and password = ?";
        final  String[] parameters = {name,password};
        return exec.execQuery(connection, query, parameters, ResultSet::isBeforeFirst);
    }

    public int getUsersCount() throws SQLException {
        final String query = "select count(*) from users";
        return exec.execQuery(connection, query, result -> {
            result.next();
            return result.getInt(1);
        });
    }

    public void incrementWons(long id) throws SQLException{
        final String query = "update users set wons=wons+1 where id =" + id;
        exec.execUpdate(connection, query);
    }

    public void incrementLoses(long id) throws SQLException{
        final String query = "update users set loses=loses+1 where id =" + id;
        exec.execUpdate(connection, query);
    }

    public void clear() throws SQLException {
        final String query = "truncate table users";
        exec.execUpdate(connection, query);
    }

    public ArrayList<UsersDataSet> getUsersScoreboard(int limit) throws SQLException {
        final String query = "select * from users order by wons desc, loses asc limit " + String.valueOf(limit);
        ArrayList<UsersDataSet> usersDataSets = new ArrayList<>();
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
    }

    public ArrayList<UsersDataSet> getUsersScoreboard() throws SQLException {
        final String query = "select * from users order by wons desc, loses asc";
        ArrayList<UsersDataSet> usersDataSets = new ArrayList<>();
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
    }


}
