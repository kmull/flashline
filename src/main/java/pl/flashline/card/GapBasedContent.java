package pl.flashline.card;

import java.util.List;

/**
 * Pokrywa RECALL, MULTIPLE_CHOICE, TRANSLATE i tryby Luka/Cały tekst/
 * Ustnie/Dyktando — wszystkie dzielą tę samą strukturę: pełny tekst,
 * opcjonalne luki, opcjonalne warianty całej odpowiedzi.
 */
public record GapBasedContent(
        String fullText,
        String gapText,
        List<String> acceptedFullAnswers,
        List<Gap> gaps
) implements CardContent {
}
