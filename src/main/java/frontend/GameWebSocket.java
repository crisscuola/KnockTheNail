package frontend;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONObject;

@WebSocket
public class GameWebSocket {
    private String myName;
    private Session session;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocket(String myName, GameMechanics gameMechanics, WebSocketService webSocketService) {
        this.myName = myName;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
        this.session = session;
    }

    public String getMyName() {
        return myName;
    }

    public void startGame(GameUser user) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "start");
            jsonStart.put("enemyName", user.getEnemyName());
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void gameOver(GameUser user, boolean win) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "finish");
            jsonStart.put("win", win);
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
//        JSONParser jsonParser = new JSONParser();
//        Object object = jsonParser.parse(data);
//        JSONObject jsonForce = new JSONObject();
//        jsonForce = (JSONObject) object;

        System.out.print("SocketMessage " + data);

        gameMechanics.incrementScore(myName);
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        System.out.println("GameWebSocket Connect first");
        setSession(session);
        webSocketService.addUser(this);
        gameMechanics.addUser(myName);
        System.out.println("GameWebSocket Connect second");
    }

    public void setMyScore(GameUser user) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        jsonStart.put("name", myName);
        jsonStart.put("score", user.getMyScore());
        try {
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (Exception e) {
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
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }


    public void setCommonScore(GameUser user) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        jsonStart.put("button1", "10");
        jsonStart.put("button2", "20");
        jsonStart.put("button3", "50");
        // jsonStart.put("name", myName);
        jsonStart.put("score", user.getCommonScore());
        try {
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {

    }
}