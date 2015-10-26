package base;


public class GameUser {
    private int myId;
    private int enemyId;
    private final String myName;
    private String enemyName;
    private int myScore = 0;
    private int enemyScore = 0;

    public GameUser(String myName, int id) {
        this.myName = myName;
        this.myId = id;
    }

    public String getMyName() {
        return myName;
    }

    public int getMyId(){
        return this.myId;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public int getEnemyId() { return enemyId;}

    public void setEnemyId(int id) { this.enemyId = id;}

    public int getMyScore() {
        return myScore;
    }

    public int getEnemyScore() { return enemyScore; }

    public void incrementMyScore() { myScore++; }

    public void incrementEnemyScore() { enemyScore++; }

    public void setEnemyName(String enemyName) { this.enemyName = enemyName; }
}