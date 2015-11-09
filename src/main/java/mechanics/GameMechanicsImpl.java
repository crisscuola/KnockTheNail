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



    private Map<Integer, GameSession> usersInGame = new HashMap<>();

    private Queue<UserProfile> usersToGame = new ConcurrentLinkedQueue<>();

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Override
    public void addUser(UserProfile user) {
        usersToGame.add(user);
    }

    @Override
    public void removeUserToGame(UserProfile user){
        usersToGame.remove(user);
        int id = user.getId();
        GameSession myGameSession = usersInGame.get(id);
        GameUser enemyUser = myGameSession.getEnemy(id);
        webSocketService.notifyDisconnect(enemyUser);
    }

    @Override
    public  void removeUserInGame(UserProfile user) {
        usersInGame.remove(user);
        int id = user.getId();
        GameSession myGameSession = usersInGame.get(id);
        GameUser enemyUser = myGameSession.getEnemy(id);
        webSocketService.notifyDisconnect(enemyUser);
    }

    @Override
    public void incrementScore(int id, int force) {
        GameSession myGameSession = usersInGame.get(id);

        GameUser myUser = myGameSession.getSelf(id);
        myUser.incrementMyScore(force);

        GameUser enemyUser = myGameSession.getEnemy(id);
        enemyUser.incrementEnemyScore(force);

        myGameSession.incrementCommonScore();
        myGameSession.setLastClick(myUser);


        webSocketService.notifyMyNewScore(myUser);
        webSocketService.notifyEnemyNewScore(enemyUser);
        webSocketService.notifyCommonScore(myUser);
        webSocketService.notifyCommonScore(enemyUser);


        if(myGameSession.getCommonScore() >= 20){
            boolean firstWin = myGameSession.isFirstWin();
            webSocketService.notifyGameOver(myGameSession.getFirst(), firstWin);
            webSocketService.notifyGameOver(myGameSession.getSecond(), !firstWin);
            usersInGame.remove(id);
            usersInGame.remove(enemyUser.getMyId());
        }
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
    }


    private void starGame(UserProfile first, UserProfile second) {
        GameSession gameSession = new GameSession(first, second);

        usersInGame.put(first.getId(), gameSession);
        usersInGame.put(second.getId(), gameSession);

        System.out.println("GameMech StartGame() -> notifyStartGame");
        webSocketService.notifyStartGame(gameSession.getSelf(first.getId()));
        webSocketService.notifyStartGame(gameSession.getSelf(second.getId()));
    }
}