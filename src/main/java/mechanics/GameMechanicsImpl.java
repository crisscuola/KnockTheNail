package mechanics;

import base.GameMechanics;
import base.Nail;
import base.WebSocketService;
import database.DBService;
import example.ReadXMLFileSAX;
import example.TimeHelper;
import main.UserProfile;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


public class GameMechanicsImpl implements GameMechanics {
    private static final int STEP_TIME = 100;

    private WebSocketService webSocketService;

    private Nail nail;

    private DBService dbService;

    GameSessionManager gameSessionManager = new GameSessionManager();

    private Queue<UserProfile> usersToGame = new ConcurrentLinkedQueue<>();

    public GameMechanicsImpl(WebSocketService webSocketService, DBService dbService) {
        this.webSocketService = webSocketService;
        this.nail = (Nail) ReadXMLFileSAX.readXML("data/nail.xml");
        this.dbService = dbService;
    }

    @Override
    public void notifyUserConnected(UserProfile user){
        usersToGame.add(user);
    }

    @Override
    public void notifyUserDisconnected(UserProfile user){
        usersToGame.remove(user);
        long id = user.getId();
        GameSession gameSession = gameSessionManager.get(id);
        if(gameSession != null){
            webSocketService.notifyDisconnect(gameSession.getEnemy(id));
            gameSessionManager.removeSessions(id);
        }
    }

    @Override
    public void incrementScore(long id, int force) {
        gameSessionManager.incrementScore(id,force);
        GameSession gameSession = gameSessionManager.get(id);

        webSocketService.notifyMyNewScore(gameSession.getSelf(id));
        webSocketService.notifyEnemyNewScore(gameSession.getEnemy(id));
        webSocketService.notifyCommonScore(gameSession.getSelf(id));
        webSocketService.notifyCommonScore(gameSession.getEnemy(id));

        if(gameSession.getCommonScore() >= nail.getHealth()){
            boolean firstWin = gameSession.isFirstWin();
            webSocketService.notifyGameOver(gameSession.getFirst(), firstWin);
            webSocketService.notifyGameOver(gameSession.getSecond(), !firstWin);
            if (firstWin){
                dbService.incrementWons(gameSession.getFirst().getMyId());
                dbService.incrementLoses(gameSession.getFirst().getEnemyId());
            }
            else {
                dbService.incrementWons(gameSession.getFirst().getEnemyId());
                dbService.incrementLoses(gameSession.getFirst().getMyId());
            }
//
            gameSessionManager.removeSessions(id);
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
            gameSessionManager.startGame(first,second,nail);
            webSocketService.notifyStartGame(gameSessionManager.get(first.getId()).getSelf(first.getId()), true);
            webSocketService.notifyStartGame(gameSessionManager.get(first.getId()).getSelf(second.getId()), false);
        }
    }
}