package frontend;

import main.AccountService;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LogOutServlet extends HttpServlet {
    private AccountService accountService;

    public LogOutServlet(AccountService accountService) {
        this.accountService = accountService;
    }


    @Override public void doPost(@NotNull HttpServletRequest request,
                                 @NotNull HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        JSONObject responseJSON = new JSONObject();

        String sessionCurrent = request.getSession().getId();

        if ((accountService.isSignedIn(sessionCurrent) != null)) {
            if (accountService.removeSession(sessionCurrent)) {
                responseJSON.put("success", true);
                responseJSON.put("name", name);
                responseJSON.put("message", " successfully logged out");
            } else {
                responseJSON.put("success", false);
                responseJSON.put("name", name);
                responseJSON.put("message", " hasn't been signed in before");
            }
        } else {
            responseJSON.put("success", false);
            responseJSON.put("name", name);
            responseJSON.put("message", " hasn't been signed in before");
        }
        response.getWriter().println(responseJSON.toString());
    }
}
