package com.interview.assistant.service;

import com.interview.assistant.dto.AnswerMessage;
import com.interview.assistant.entity.MessageHistory;
import com.interview.assistant.repository.MessageHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HistoryService {

    // Hinglish: fast UI response ke liye recent 10 answers memory cache me rakhte hain.
    private final Map<String, Deque<AnswerMessage>> recentCache = new ConcurrentHashMap<>();
    private final MessageHistoryRepository messageHistoryRepository;

    public HistoryService(MessageHistoryRepository messageHistoryRepository) {
        this.messageHistoryRepository = messageHistoryRepository;
    }

    public void save(String uniqueId, String question, String answer, String source) {
        MessageHistory history = new MessageHistory();
        history.setUniqueId(uniqueId);
        history.setQuestion(question);
        history.setAnswer(answer);
        history.setTimestamp(LocalDateTime.now());
        messageHistoryRepository.save(history);

        AnswerMessage msg = new AnswerMessage();
        msg.setUniqueId(uniqueId);
        msg.setQuestion(question);
        msg.setAnswer(answer);
        msg.setSource(source);
        msg.setTimestamp(history.getTimestamp());

        recentCache.computeIfAbsent(uniqueId, k -> new ArrayDeque<>()).addFirst(msg);
        while (recentCache.get(uniqueId).size() > 10) {
            recentCache.get(uniqueId).removeLast();
        }
    }

    public List<AnswerMessage> getRecent(String uniqueId) {
        Deque<AnswerMessage> cache = recentCache.get(uniqueId);
        if (cache != null && !cache.isEmpty()) {
            return new ArrayList<>(cache);
        }

        List<MessageHistory> fromDb = messageHistoryRepository.findTop10ByUniqueIdOrderByTimestampDesc(uniqueId);
        List<AnswerMessage> converted = new ArrayList<>();
        for (MessageHistory item : fromDb) {
            AnswerMessage msg = new AnswerMessage();
            msg.setUniqueId(item.getUniqueId());
            msg.setQuestion(item.getQuestion());
            msg.setAnswer(item.getAnswer());
            msg.setSource("HISTORY");
            msg.setTimestamp(item.getTimestamp());
            converted.add(msg);
        }
        return converted;
    }
}
