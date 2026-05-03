package com.interview.assistant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "performance_metrics", indexes = {
    @Index(name = "idx_performance_unique_id", columnList = "unique_id")
})
public class PerformanceMetrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unique_id", nullable = false)
    private String uniqueId;

    @Column(name = "total_questions_answered")
    private Integer totalQuestionsAnswered = 0;

    @Column(name = "average_response_time_ms")
    private Integer averageResponseTimeMs = 0;

    @Column(name = "average_rating")
    private BigDecimal averageRating;

    @Column(name = "accuracy_percentage")
    private BigDecimal accuracyPercentage;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    public void setDefaults() {
        if (lastUpdated == null) {
            lastUpdated = LocalDateTime.now();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUniqueId() { return uniqueId; }
    public void setUniqueId(String uniqueId) { this.uniqueId = uniqueId; }

    public Integer getTotalQuestionsAnswered() { return totalQuestionsAnswered; }
    public void setTotalQuestionsAnswered(Integer totalQuestionsAnswered) { this.totalQuestionsAnswered = totalQuestionsAnswered; }

    public Integer getAverageResponseTimeMs() { return averageResponseTimeMs; }
    public void setAverageResponseTimeMs(Integer averageResponseTimeMs) { this.averageResponseTimeMs = averageResponseTimeMs; }

    public BigDecimal getAverageRating() { return averageRating; }
    public void setAverageRating(BigDecimal averageRating) { this.averageRating = averageRating; }

    public BigDecimal getAccuracyPercentage() { return accuracyPercentage; }
    public void setAccuracyPercentage(BigDecimal accuracyPercentage) { this.accuracyPercentage = accuracyPercentage; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}
