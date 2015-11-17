package main;


import org.jetbrains.annotations.NotNull;

public class UserProfile {
    private String name;
    private String password;
    private  int win;
    private  int lose;
    private int id;

    public UserProfile(String login, String password) {
        this.name = login;
        this.password = password;
        this.win = 0;
        this.lose = 0;

    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
