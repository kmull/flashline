package pl.flashline.deck;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class AlgorithmSettings {

    public static final int DEFAULT_FIRST_INTERVAL_DAYS = 1;
    public static final int DEFAULT_SECOND_INTERVAL_DAYS = 6;
    public static final double DEFAULT_INITIAL_EASE_FACTOR = 2.5;
    public static final double DEFAULT_MIN_EASE_FACTOR = 1.3;
    public static final int DEFAULT_REQUEUE_THRESHOLD = 4;

    @Column(name = "first_interval_days", nullable = false)
    private int firstIntervalDays = DEFAULT_FIRST_INTERVAL_DAYS;

    @Column(name = "second_interval_days", nullable = false)
    private int secondIntervalDays = DEFAULT_SECOND_INTERVAL_DAYS;

    @Column(name = "initial_ease_factor", nullable = false)
    private double initialEaseFactor = DEFAULT_INITIAL_EASE_FACTOR;

    @Column(name = "min_ease_factor", nullable = false)
    private double minEaseFactor = DEFAULT_MIN_EASE_FACTOR;

    @Column(name = "good_quality_threshold", nullable = false)
    private int requeueThreshold = DEFAULT_REQUEUE_THRESHOLD;

    public static AlgorithmSettings defaults() {
        return new AlgorithmSettings();
    }

    public void resetToDefaults() {
        this.firstIntervalDays = DEFAULT_FIRST_INTERVAL_DAYS;
        this.secondIntervalDays = DEFAULT_SECOND_INTERVAL_DAYS;
        this.initialEaseFactor = DEFAULT_INITIAL_EASE_FACTOR;
        this.minEaseFactor = DEFAULT_MIN_EASE_FACTOR;
        this.requeueThreshold = DEFAULT_REQUEUE_THRESHOLD;
    }

    public void update(int firstIntervalDays, int secondIntervalDays,
                       double initialEaseFactor, double minEaseFactor,
                       int requeueThreshold) {
        this.firstIntervalDays = firstIntervalDays;
        this.secondIntervalDays = secondIntervalDays;
        this.initialEaseFactor = initialEaseFactor;
        this.minEaseFactor = minEaseFactor;
        this.requeueThreshold = requeueThreshold;
    }

}
