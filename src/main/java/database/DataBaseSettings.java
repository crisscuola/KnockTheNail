package database;


public class DataBaseSettings {

    private final String type;
    private final String host;
    private final int port;
    private final String name;
    private final String password;

    public DataBaseSettings() {
        this.type = "jdbc:mysql://";
        this.host = "localhost:";
        this.port = 3306;
        this.name = "db_knock";
        this.password = "test";
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
