package com.interview.assistant.repository;

import com.interview.assistant.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "SELECT * FROM questions WHERE keyword ILIKE CONCAT('%', ?1, '%') LIMIT 1", nativeQuery = true)
    Optional<Question> findFirstByKeywordLike(String normalizedKeyword);
}
