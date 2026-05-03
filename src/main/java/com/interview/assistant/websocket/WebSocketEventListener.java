package com.interview.assistant.websocket;

import com.interview.assistant.service.SessionService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private final SessionService sessionService;

    public WebSocketEventListener(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        if (accessor.getUser() != null && accessor.getSessionId() != null) {
            String principalName = accessor.getUser().getName();
            // Hinglish: receiver ka principal uniqueId hai to usko online mark kar dete hain.
            if (!principalName.startsWith("anonymous-")) {
                sessionService.markReceiverOnline(principalName, accessor.getSessionId());
            }
        }
    }

    @EventListener
    public void handleSessionDisconnected(SessionDisconnectEvent event) {
        sessionService.markOfflineBySession(event.getSessionId());
    }
}
