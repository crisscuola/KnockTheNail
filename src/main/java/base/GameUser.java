package base;


public class GameUser {
    private int myId;
    private int enemyId;
    private final String myName;
    private String enemyName;
    private float myScore = 0;
    private float enemyScore = 0;
    private  double frictionRate = 1;


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

    public float getMyScore() {return myScore;}

    public float getEnemyScore() { return enemyScore; }

    public  double getFrictionRate() { return  frictionRate; }

    public double changeFrictionRate() { return frictionRate++;}


    public void incrementMyScore(int force) { myScore += force/frictionRate; }

    public void incrementEnemyScore(int force) { enemyScore+=force/frictionRate; }

    public void setEnemyName(String enemyName) { this.enemyName = enemyName; }
}