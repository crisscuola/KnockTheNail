package frontend;

import database.DBService;
import database.DataBaseSettings;
import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Сергей on 07.12.2015.
 */
public class SignInServletTest {
    @NotNull
    private final DBService dbService = new DBService(new DataBaseSettings("jdbc:mysql","localhost","db_knock_test","test","test",3306));
    @NotNull
    private final AccountService accountService = new AccountService(dbService);
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Test
    public void testDoPostSuccess() throws IOException{
        HttpSession session = mock(HttpSession.class);
        when(session.getId()).thenReturn("123");

        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("password")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("1");

        HttpServletResponse response = mock(HttpServletResponse.class);

        PrintWriter printWriter = new PrintWriter(outContent,true);
        when(response.getWriter()).thenReturn(printWriter);

        dbService.clearUserTable();

        UserProfile userProfile = new UserProfile("1","1",0,0);
        accountService.addUser("1",userProfile);

        SignInServlet signInServlet = new SignInServlet(accountService);

        try {
            signInServlet.doPost(request, response);
        } catch (ServletException | IOException e){
            e.printStackTrace();
        }

        JSONObject responseJSON = new JSONObject();
        responseJSON.put("success", true);
        responseJSON.put("method", "signin");
        responseJSON.put("message", " successfully logged in!");
        responseJSON.put("name", "1");
        responseJSON.put("id", 1);

        assertTrue(accountService.isSignedIn("123") != null);
        assertEquals(outContent.toString(),responseJSON.toString()+ "\r\n");
    }

    @Test
    public void testDoPostFail() throws IOException{
        HttpSession session = mock(HttpSession.class);
        when(session.getId()).thenReturn("123");

        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("password")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("0");

        HttpServletResponse response = mock(HttpServletResponse.class);

        PrintWriter printWriter = new PrintWriter(outContent,true);
        when(response.getWriter()).thenReturn(printWriter);

        dbService.clearUserTable();

        UserProfile userProfile = new UserProfile("1","1",0,0);
        accountService.addUser("1",userProfile);

        SignInServlet signInServlet = new SignInServlet(accountService);

        try {
            signInServlet.doPost(request, response);
        } catch (ServletException | IOException e){
            e.printStackTrace();
        }

        JSONObject responseJSON = new JSONObject();
        responseJSON.put("method", "signin");
        responseJSON.put("success", false);
        responseJSON.put("message", " login failed");

        assertTrue(accountService.isSignedIn("123") == null);
        assertEquals(outContent.toString(),responseJSON.toString()+ "\r\n");
    }


}
