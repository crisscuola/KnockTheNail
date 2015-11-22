package frontend;

import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CheckingSessionServlet extends HttpServlet {
    private AccountService accountService;

    public CheckingSessionServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override public void doPost(@NotNull HttpServletRequest request,
                                 @NotNull HttpServletResponse response) throws ServletException, IOException {

        JSONObject responseJSON = new JSONObject();

        String sessionCurrent = request.getSession().getId();
        UserProfile user = accountService.getUserBySession(sessionCurrent);

        if ((user != null)) {
            responseJSON.put("success", true);
            responseJSON.put("method", "check");
            responseJSON.put("id", user.getId());
            responseJSON.put("name", user.getName());
        } else {
            responseJSON.put("success", false);
            responseJSON.put("method", "check");
        }
        response.getWriter().println(responseJSON.toString());
    }

    @Override public void doGet(@NotNull HttpServletRequest request,
                                 @NotNull HttpServletResponse response) throws ServletException, IOException {

        JSONObject responseJSON = new JSONObject();

        String sessionCurrent = request.getSession().getId();
        UserProfile user = accountService.getUserBySession(sessionCurrent);

        if ((user != null)) {
            responseJSON.put("success", true);
            responseJSON.put("method", "check");

            responseJSON.put("name", user.getName());
        } else {
            responseJSON.put("success", false);
        }
        response.getWriter().println(responseJSON.toString());
    }

}
