package main;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;


public class AccountService {

    @NotNull
    private Map<String, UserProfile> users = new HashMap<>();
    @NotNull
    private Map<String, UserProfile> sessions = new HashMap<>();

    public boolean addUser(String userName, UserProfile userProfile) {
        if (users.containsKey(userName))
            return false;
        users.put(userName, userProfile);
        return true;
    }

    public void addSessions(String sessionId, UserProfile userProfile) {
        sessions.put(sessionId, userProfile);
    }

    @Nullable
    public UserProfile getUser(String userName) {
        try {
            return users.get(userName);
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Nullable
    public UserProfile getUserBySession(String session) {
//        try {
//            return sessions.get(session);
//        } catch (NullPointerException e) {
//            return null;
//        }
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
        try {
            return sessions.size();

        } catch (NullPointerException e) {
            return -1;
        }
    }

    public int countSignUp() {
        return users.size();
    }
}

