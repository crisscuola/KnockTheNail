package main;

import database.DBService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Сергей on 20.11.2015.
 */
public class AccountServiceNew {

    @NotNull
    private DBService dbService;
    @NotNull
    private Map<String, UserProfile> sessions = new HashMap<>();

    public AccountServiceNew(DBService dbService){
        this.dbService = dbService;
    }
    public boolean addUser(String userName, UserProfile userProfile) {
        dbService.createUser(userName, userProfile.getPassword());
        if (users.containsKey(userName))
            return false;
        int id = users.size();
        userProfile.setId(id);
        users.put(userName, userProfile);
        return true;
    }

    public void addSessions(String sessionId, UserProfile userProfile) {
        sessions.put(sessionId, userProfile);
    }

    @Nullable
    public UserProfile getUser(String userName) {
        dbService.
        return users.get(userName);
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
        return users.size();
    }


}