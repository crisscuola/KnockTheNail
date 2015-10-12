package main;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;


import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class AccountServiceTest {
    private AccountService accountService;
    private UserProfile userProfile;

    @NotNull
    private Map<String, UserProfile> sessions = new HashMap<>();
    @NotNull
    private Map<String, UserProfile> users = new HashMap<>();


    @Test
    public void testSignUp() throws Exception {
        accountService = new AccountService();
        userProfile = new UserProfile("name", "password");
        accountService.addUser("name", userProfile);
        UserProfile result = accountService.getUser("name");
        assertNotNull(result);

    }


    @Test
    public void testAddUser() throws Exception {
        accountService = new AccountService();
        userProfile = new UserProfile("name", "password");
        accountService.addUser("name", userProfile);
        userProfile = new UserProfile("name", "");
        accountService.addUser("name", userProfile);
        users.put("name", userProfile);

    }

    @Test
    public void testAddSessions() throws Exception {
        accountService = new AccountService();
        String sessionId = "session";
        userProfile = new UserProfile("name", "password");
        accountService.addSessions(sessionId, userProfile);

    }

    @Test
    public void testGetUser() throws Exception {

    }

    @Test
    public void testIsSignedIn() throws Exception {
        accountService = new AccountService();
        String sessionId = "session";
        userProfile = new UserProfile("name", "password");
        accountService.addSessions(sessionId, userProfile);

    }

    @Test
    public void testRemoveSessions() throws Exception {

    }

    @Test
    public void testCountSignIn() throws Exception {


    }

    @Test
    public void testCountSignUp() throws Exception {

    }
}