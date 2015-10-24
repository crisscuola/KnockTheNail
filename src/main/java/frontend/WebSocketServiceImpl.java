package frontend;

import base.GameUser;
import base.WebSocketService;

import java.util.HashMap;
import java.util.Map;


public class WebSocketServiceImpl implements WebSocketService {
    private Map<String, GameWebSocket> userSockets = new HashMap<>();

    public void addUser(GameWebSocket user) {
        userSockets.put(user.getMyName(), user);
    }

    public void notifyMyNewScore(GameUser user) {
        userSockets.get(user.getMyName()).setMyScore(user);
    }

    public void notifyEnemyNewScore(GameUser user) {
        userSockets.get(user.getMyName()).setEnemyScore(user);
    }

    public  void notifyCommonScore(GameUser user) {userSockets.get(user.getMyName()).setCommonScore(user);}

    public void notifyStartGame(GameUser user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        System.out.println("notifyStartGame");
        gameWebSocket.startGame(user);
    }

    @Override
    public void notifyGameOver(GameUser user, boolean win) {
        userSockets.get(user.getMyName()).gameOver(user, win);
    }
}