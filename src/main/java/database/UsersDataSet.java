package database;

public class UsersDataSet {
    private  long id;
    private String name;
    private String password;
    private Integer win;
    private  Integer lose;

    public UsersDataSet(long id,String name, String password) {
        this.id = id;
        this.name = name;
        this.password =password;
        this.win = 0;
        this.lose = 0;
    }

    public  UsersDataSet(long id,String name, String password, Integer win , Integer lose) {
        this.id = id;
        this.name = name;
        this.password =password;
        this.win = win;
        this.lose = lose;
    }

    public String getName() {
        return name;
    }

    public  String getPassword() {
        return  password;
    }

    public  Integer getWin() {
        return  win;
    }

    public  Integer getLose() {
        return  lose;
    }

    public  long getId () {
        return  id;
    }


}
