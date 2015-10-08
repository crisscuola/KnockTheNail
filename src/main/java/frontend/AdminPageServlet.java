package frontend;

import example.TimeHelper;
import main.AccountService;
import org.jetbrains.annotations.NotNull;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminPageServlet extends HttpServlet {
    private AccountService accountService;

    public AdminPageServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(@NotNull HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        if (String.valueOf(password).equals("admin")) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);

            Map<String, Object> pageVariables = new HashMap<>();

            String timeString = request.getParameter("shutdown");
            if (timeString != null) {
                int timeMS = Integer.valueOf(timeString);
                System.out.print("Server will be down after: " + timeMS + " ms");
                TimeHelper.sleep(timeMS);
                System.out.print("\nShutdown");
                System.exit(0);
            }

            int countLogin = accountService.countSignIn();
            int countSignUp = accountService.countSignUp();

            pageVariables.put("status", "run");
            pageVariables.put("countLogIn", countLogin);
            pageVariables.put("countSignUp", countSignUp);

            response.getWriter().println(PageGenerator.getPage("admin.html", pageVariables));
        } else {
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("loginStatus", "error admin log");
            response.getWriter().print(PageGenerator.getPage("adminlogin.html", pageVariables));
        }
    }

}
