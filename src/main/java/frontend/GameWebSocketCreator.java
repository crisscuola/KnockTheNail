package frontend;

import  main.AccountService;
import base.GameMechanics;
import base.WebSocketService;
import main.UserProfile;
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
        String sessionId = req.getHttpServletRequest().getSession().getId();
        System.out.println(sessionId);
        UserProfile player = accountService.getUserBySession(String.valueOf(sessionId));
//        if(player == null){
//            player = accountService.createRandomUser();
//            accountService.addSessions(sessionId,player);
//        }
        return new GameWebSocket(player, gameMechanics, webSocketService);
    }
}