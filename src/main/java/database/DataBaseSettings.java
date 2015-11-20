package database;


public class DataBaseSettings {

    private final String type;
    private final String host;
    private final int port;
    private final String name;
    private final String password;

    public DataBaseSettings() {
        this.type = "";
        this.host = "";
        this.port = 0;
        this.name = "";
        this.password = "";
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
