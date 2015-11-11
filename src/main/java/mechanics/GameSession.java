package mechanics;

import base.GameUser;
import main.UserProfile;
import base.Nail;


public class GameSession {
    private final GameUser first;
    private final GameUser second;
    private int commonScore;
    private int  frictionRate;
    private GameUser lastClick;

    private Nail nail;

    public GameSession(UserProfile user1, UserProfile user2) {
        int id1 = user1.getId();
        int id2 = user2.getId();

        String name1 = user1.getName();
        String name2 = user2.getName();

        GameUser gameUser1 = new GameUser(name1,id1);
        gameUser1.setEnemyName(name2);
        gameUser1.setEnemyId(id2);

        GameUser gameUser2 = new GameUser(name2,id2);
        gameUser2.setEnemyName(name1);
        gameUser2.setEnemyId(id1);

        this.first = gameUser1;
        this.second = gameUser2;

        commonScore = 0;

        frictionRate = 1;

        System.out.println("Game Session OK");
    }

    public GameUser getEnemy(int id) {
        return first.getMyId() == id ? second : first;
    }

    public GameUser getSelf(int id) {
        return first.getMyId() == id ? first : second;
    }

    public GameUser getFirst() {
        return first;
    }

    public GameUser getSecond() {
        return second;
    }

    public  boolean isFirstWin() {
        return lastClick == first;
    }

    public void changeFrictionRate() {

        frictionRate++;

    }

    public void incrementCommonScore(int force){
        changeFrictionRate();

       // commonScore+= (force/frictionRate);
       // commonScore+= force ;
        System.out.println("CommonScore inc "+commonScore);
    }

    public int getCommonScore() {
        return commonScore;
    }

    public  int getFrictionRate() { return  frictionRate; }

    public void setLastClick(GameUser user) {
        lastClick = user;
    }
}
