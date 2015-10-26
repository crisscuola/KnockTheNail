package frontend;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import main.UserProfile;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.io.IOException;

@WebSocket
public class GameWebSocket {
    private UserProfile user;
    private Session session;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocket(@NotNull UserProfile user, GameMechanics gameMechanics, WebSocketService webSocketService) {
        this.user = user;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    public Integer getMyId() {
        return user.getId();
    }

    public void startGame(GameUser user) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "start");
            jsonStart.put("enemyName", user.getEnemyName());
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            System.out.print(e.toString());
        }
    }

    public void gameOver(GameUser user, boolean win) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "finish");
            jsonStart.put("win", win);
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        System.out.print("SocketMessage " + data);
        gameMechanics.incrementScore(user.getId());
    }

    @OnWebSocketConnect
    public void onOpen(Session s) {
        System.out.println("GameWebSocket Connect first");
        this.session = s;
        webSocketService.addUser(this);
        gameMechanics.addUser(user);
        System.out.println("GameWebSocket Connect second");
    }

    public void setMyScore(GameUser user) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        jsonStart.put("name", user.getMyName());
        jsonStart.put("score", user.getMyScore());
        try {
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            System.out.print(e.toString());
        }
    }

    public void setEnemyScore(GameUser user) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        jsonStart.put("name", user.getEnemyName());
        jsonStart.put("score", user.getEnemyScore());
        try {
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            System.out.print(e.toString());
        }
    }

    public void setCommonScore(GameUser user) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        jsonStart.put("button1", "10");
        jsonStart.put("button2", "20");
        jsonStart.put("button3", "50");
        jsonStart.put("score", user.getMyScore()+user.getEnemyScore());
        try {
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        //gameMechanics.removeUser(user.getId());
    }
}