package frontend;


//import com.sun.deploy.net.HttpRequest;
import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ScoreboardServlet extends HttpServlet{
    private AccountService accountService;

    public ScoreboardServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {


        JSONArray users = new JSONArray();
        String limit = request.getParameter("limit");
        ArrayList<UserProfile> userProfiles;
        if(limit != null) {
            System.out.println("Nigga");
            userProfiles = accountService.getUsersScoreBoard(Integer.valueOf(limit));
        } else {
            userProfiles = accountService.getUsersScoreBoard();
        }
        for (UserProfile user: userProfiles) {
            JSONObject responseJSON = new JSONObject();

            responseJSON.put("name", user.getName());
            responseJSON.put("win", user.getWin());
            responseJSON.put("lose", user.getLose());
            responseJSON.put("id", user.getId());

            users.put(responseJSON);
        }

        response.getWriter().println(users);

    }


    @Override public void doPost(@NotNull HttpServletRequest request,
                                 @NotNull HttpServletResponse response) throws ServletException, IOException {

        //String name = request.getParameter("name");
        //String password = request.getParameter("password");
        JSONObject responseJSON = new JSONObject();
        System.out.println(request.getParameter("name"));
        System.out.println("POST /SCORES");
        responseJSON.put("status", "OK");

//        if (accountService.addUser(name, new UserProfile(name, password,1,1))) {
//            responseJSON.put("success", true);
//            responseJSON.put("message", " successfully registered!");
//            responseJSON.put("name", name);
//        } else {
//            responseJSON.put("success", false);
//            responseJSON.put("name", name);
//            responseJSON.put("message", "is already signed up");
//        }
        response.getWriter().println(responseJSON.toString());
    }

    @Override public void doPut(@NotNull HttpServletRequest request,
                                 @NotNull HttpServletResponse response) throws ServletException, IOException {

        //String name = request.getParameter("name");
        //String password = request.getParameter("password");
        JSONObject responseJSON = new JSONObject();
        System.out.println(request.getParameter("id") + " " + request.getParameter("name"));
        System.out.println("PUT /SCORES");
        responseJSON.put("status", "OK");

//        if (accountService.addUser(name, new UserProfile(name, password,1,1))) {
//            responseJSON.put("success", true);
//            responseJSON.put("message", " successfully registered!");
//            responseJSON.put("name", name);
//        } else {
//            responseJSON.put("success", false);
//            responseJSON.put("name", name);
//            responseJSON.put("message", "is already signed up");
//        }
        response.getWriter().println(responseJSON.toString());
    }
}
