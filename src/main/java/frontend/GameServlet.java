package frontend;

import base.AuthService;
import base.GameMechanics;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameServlet extends HttpServlet {

    private GameMechanics gameMechanics;
    private AuthService authService;

    public GameServlet(GameMechanics gameMechanics, AuthService authService) {
        this.gameMechanics = gameMechanics;
        this.authService = authService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
    }




}
