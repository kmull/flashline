package pl.flashline.card;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Sealed interface (Java 17+) — zamyka listę możliwych implementacji do
 * tych czterech, wypisanych jawnie w "permits". Kompilator wymusza
 * wyczerpujące dopasowanie w switch (bez "default") wszędzie tam, gdzie
 * przetwarzamy CardContent — jeśli kiedyś dodasz piąty wariant, każdy
 * taki switch przestanie się kompilować, dopóki go nie obsłużysz.
 * <p>
 * Cztery warianty odpowiadają czterem strukturalnie różnym kształtom
 * danych (nie każdemu CardType z osobna — RECALL/MULTIPLE_CHOICE/
 * TRANSLATE dzielą tę samą strukturę gapów, więc mapują się wszystkie
 * na GapBasedContent).
 */

/**
 * @JsonTypeInfo mówi Jacksonowi: "przy zapisie dołóż pole tekstowe z nazwą
 * konkretnego typu, przy odczycie użyj tego pola, żeby wiedzieć którego
 * rekordu użyć". Bez tego Jackson widziałby tylko "jakiś JSON z polami
 * fullText/gapText/..." i nie wiedziałby, czy zbudować GapBasedContent
 * czy któryś z pozostałych trzech wariantów.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GapBasedContent.class, name = "GAP_BASED"),
        @JsonSubTypes.Type(value = DragOrderContent.class, name = "DRAG_ORDER"),
        @JsonSubTypes.Type(value = FindInTextContent.class, name = "FIND_IN_TEXT"),
        @JsonSubTypes.Type(value = ReadingComprehensionContent.class, name = "READING_COMPREHENSION"),
})
public sealed interface CardContent
        permits GapBasedContent, DragOrderContent, FindInTextContent, ReadingComprehensionContent {
}
