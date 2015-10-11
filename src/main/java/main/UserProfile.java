package main;


import org.jetbrains.annotations.Nullable;

public class UserProfile {
    private String name;
    private String password;
    private Boolean isLogged;

    public UserProfile(String login, String password) {
        this.name = login;
        this.password = password;
        this.isLogged = false;
    }

    public void setIsLogged(boolean status) {
        isLogged = status;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getPassword() {
        return password;
    }
}
