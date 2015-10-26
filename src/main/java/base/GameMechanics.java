package base;


import main.UserProfile;

public interface GameMechanics {

    public void addUser(UserProfile user);

    public void incrementScore(int userName);

    public void run();
}