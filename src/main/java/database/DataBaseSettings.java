package database;


public class DataBaseSettings {

    private final String type;
    private final String host;
    private final int port;
    private final String dbname;
    private final String user;
    private final String password;

    public DataBaseSettings(String type, String host, String dbname, String user, String password, int port) {
        this.type = type;
        this.host = host;
        this.port = port;
        this.dbname = dbname;
        this.user = user;
        this.password = password;
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

    public String getDBName() {
        return dbname;
    }

    public String getUser() { return user;}

    public String getPassword() {
        return password;
    }
}
