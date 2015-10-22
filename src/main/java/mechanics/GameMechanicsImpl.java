package mechanics;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import org.json.simple.JSONObject;
//import utils.TimeHelper;

import java.util.*;


public class GameMechanicsImpl implements GameMechanics {
    private static final int STEP_TIME = 100;

    private static final int gameTime = 15 * 1000000;

    private WebSocketService webSocketService;

    private Map<String, GameSession> nameToGame = new HashMap<>();

    private Set<GameSession> allSessions = new HashSet<>();

    private String waiter;

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    private Boolean  win = true;

    public void setWin() { win = !win; }

    public boolean checkWin() { return win;}

    public void addUser(String user) {
        if (waiter != null) {
            starGame(user);
            waiter = null;
        } else {
            waiter = user;
        }
    }

    public void incrementScore(String userName) {
        GameSession myGameSession = nameToGame.get(userName);

        GameUser myUser = myGameSession.getSelf(userName);
        myUser.incrementMyScore();

        GameUser enemyUser = myGameSession.getEnemy(userName);
        enemyUser.incrementEnemyScore();

        myUser.incrementCommonScore();
        enemyUser.incrementCommonScore();

        setWin();

        webSocketService.notifyMyNewScore(myUser);
        webSocketService.notifyEnemyNewScore(enemyUser);
        webSocketService.notifyCommonNewScore(myUser);
        webSocketService.notifyCommonNewScore(enemyUser);
    }

    @Override
    public void run() {
        while (true) {
            gmStep();
           // TimeHelper.sleep(STEP_TIME);
        }
    }

    private void gmStep() {

        for (GameSession session : allSessions) {

            if(session.getFirst().getCommonScore() >= 20) {
                if (checkWin()) {
                    boolean firstWin = session.isFirstWin();
                    webSocketService.notifyGameOver(session.getFirst(), firstWin);
                    webSocketService.notifyGameOver(session.getSecond(), !firstWin);
                }

                else {
                    boolean firstWin = ! session.isFirstWin();
                    webSocketService.notifyGameOver(session.getFirst(), firstWin);
                    webSocketService.notifyGameOver(session.getSecond(), !firstWin);

                }

            }

        }
    }


    private void starGame(String first) {
        String second = waiter;
        GameSession gameSession = new GameSession(first, second);
        allSessions.add(gameSession);
        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);

        webSocketService.notifyStartGame(gameSession.getSelf(first));
        webSocketService.notifyStartGame(gameSession.getSelf(second));
    }
}