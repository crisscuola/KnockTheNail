package mechanics;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import example.TimeHelper;

import java.util.*;


public class GameMechanicsImpl implements GameMechanics {
    private static final int STEP_TIME = 100;

    private WebSocketService webSocketService;

    private Map<String, GameSession> nameToGame = new HashMap<>();

    private Set<GameSession> allSessions = new HashSet<>();

    private String waiter;

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Override
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

    @Override
    public void incrementScore(String userName) {
        GameSession myGameSession = nameToGame.get(userName);

        GameUser myUser = myGameSession.getSelf(userName);
        myUser.incrementMyScore();

        GameUser enemyUser = myGameSession.getEnemy(userName);
        enemyUser.incrementEnemyScore();

        myGameSession.incrementCommonScore();
        myGameSession.setLastClick(myUser);

        webSocketService.notifyMyNewScore(myUser);
        webSocketService.notifyEnemyNewScore(enemyUser);
        webSocketService.notifyCommonScore(myUser);
        webSocketService.notifyCommonScore(enemyUser);
    }

    @Override
    public void run() {
        while (true) {
            try {
                gmStep();
                TimeHelper.sleep(STEP_TIME);
            } catch (RuntimeException e){
                e.printStackTrace();
                break;
            }
        }
    }

    private void gmStep() {
        for (GameSession session : allSessions) {
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