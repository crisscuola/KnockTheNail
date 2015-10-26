package main;

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


public class Main {

    public static final int DEF_PORT = 8080;

    public static void main(@NotNull String[] args) throws Exception {
        int port = DEF_PORT;
        if (args.length == 1) {
            String portString = args[0];
            if ((Integer.valueOf(portString) >= 1024) && (Integer.valueOf(portString) <= 49151))
                port = Integer.valueOf(portString);
            else {
                System.out.append("Incorrect port").append('\n');
            }
        }

        System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');

        AccountService accountService = new AccountService();

        Servlet signIn = new SignInServlet(accountService);
        Servlet signUp = new SignUpServlet(accountService);
        Servlet logout = new LogOutServlet(accountService);
        Servlet admin = new AdminPageServlet(accountService);
        Servlet check = new CheckingSessionServlet(accountService);

        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(signIn), "/signin");
        context.addServlet(new ServletHolder(signUp), "/signup");
        context.addServlet(new ServletHolder(logout), "/logout");
        context.addServlet(new ServletHolder(admin), "/admin");
        context.addServlet(new ServletHolder(check), "/check");

        context.addServlet(new ServletHolder(new WebSocketGameServlet(accountService, gameMechanics, webSocketService)), "/gameplay");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});


        Server server = new Server(port);
        server.setHandler(handlers);

        server.start();
        gameMechanics.run();
    }
}