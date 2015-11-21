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

    public GameSession(UserProfile user1, UserProfile user2, Nail nail) {
        long id1 = user1.getId();
        long id2 = user2.getId();

        String name1 = user1.getName();
        String name2 = user2.getName();

        GameUser gameUser1 = new GameUser(name1,id1,nail);
        gameUser1.setEnemyName(name2);
        gameUser1.setEnemyId(id2);
        gameUser1.setShouldClick(true);


        GameUser gameUser2 = new GameUser(name2,id2,nail);
        gameUser2.setEnemyName(name1);
        gameUser2.setEnemyId(id1);
        gameUser2.setShouldClick(false);

        this.first = gameUser1;
        this.second = gameUser2;

        commonScore = 0;

        frictionRate = 1;

        System.out.println("Game Session OK");

        this.nail=nail;
    }

    public GameUser getEnemy(long id) {
        return first.getMyId() == id ? second : first;
    }

    public GameUser getSelf(long id) {
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
        frictionRate+=commonScore/nail.getHealth();
    }

    public void incrementCommonScore(float force){
        commonScore+= (force/frictionRate);
        System.out.println("CommonScore inc "+commonScore);
    }

    public float getCommonScore() {
        return commonScore;
    }

    public  float getFrictionRate() { return  frictionRate; }

    public void changeShouldClick() {
        this.first.setShouldClick(!this.first.getShouldClick());
        this.second.setShouldClick(!this.second.getShouldClick());
    }

    public void setLastClick(GameUser user) {
        lastClick = user;
    }
}
