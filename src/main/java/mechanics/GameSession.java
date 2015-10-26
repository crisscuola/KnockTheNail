package mechanics;

import base.GameUser;
import main.UserProfile;

import java.util.HashMap;
import java.util.Map;


public class GameSession {
    private final GameUser first;
    private final GameUser second;
    private int commonScore;
    private GameUser lastClick;

    private Map<Integer, GameUser> users = new HashMap<>();

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

        users.put(id1, gameUser1);
        users.put(id2, gameUser2);

        this.first = gameUser1;
        this.second = gameUser2;

        commonScore = 0;

        System.out.println("Game Session OK");
    }

    public GameUser getEnemy(int id) {
        int enemyId = users.get(id).getEnemyId();
        return users.get(enemyId);
    }

    public GameUser getSelf(int id) {
        return users.get(id);
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

    public void incrementCommonScore(){
        commonScore++;
    }

    public int getCommonScore() {
        return commonScore;
    }

    public void setLastClick(GameUser user) {
        lastClick = user;
    }
}
