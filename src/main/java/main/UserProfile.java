package main;


import org.jetbrains.annotations.Nullable;

public class UserProfile {
    private String name;
    private String password;
    private int id;

    public UserProfile(String login, String password) {
        this.name = login;
        this.password = password;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
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
