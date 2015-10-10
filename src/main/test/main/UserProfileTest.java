package main;

import org.junit.Test;

import static org.junit.Assert.*;


public class UserProfileTest {
    private String name;
    private String password;
    private UserProfile userProfile;
    private AccountService accountService;

    @Test
    public void testGetName() throws Exception {
        userProfile = new UserProfile("name", "password");
        String result = userProfile.getName();
        assertEquals(result,"name");
    }

    @Test
    public void testGetPassword() throws Exception {
        userProfile = new UserProfile("name", "password");
        String result = userProfile.getPassword();
        assertEquals(result,"password");
    }
}