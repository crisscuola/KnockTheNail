package base;


public class GameUser {
    private final String myName;
    private String enemyName;
    private int myScore = 0;
    private int enemyScore = 0;
    private  int commonScore = 0;
    private Boolean lastClick = true;

    public GameUser(String myName) {
        this.myName = myName;
    }

    public String getMyName() {
        return myName;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public int getMyScore() {
        return myScore;
    }

    public int getEnemyScore() { return enemyScore; }

    public  int getCommonScore() { return commonScore; }

    public void incrementMyScore() { myScore++; }

    public void incrementEnemyScore() { enemyScore++; }

    public void incrementCommonScore() { commonScore++; }

    public void setEnemyName(String enemyName) { this.enemyName = enemyName; }

    public  void setLastclick() { lastClick =  !lastClick; }

}