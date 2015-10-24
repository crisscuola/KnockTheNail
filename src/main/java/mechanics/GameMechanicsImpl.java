package mechanics;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import example.TimeHelper;
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

    //private Boolean  win = true;

    //public void setWin() { win = !win; }

    //public boolean checkWin() { return win;}

    public void addUser(String user) {
        if (waiter != null) {
            starGame(user);
            waiter = null;
            System.out.println("GameMech AddUser2 OK");
        } else {
            waiter = user;
            System.out.println("GameMech AddUser1 OK");
        }
    }

    public void incrementScore(String userName) {
        GameSession myGameSession = nameToGame.get(userName);

        GameUser myUser = myGameSession.getSelf(userName);
        myUser.incrementMyScore();

        GameUser enemyUser = myGameSession.getEnemy(userName);
        enemyUser.incrementEnemyScore();

        myGameSession.incrementCommonScore();

        webSocketService.notifyMyNewScore(myUser);
        webSocketService.notifyEnemyNewScore(enemyUser);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("run()");
            gmStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }

    private void gmStep() {
        for (GameSession session : allSessions) {
            System.out.println(session.getFirst().getMyName());
            if (session.getCommonScore() >= 20) {
                boolean firstWin = session.isFirstWin();
                webSocketService.notifyGameOver(session.getFirst(), firstWin);
                webSocketService.notifyGameOver(session.getSecond(), !firstWin);
                allSessions.remove(session);
            }
        }
    }


    private void starGame(String first) {
        String second = waiter;
        GameSession gameSession = new GameSession(first, second);
        allSessions.add(gameSession);
        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);

        System.out.println("GameMech StartGame() -> notifyStartGame");
        webSocketService.notifyStartGame(gameSession.getSelf(first));
        webSocketService.notifyStartGame(gameSession.getSelf(second));
    }
}