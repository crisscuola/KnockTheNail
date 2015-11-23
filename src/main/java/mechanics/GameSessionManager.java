package mechanics;

import base.GameUser;
import base.Nail;
import main.UserProfile;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.Map;


public class GameSessionManager {
    private Map<Long, GameSession> usersInGame = new HashMap<>();

    public void startGame(UserProfile first, UserProfile second, Nail nail){
        GameSession gameSession = new GameSession(first, second,nail);

        usersInGame.put(first.getId(), gameSession);
        usersInGame.put(second.getId(), gameSession);
    }

    public void incrementScore(long id, int force){
        GameSession myGameSession = usersInGame.get(id);

        GameUser myUser = myGameSession.getSelf(id);
        myUser.incrementMyScore(force);
        myUser.changeFrictionRate();

        GameUser enemyUser = myGameSession.getEnemy(id);
        enemyUser.incrementEnemyScore(force);
        enemyUser.changeFrictionRate();

        myGameSession.incrementCommonScore(force);
        myGameSession.changeFrictionRate();

        myGameSession.changeShouldClick();

        myGameSession.setLastClick(myUser);
    }

    public GameSession get(long id){
        return usersInGame.get(id);
    }

    public void removeSessions(long id){
        GameSession myGameSession = usersInGame.get(id);
        if(myGameSession != null){
            GameUser enemyUser = myGameSession.getEnemy(id);
            usersInGame.remove(id);
            usersInGame.remove(enemyUser.getMyId());
        }
    }
}
