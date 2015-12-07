package base;



public class GameUser {
    private long myId;
    private long enemyId;
    private final String myName;
    private String enemyName;
    private float myScore = 0;
    private float enemyScore = 0;
    private  double frictionRate = 1;
    private boolean shouldClick = false;
    private Nail nail;


    public GameUser(String myName, long id, Nail nail) {
        this.myName = myName;
        this.myId = id;

        this.nail = nail;
    }

    public String getMyName() {
        return myName;
    }

    public long getMyId(){
        return this.myId;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public long getEnemyId() { return enemyId;}

    public void setEnemyId(long id) { this.enemyId = id;}

    public float getMyScore() {return myScore;}

    public float getEnemyScore() { return enemyScore; }

    public  double getFrictionRate() { return  frictionRate; }

    public double changeFrictionRate() {
        return frictionRate+=(myScore + enemyScore)/nail.getHealth();}

    public void incrementMyScore(int force) { myScore += force/frictionRate; }

    public void incrementEnemyScore(int force) { enemyScore+=force/frictionRate; }

    public void setEnemyName(String enemyName) { this.enemyName = enemyName; }

    public void setShouldClick(boolean currentClick) { this.shouldClick = currentClick;}

    public boolean getShouldClick() {return shouldClick;}
}