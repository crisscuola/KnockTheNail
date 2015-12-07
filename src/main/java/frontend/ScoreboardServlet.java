package frontend;

import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;



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
            userProfiles = accountService.getUsersScoreBoard(Integer.valueOf(limit));
        } else {
            userProfiles = accountService.getUsersScoreBoard();
        }
        for (UserProfile user: userProfiles) {
            JSONObject responseJSON = new JSONObject();

            responseJSON.put("name", user.getName());
            responseJSON.put("wons", user.getWin());
            responseJSON.put("loses", user.getLose());
            responseJSON.put("id", user.getId());

            users.put(responseJSON);
        }

        response.getWriter().println(users);

    }


    @Override public void doPost(@NotNull HttpServletRequest request,
                                 @NotNull HttpServletResponse response) throws ServletException, IOException {

        JSONObject responseJSON = new JSONObject();
        responseJSON.put("status", "OK");
        responseJSON.put("method", "scores");

        response.getWriter().println(responseJSON.toString());
    }

    @Override public void doPut(@NotNull HttpServletRequest request,
                                 @NotNull HttpServletResponse response) throws ServletException, IOException {


        JSONObject responseJSON = new JSONObject();
        System.out.println(request.getParameter("id") + ' ' + request.getParameter("name"));
        System.out.println("PUT /SCORES");
        responseJSON.put("status", "OK");

        response.getWriter().println(responseJSON.toString());
    }
}
