package com.interview.assistant.service;

import com.interview.assistant.entity.User;
import com.interview.assistant.enums.UserStatus;
import com.interview.assistant.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    // Hinglish: uniqueId se active websocket session map maintain kar rahe hain.
    private final Map<String, String> receiverSessionMap = new ConcurrentHashMap<>();
    private final Map<String, String> sessionToUniqueIdMap = new ConcurrentHashMap<>();
    private final Set<String> activeSenders = ConcurrentHashMap.newKeySet();
    private final UserRepository userRepository;

    public SessionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void markReceiverOnline(String uniqueId, String sessionId) {
        receiverSessionMap.put(uniqueId, sessionId);
        sessionToUniqueIdMap.put(sessionId, uniqueId);
        userRepository.findByUniqueId(uniqueId).ifPresent(user -> {
            user.setStatus(UserStatus.ONLINE);
            userRepository.save(user);
        });
    }

    public void markSenderOnline(String username) {
        activeSenders.add(username);
    }

    public void markOfflineBySession(String sessionId) {
        String uniqueId = sessionToUniqueIdMap.remove(sessionId);
        if (uniqueId != null) {
            receiverSessionMap.remove(uniqueId);
            userRepository.findByUniqueId(uniqueId).ifPresent(user -> {
                user.setStatus(UserStatus.OFFLINE);
                userRepository.save(user);
            });
        }
    }

    public void markReceiverOffline(String uniqueId) {
        receiverSessionMap.remove(uniqueId);
        userRepository.findByUniqueId(uniqueId).ifPresent(user -> {
            user.setStatus(UserStatus.OFFLINE);
            userRepository.save(user);
        });
    }

    public void markSenderOffline(String username) {
        activeSenders.remove(username);
    }

    public boolean isReceiverOnline(String uniqueId) {
        return receiverSessionMap.containsKey(uniqueId);
    }

    public Map<String, String> getReceiverSessionMap() {
        return receiverSessionMap;
    }

    public Set<String> getActiveSenders() {
        return activeSenders;
    }
}
