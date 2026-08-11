package pl.flashline.review;

public record ReviewResult(
        double easeFactor,
        int intervalDays,
        int repetitions,
        boolean requeueToday
) {
}
