package frontend;

import base.GameUser;
import base.WebSocketService;

import java.util.HashMap;
import java.util.Map;


public class WebSocketServiceImpl implements WebSocketService {
    private Map<Integer, GameWebSocket> userSockets = new HashMap<>();

    @Override
    public void addUser(GameWebSocket user) {
        userSockets.put(user.getMyId(), user);
    }

    @Override
    public void notifyMyNewScore(GameUser user) {
        userSockets.get(user.getMyId()).setMyScore(user);
    }

    @Override
    public void notifyEnemyNewScore(GameUser user) {
        userSockets.get(user.getMyId()).setEnemyScore(user);
    }

    @Override
    public  void notifyCommonScore(GameUser user) {userSockets.get(user.getMyId()).setCommonScore(user);}

    @Override
    public void notifyStartGame(GameUser user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyId());
        System.out.println("notifyStartGame");
        gameWebSocket.startGame(user);
    }

    @Override
    public void notifyGameOver(GameUser user, boolean win) {
        userSockets.get(user.getMyId()).gameOver(user, win);
    }
}