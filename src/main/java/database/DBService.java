package database;

import main.UserProfile;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Сергей on 20.11.2015.
 */
public class DBService {
    private Connection connection;

    public DBService(){
        connection = getConnection();
    }

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

            System.out.append("URL: " + url + "\n");

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
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
}
