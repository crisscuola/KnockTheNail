package frontend;

import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class LogOutServlet extends HttpServlet {
    private AccountService accountService;

    public LogOutServlet(AccountService accountService) {
        this.accountService = accountService;
    }


    @Override
    public void doGet(@NotNull HttpServletRequest request,
                      @NotNull HttpServletResponse response) throws ServletException, IOException {

        String sessionCurrent = request.getSession().getId();

        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<>();
        if (accountService.isSignedIn(sessionCurrent) != null) {
            accountService.removeSessions(sessionCurrent);
            pageVariables.put("isLogin", 0);
            pageVariables.put("loginStatus", "Loggout passed");
        } else {
            pageVariables.put("isLogin", 0);
            pageVariables.put("loginStatus", "error logout");
        }

        response.getWriter().println(PageGenerator.getPage("authstatus.html", pageVariables));

    }
}
