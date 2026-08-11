package pl.flashline.review;

import java.time.LocalDate;

public record ReviewResponse(
        LocalDate nextReviewDate,
        int intervalDays,
        boolean requeueToday
) {
}
