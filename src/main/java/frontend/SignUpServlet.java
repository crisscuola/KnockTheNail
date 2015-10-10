package frontend;

import org.json.JSONObject;
import freemarker.template.TemplateException;
import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class SignUpServlet extends HttpServlet {
    private AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();

        response.getWriter().println(PageGenerator.getPage("signup.html", pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

<<<<<<< HEAD
    @Override public void doPost(@NotNull HttpServletRequest request,
        @NotNull HttpServletResponse response) throws ServletException, IOException {
=======
    @Override
    public void doPost(@NotNull HttpServletRequest request,
                       @NotNull HttpServletResponse response) throws ServletException, IOException {
>>>>>>> d74c2d4f15e3f688815fad5f97f40fa567086edb
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        JSONObject responseJSON = new JSONObject();
        Map<String, Object> pageVariables = new HashMap<>();

        if (accountService.addUser(name, new UserProfile(name, password))) {
            //pageVariables.put("signUpStatus", "New user created");
            responseJSON.put("success", true);
            responseJSON.put("message", " successfully registered!");
            responseJSON.put("name", name);
        } else {
            responseJSON.put("success", false);
            responseJSON.put("name", name);
            responseJSON.put("message", "is already signed up");
        }
        response.getWriter().println(responseJSON.toString());
    }
}
