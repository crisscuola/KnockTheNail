package base;


import org.json.simple.JSONObject;

public interface GameMechanics {

    public void addUser(String user);

    public void incrementScore(String userName);

    public void run();
}