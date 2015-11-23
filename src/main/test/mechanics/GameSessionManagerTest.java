package mechanics;

import base.Nail;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameSessionManagerTest {
    @NotNull
    private final GameSessionManager gameSessionManager = new GameSessionManager();

    @Test
    public void testIncrementScore(){
        UserProfile first = new UserProfile("1","1",0,0);
        first.setId(1);
        UserProfile second = new UserProfile("2","2",0,0);
        second.setId(2);
        Nail nail = new Nail();
        gameSessionManager.startGame(first,second,nail);
        float score1 = gameSessionManager.get(1).getSelf(1).getMyScore();
        float score2 = gameSessionManager.get(1).getEnemy(1).getEnemyScore();
        System.out.println(score1);
        System.out.println(score2);
        assertTrue(score1 == score2);
        gameSessionManager.incrementScore(1,20);
        float score3 = gameSessionManager.get(1).getSelf(1).getMyScore();
        float score4 = gameSessionManager.get(1).getEnemy(1).getEnemyScore();
        assertTrue(score3 == score4);
        assertTrue(score1 != score3);
        assertTrue(score3 == 20);
        assertTrue(gameSessionManager.get(1).getCommonScore() == 20);
        assertTrue(gameSessionManager.get(1).getFrictionRate() > 1);
        assertTrue(gameSessionManager.get(1).isFirstWin());
    }
}