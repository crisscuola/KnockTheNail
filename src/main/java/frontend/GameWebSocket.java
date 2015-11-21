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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import mechanics.GameSession;

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

    public long getMyId() {
        return user.getId();
    }

    public void startGame(GameUser user, boolean shouldClick) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "start");
            jsonStart.put("enemyName", user.getEnemyName());
            jsonStart.put("shouldClick", shouldClick);
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

    public void disconnect(GameUser user) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "disconnect");
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) {

        JSONParser parser = new JSONParser();

        Object obj = null;
        try {
            obj = parser.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObj = (JSONObject) obj;


        //JSONObject jo = JSONObject.get("data");
        System.out.println(jsonObj.get("force"));

        String x = String.valueOf(jsonObj.get("force"));

        int force = Integer.valueOf(x);


        System.out.print("SocketMessage " + data);
        System.out.print("Force " + force);
        gameMechanics.incrementScore(user.getId(), force);

    }

    @OnWebSocketConnect
    public void onOpen(Session s) {
        System.out.println("GameWebSocket Connect first");
        this.session = s;
        webSocketService.addUser(this);
        gameMechanics.notifyUserConnected(user);
        System.out.println("GameWebSocket Connect second");
    }

    public void setMyScore(GameUser user) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment_myscore");
        jsonStart.put("name", user.getMyName());
        jsonStart.put("score", user.getMyScore());
        jsonStart.put("shouldClick", user.getShouldClick());
        try {
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            System.out.print(e.toString());
        }
    }

    public void setEnemyScore(GameUser user) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment_enemyscore");
        jsonStart.put("name", user.getEnemyName());
        jsonStart.put("score", user.getEnemyScore());
        jsonStart.put("shouldClick", !user.getShouldClick());
        try {
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            System.out.print(e.toString());
        }
    }

    public void setCommonScore(GameUser user) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        jsonStart.put("button1", 5 - (1/user.getFrictionRate()));
        jsonStart.put("button2", 10 - (1/user.getFrictionRate()));
        jsonStart.put("button3", 20 - (1/user.getFrictionRate()));
        jsonStart.put("commonScore", user.getMyScore()+user.getEnemyScore());
        jsonStart.put("frictionRate", user.getFrictionRate());

        try {
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            System.out.print(e.toString());
        }
    }

//    public void setFrictionRate(GameUser user) {
//        JSONObject jsonStart = new JSONObject();
//        jsonStart.put("status", "increment");
//
//        jsonStart.put("frictionRate", user.getFrictionRate());
//        try {
//            session.getRemote().sendString(jsonStart.toJSONString());
//        } catch (IOException e) {
//            System.out.print(e.toString());
//        }
//
//    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        gameMechanics.notifyUserDisconnected(user);
        System.out.println("Closed by " + user.getName());

    }
}