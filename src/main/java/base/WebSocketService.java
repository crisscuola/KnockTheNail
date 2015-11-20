package base;

import frontend.GameWebSocket;


public interface WebSocketService {

    void addUser(GameWebSocket user);

    void notifyMyNewScore(GameUser user);

    void notifyEnemyNewScore(GameUser user);

    void notifyStartGame(GameUser user, boolean shouldClick);

    void notifyCommonScore(GameUser user);

    void notifyGameOver(GameUser user, boolean win);

    void notifyDisconnect(GameUser user);
}

