package mechanics;

import base.GameUser;
import main.UserProfile;
import base.Nail;


public class GameSession {
    private final GameUser first;
    private final GameUser second;
    private float commonScore;
    private float  frictionRate;
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

        frictionRate+=commonScore/100;

        //frictionRate+=5;

    }

    public void incrementCommonScore(float force){

        commonScore+= (force/frictionRate);

        System.out.println("CommonScore inc "+commonScore);
    }

    public float getCommonScore() {
        return commonScore;
    }

    public  float getFrictionRate() { return  frictionRate; }

    public void setLastClick(GameUser user) {
        lastClick = user;
    }
}
