package com.interview.assistant.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        List<String> uid = UriComponentsBuilder.fromUri(request.getURI()).build().getQueryParams().get("uid");
        String principalName = (uid != null && !uid.isEmpty()) ? uid.get(0) : "anonymous-" + System.currentTimeMillis();
        return () -> principalName;
    }
}
