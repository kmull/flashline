package pl.flashline.card;

import java.util.List;

/**
 * Token-based (nie character-span) — cały tekst z góry podzielony na
 * słowa, trzeba wskazać jeden konkretny token. Prostsze i mniej podatne
 * na błędy niż zaznaczanie dowolnego zakresu znaków myszką.
 */
public record FindInTextContent(
        String fullText,
        List<String> tokens,
        int correctTokenIndex
) implements CardContent {
}
