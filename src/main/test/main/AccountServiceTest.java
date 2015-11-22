package main;

import database.DBService;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;


import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class AccountServiceTest {
    @NotNull
    private final DBService dbService = new DBService();
    @NotNull
    private final AccountService accountService = new AccountService(dbService);
    @NotNull
    private final UserProfile userProfile = new UserProfile("1","1",0,0);


    @Test
    public void testAddUser() throws Exception {
        dbService.clearUserTable();
        Boolean result = accountService.addUser("1", userProfile);
        assertTrue(result);
        //assertEquals(userProfile,accountService.getUser("1"));
        Boolean result2 = accountService.addUser("1", userProfile);
        assertEquals(false,result2);
    }


    @Test
    public void testAddSessions() throws Exception {
        String sessionId = "session";
        accountService.addSessions(sessionId, userProfile);
        String result = accountService.isSignedIn(sessionId);
        assertEquals(sessionId,result);
        accountService.addSessions(sessionId, userProfile);
    }

    @Test
    public void testGetUser() throws Exception {
        dbService.clearUserTable();
        assertNull(accountService.getUser("1"));
        accountService.addUser("1", userProfile);
        UserProfile result = accountService.getUser("1");
        //assertEquals(userProfile, result);
        UserProfile result2 = accountService.getUser("abcd");
        assertNull(result2);
    }

    @Test
    public void testIsSignedIn() throws Exception {
        assertNull(accountService.isSignedIn("session"));
        String sessionId = "session";
        accountService.addSessions(sessionId, userProfile);
        String result = accountService.isSignedIn(sessionId);
        assertEquals(sessionId,result);
        String result2 = accountService.isSignedIn("abcd");
        assertNull(result2);
    }

    @Test
    public void testRemoveSessions() throws Exception {
        String sessionId = "session";
        accountService.addSessions(sessionId, userProfile);
        Boolean result = accountService.removeSession(sessionId);
        assertTrue(result);
        assertNull(accountService.isSignedIn("session"));
        Boolean result2 = accountService.removeSession("abcd");
        assertFalse(result2);
    }

    @Test
    public void testCountSignIn() throws Exception {
        String sessionId = "session";
        accountService.addSessions(sessionId, userProfile);
        UserProfile user2 = new UserProfile("2","2",0,0);
        accountService.addSessions("session2",user2);
        int result = accountService.countSignIn();
        assertEquals(2,result);
    }

    @Test
    public void testCountSignUp() throws Exception {
        dbService.clearUserTable();
        accountService.addUser("1",userProfile);
        UserProfile user2 = new UserProfile("2","2",0,0);
        accountService.addUser("2", user2);
        int result = accountService.countSignUp();
        assertEquals(2,result);
    }

    @Test
    public void testGetUserBySession() throws Exception {
        accountService.addSessions("session", userProfile);
        UserProfile result = accountService.getUserBySession("session");
        assertEquals(userProfile,result);
        UserProfile result2 = accountService.getUserBySession("abcd");
        assertNull(result2);
    }
}