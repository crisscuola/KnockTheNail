package mechanics;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import example.TimeHelper;
import main.UserProfile;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


public class GameMechanicsImpl implements GameMechanics {
    private static final int STEP_TIME = 100;

    private WebSocketService webSocketService;

    private Map<Integer, GameSession> nameToGame = new HashMap<>();

    private Set<GameSession> allSessions = new HashSet<>();

    private Queue<UserProfile> usersToGame = new ConcurrentLinkedQueue<>();

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    public void addUser(UserProfile user) {
        usersToGame.add(user);
    }

    @Override
    public void incrementScore(int id) {
        GameSession myGameSession = nameToGame.get(id);

        GameUser myUser = myGameSession.getSelf(id);
        myUser.incrementMyScore();

        GameUser enemyUser = myGameSession.getEnemy(id);
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
        if(usersToGame.size() >= 2){
            UserProfile first = usersToGame.poll();
            UserProfile second = usersToGame.poll();
            starGame(first,second);
        }
        for (GameSession session : allSessions) {
            if (session.getCommonScore() >= 20) {
                boolean firstWin = session.isFirstWin();
                webSocketService.notifyGameOver(session.getFirst(), firstWin);
                webSocketService.notifyGameOver(session.getSecond(), !firstWin);
                allSessions.remove(session);
            }
        }
    }


    private void starGame(UserProfile first, UserProfile second) {
        GameSession gameSession = new GameSession(first, second);
        allSessions.add(gameSession);

        nameToGame.put(first.getId(), gameSession);
        nameToGame.put(second.getId(), gameSession);

        System.out.println("GameMech StartGame() -> notifyStartGame");
        webSocketService.notifyStartGame(gameSession.getSelf(first.getId()));
        webSocketService.notifyStartGame(gameSession.getSelf(second.getId()));
    }
}