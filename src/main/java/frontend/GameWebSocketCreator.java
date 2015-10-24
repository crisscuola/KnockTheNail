package frontend;

import  main.AccountService;
import base.GameMechanics;
import base.WebSocketService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;


public class GameWebSocketCreator implements WebSocketCreator {
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;
    private AccountService accountService;

    public GameWebSocketCreator( AccountService accountService,
                                GameMechanics gameMechanics,
                                WebSocketService webSocketService) {
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
        this.accountService = accountService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        System.out.println("resp: " + resp);
        String sessionId = req.getHttpServletRequest().getSession().getId();
        String name = accountService.getUserBySession(sessionId).getName();
        System.out.println("get Name: " + name);

        return new GameWebSocket(name, gameMechanics, webSocketService);
    }
}