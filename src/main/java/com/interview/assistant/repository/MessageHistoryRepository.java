package com.interview.assistant.repository;

import com.interview.assistant.entity.MessageHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageHistoryRepository extends JpaRepository<MessageHistory, Long> {
    List<MessageHistory> findTop10ByUniqueIdOrderByTimestampDesc(String uniqueId);
}
