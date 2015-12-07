package database;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import main.UserProfile;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;


public class DBService {

    private UsersDAO usersDAO;

    public DBService(@Nullable DataBaseSettings dataBaseSettings){
        Connection connection = getConnection(dataBaseSettings);
        usersDAO = new UsersDAO(connection);
    }

    @Nullable
    private static Connection getConnection(DataBaseSettings dataBaseSettings) {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append(dataBaseSettings.getType()).
                    append("://").
                    append(dataBaseSettings.getHost()).
                    append(':').
                    append(String.valueOf(dataBaseSettings.getPort())).
                    append('/').
                    append(dataBaseSettings.getDBName()).
                    append('?').
                    append("user=").append(dataBaseSettings.getUser()).
                    append('&').
                    append("password=").
                    append(dataBaseSettings.getPassword());

            System.out.append("URL: ").append(url).append('\n');

            return DriverManager.getConnection(url.toString());
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println("Database connection failed");
            System.exit(1);
        }
        return null;
    }

    public void createUser(String name, String password){
        try {
            usersDAO.createUser(name, password);
        } catch (MySQLIntegrityConstraintViolationException e){
            System.err.println("User " + name + " is already exists");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isUserExist(String name){
        try {
            return usersDAO.isUserExist(name);
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkPassword(String name, String password){
        try {
            return usersDAO.checkPassword(name, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Nullable
    public UserProfile getUser(long id){
        UsersDataSet usersDataSet;
        try {
            usersDataSet = usersDAO.get(id);
        } catch (SQLException e) {
            System.out.println("User with id " + String.valueOf(id) + " doesn't exist");
            return null;
        }
        UserProfile userProfile = new UserProfile(usersDataSet.getName(),usersDataSet.getPassword(),usersDataSet.getWin(),usersDataSet.getLose());
        userProfile.setId(usersDataSet.getId());
        return userProfile;
    }

    @Nullable
    public UserProfile getUser(String name){
        UsersDataSet usersDataSet;
        try {
            usersDataSet = usersDAO.get(name);
        } catch (SQLException e) {
            System.out.println("User with name " + name + " doesn't exist");
            return null;
        }
        if (usersDataSet == null)
            return null;
        UserProfile userProfile = new UserProfile(usersDataSet.getName(),usersDataSet.getPassword(),usersDataSet.getWin(),usersDataSet.getLose());
        userProfile.setId(usersDataSet.getId());
        return userProfile;
    }

    public int getUsersCount() {
        try {
            return usersDAO.getUsersCount();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void incrementWons(long id) {
        try {
            usersDAO.incrementWons(id);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void incrementLoses(long id) {
        try {
            usersDAO.incrementLoses(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearUserTable() {
        try {
            usersDAO.clear();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<UserProfile> getUsersScoreboard(int limit) {
        ArrayList<UsersDataSet> users;
        try {
            users = usersDAO.getUsersScoreboard(limit);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        ArrayList<UserProfile> userProfiles = new ArrayList<>();
        for (UsersDataSet user : users) {
            UserProfile userProfile = new UserProfile(user.getName(), user.getPassword(), user.getWin(), user.getLose());
            userProfile.setId(user.getId());
            userProfiles.add(userProfile);
        }
        return userProfiles;
    }

    public ArrayList<UserProfile> getUsersScoreboard() {
        ArrayList<UsersDataSet> users;
        try {
            users = usersDAO.getUsersScoreboard();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        ArrayList<UserProfile> userProfiles = new ArrayList<>();
        for (UsersDataSet user: users) {
            UserProfile userProfile = new UserProfile(user.getName(),user.getPassword(),user.getWin(),user.getLose());
            userProfile.setId(user.getId());
            userProfiles.add(userProfile);
        }
        return userProfiles;
    }
}
