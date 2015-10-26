package main;


import base.GameUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class AccountService {

    @NotNull
    private Map<String, UserProfile> users = new HashMap<>();
    @NotNull
    private Map<String, UserProfile> sessions = new HashMap<>();

    public boolean addUser(String userName, UserProfile userProfile) {
        if (users.containsKey(userName))
            return false;
        int id = users.size();
        userProfile.setId(id);
        users.put(userName, userProfile);
        return true;
    }

    public UserProfile createRandomUser(){
        Random random = new Random();
        Integer randInt = random.nextInt();
        String name = randInt.toString();
        int id = users.size();
        UserProfile user = new UserProfile(name,"");
        user.setId(id);
        users.put(name,user);
        return user;
    }

    public void addSessions(String sessionId, UserProfile userProfile) {
        sessions.put(sessionId, userProfile);
    }

    @Nullable
    public UserProfile getUser(String userName) {
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

