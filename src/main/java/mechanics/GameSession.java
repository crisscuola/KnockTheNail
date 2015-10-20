package mechanics;


import base.GameUser;

import java.util.HashMap;
import java.util.Map;

public class GameSession {
    private final GameUser first;
    private final GameUser second;

    private Map<String, GameUser> users = new HashMap<>();

    public GameSession(String user1, String user2) {

        GameUser gameUser1 = new GameUser(user1);
        gameUser1.setEnemyName(user2);

        GameUser gameUser2 = new GameUser(user2);
        gameUser2.setEnemyName(user1);

        users.put(user1, gameUser1);
        users.put(user2, gameUser2);

        this.first = gameUser1;
        this.second = gameUser2;
    }

    public GameUser getEnemy(String user) {
        String enemyName = users.get(user).getEnemyName();
        return users.get(enemyName);
    }
}
