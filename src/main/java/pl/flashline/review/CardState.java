package pl.flashline.review;

public record CardState(
        double easeFactor,
        int intervalDays,
        int repetitions
) {

    public static CardState initial(double initialEaseFactor) {
        return new CardState(initialEaseFactor, 0, 0);
    }
}
