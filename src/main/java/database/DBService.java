package database;

import main.UserProfile;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;


public class DBService {
    private Connection connection;

    public DBService(){
        connection = getConnection();
    }

    @Nullable
    private static Connection getConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").            //host name
                    append("3306/").                //port
                    append("db_knock?").            //db name
                    append("user=test&").            //login
                    append("password=test");        //password

            System.out.append("URL: ").append(url).append('\n');

            return DriverManager.getConnection(url.toString());
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            System.out.println("Database connection failed");
            System.exit(1);
        }
        return null;
    }

    public void createUser(String name, String password){
        UsersDAO usersDAO = new UsersDAO(connection);
        usersDAO.createUser(name,password);
    }

    public boolean isUserExist(String name){
        UsersDAO usersDAO = new UsersDAO(connection);
        return usersDAO.isUserExist(name);
    }

    public boolean checkPassword(String name, String password){
        UsersDAO usersDAO = new UsersDAO(connection);
        return usersDAO.checkPassword(name,password);
    }
    public UserProfile getUser(long id){
        UsersDAO usersDAO = new UsersDAO(connection);
        UsersDataSet usersDataSet = usersDAO.get(id);
        UserProfile userProfile = new UserProfile(usersDataSet.getName(),usersDataSet.getPassword(),usersDataSet.getWin(),usersDataSet.getLose());
        userProfile.setId(usersDataSet.getId());
        return userProfile;
    }

    @Nullable
    public UserProfile getUser(String name){
        UsersDAO usersDAO = new UsersDAO(connection);
        UsersDataSet usersDataSet = usersDAO.get(name);
        if (usersDataSet == null)
            return null;
        UserProfile userProfile = new UserProfile(usersDataSet.getName(),usersDataSet.getPassword(),usersDataSet.getWin(),usersDataSet.getLose());
        userProfile.setId(usersDataSet.getId());
        return userProfile;
    }

    public int getUsersCount(){
        UsersDAO usersDAO = new UsersDAO(connection);
        return usersDAO.getUsersCount();
    }

    public void incrementWons(long id){
        UsersDAO usersDAO = new UsersDAO(connection);
        usersDAO.incrementWons(id);
    }

    public void incrementLoses(long id){
        UsersDAO usersDAO = new UsersDAO(connection);
        usersDAO.incrementLoses(id);
    }

    public void clearUserTable() {
        UsersDAO usersDAO = new UsersDAO(connection);
        usersDAO.clear();
    }

    public ArrayList<UserProfile> getUsersScoreboard(int limit) {
        UsersDAO usersDAO = new UsersDAO(connection);
        ArrayList<UsersDataSet> users = usersDAO.getUsersScoreboard(limit);
        ArrayList<UserProfile> userProfiles = new ArrayList<>();
        for (UsersDataSet user : users) {
            UserProfile userProfile = new UserProfile(user.getName(), user.getPassword(), user.getWin(), user.getLose());
            userProfile.setId(user.getId());
            userProfiles.add(userProfile);
        }
        return userProfiles;
    }

    public ArrayList<UserProfile> getUsersScoreboard() {
        UsersDAO usersDAO = new UsersDAO(connection);
        ArrayList<UsersDataSet> users = usersDAO.getUsersScoreboard();
        ArrayList<UserProfile> userProfiles = new ArrayList<>();
        for (UsersDataSet user: users) {
            UserProfile userProfile = new UserProfile(user.getName(),user.getPassword(),user.getWin(),user.getLose());
            userProfile.setId(user.getId());
            userProfiles.add(userProfile);
        }
        return userProfiles;
    }
}
