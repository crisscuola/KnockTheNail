package main;

import database.DBService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Сергей on 20.11.2015.
 */
public class AccountService {

    @NotNull
    private DBService dbService;
    @NotNull
    private Map<String, UserProfile> sessions = new HashMap<>();

    public AccountService(@NotNull DBService dbService){
        this.dbService = dbService;
    }
    public boolean addUser(String userName, UserProfile userProfile) {
        if (dbService.isUserExist(userName)){
            return false;
        }
        dbService.createUser(userName, userProfile.getPassword());
        return true;
    }

    public void addSessions(String sessionId, UserProfile userProfile) {
        sessions.put(sessionId, userProfile);
    }

    @Nullable
    public UserProfile getUser(String userName) {
        return dbService.getUser(userName);
    }

    @Nullable
    public UserProfile getUserBySession(String session) {
        if(sessions.containsKey(session))
            return sessions.get(session);
        else
            return null;
    }

    @Nullable
    public String isSignedIn(String sessionId) {
        if (sessions.containsKey(sessionId))
            return sessionId;
        else
            return null;
    }

    public boolean removeSession(String sessionId) {
        if (sessions.containsKey(sessionId)) {
            sessions.remove(sessionId);
            return true;
        }
        return false;
    }

    public int countSignIn() {
        return sessions.size();
    }

    public int countSignUp() {
        return dbService.getUsersCount();
    }

    public ArrayList<UserProfile> getUsersScoreBoard(int limit) {
        return dbService.getUsersScoreboard(limit);
    }

    public ArrayList<UserProfile> getUsersScoreBoard() {
        return dbService.getUsersScoreboard();
    }

}