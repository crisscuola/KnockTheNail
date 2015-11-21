package base;


import main.UserProfile;

public interface GameMechanics {

    public void incrementScore(long id, int force);

    public void notifyUserConnected(UserProfile user);

    public void notifyUserDisconnected(UserProfile user);

    public void run();
}