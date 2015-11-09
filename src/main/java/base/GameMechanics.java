package base;


import main.UserProfile;

public interface GameMechanics {

    public boolean addUser(UserProfile user);

    public boolean removeUserToGame(UserProfile user);

    public  void removeUserInGame(UserProfile user);

    public void incrementScore(int userName);

    public void run();
}