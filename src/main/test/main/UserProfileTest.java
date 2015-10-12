package main;

import org.junit.Test;

import static org.junit.Assert.*;


public class UserProfileTest {
    private UserProfile userProfile = new UserProfile("1","1");

    @Test
    public void testGetName() throws Exception {
        String result = userProfile.getName();
        assertEquals(result,"1");
    }

    @Test
    public void testGetPassword() throws Exception {
        String result = userProfile.getPassword();
        assertEquals(result,"1");
    }
}