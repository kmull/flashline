package pl.flashline.card;

import java.util.List;

public record ReadingComprehensionContent(
        String passage,
        List<ComprehensionQuestion> questions
) implements CardContent {
}
