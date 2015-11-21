package main;

import database.DBService;
import frontend.*;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jetbrains.annotations.NotNull;

import javax.servlet.Servlet;

import base.GameMechanics;
import base.WebSocketService;

import frontend.WebSocketGameServlet;
import frontend.WebSocketServiceImpl;
import mechanics.GameMechanicsImpl;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Main {

    public static final int DEF_PORT = 8080;

    public static void main(@NotNull String[] args) throws Exception {
        Integer port = getPort();
        if (port == null){
            port = DEF_PORT;
        }
        else if ((port < 1024) && (port > 49151)){
            System.out.append("Incorrect port").append('\n');
            return;
        }

        System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');

        DBService dbService = new DBService();

        AccountService accountService = new AccountService(dbService);

        Servlet signIn = new SignInServlet(accountService);
        Servlet signUp = new SignUpServlet(accountService);
        Servlet logout = new LogOutServlet(accountService);
        Servlet admin = new AdminPageServlet(accountService);
        Servlet check = new CheckingSessionServlet(accountService);
        Servlet scoreboard = new ScoreboardServlet(accountService);

        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService, dbService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(signIn), "/signin");
        context.addServlet(new ServletHolder(signUp), "/signup");
        context.addServlet(new ServletHolder(logout), "/logout");
        context.addServlet(new ServletHolder(admin), "/admin");
        context.addServlet(new ServletHolder(check), "/check");
        context.addServlet(new ServletHolder(scoreboard), "/scores");

        context.addServlet(new ServletHolder(new WebSocketGameServlet(accountService, gameMechanics, webSocketService)), "/gameplay");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        for  ( int i = 0; i < 10; i++) {
            String myStr = Integer.toString(i);
            accountService.addUser(myStr,new UserProfile(myStr,myStr,i,i));
        }


        Server server = new Server(port);
        server.setHandler(handlers);

        server.start();
        gameMechanics.run();
    }

    @Nullable
    private static Integer getPort() {
        try (final FileInputStream fis = new FileInputStream("cfg/server.cfg")) {
            final Properties properties = new Properties();
            properties.load(fis);
            return Integer.valueOf(properties.getProperty("port"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}