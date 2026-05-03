package com.interview.assistant.service;

import com.interview.assistant.entity.SystemLog;
import com.interview.assistant.repository.SystemLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {
    private final SystemLogRepository systemLogRepository;

    public LogService(SystemLogRepository systemLogRepository) {
        this.systemLogRepository = systemLogRepository;
    }

    public void log(String actor, String targetUniqueId, String action) {
        SystemLog log = new SystemLog();
        log.setActor(actor);
        log.setTargetUniqueId(targetUniqueId);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        systemLogRepository.save(log);
    }

    public List<SystemLog> getRecentLogs() {
        return systemLogRepository.findTop100ByOrderByTimestampDesc();
    }
}
