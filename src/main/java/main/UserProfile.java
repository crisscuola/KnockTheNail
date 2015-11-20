package main;


import org.jetbrains.annotations.NotNull;

public class UserProfile {
    private String name;
    private String password;
    private  int win;
    private  int lose;
    private long id;

    public UserProfile(String login, String password, int win, int lose) {
        this.name = login;
        this.password = password;
        this.win = win;
        this.lose = lose;

    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    public  int getWin() {return win; }

    public int getLose() {return  lose; }

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }
}
