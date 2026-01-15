package de.thws.chat.boundary;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

// see https://quarkus.io/guides/websockets
@ServerEndpoint("/chat/{username}")
@ApplicationScoped
public class ChatSocket {

    @Inject
    @LogMessage
    Event<String> logServiceEvent;

    // works only on a single instance machine - for production use middleware
    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        broadcast("User " + username + " joined");

        sessions.put(username, session);

        logServiceEvent.fireAsync("User " + username + " connected with session id " + session.getId());
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessions.remove(username);
        broadcast("User " + username + " left");
        logServiceEvent.fireAsync("User " + username + " disconnected from session id " + session.getId());
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        sessions.remove(username);
        broadcast("User " + username + " left on error: " + throwable);
        logServiceEvent.fireAsync("User " + username + " left on error: " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
        broadcast(">> " + username + ": " + message);
        logServiceEvent.fireAsync("User " + username + " sent message: " + message);
    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    logServiceEvent.fireAsync("Unable to send message: " + result.getException());
                }
            });
        });
    }

}