package base;


import main.UserProfile;

public interface GameMechanics {

    public void addUser(UserProfile user);

    public void removeUserToGame(UserProfile user);

    public  void removeUserInGame(UserProfile user);

    public void incrementScore(int userName);

    public void run();
}