package pl.flashline.card;

/**
 * Format pytania — NIEZALEŻNY od trybu odpowiadania (patrz AnswerMode).
 * RECALL, MULTIPLE_CHOICE i TRANSLATE dzielą tę samą strukturę danych
 * (GapBasedContent) — to pole mówi tylko JAK pytanie jest zbudowane,
 * nie jak wygląda struktura JSON pod spodem.
 */
public enum CardType {
    RECALL,
    MULTIPLE_CHOICE,
    TRANSLATE,
    DRAG_ORDER,
    FIND_IN_TEXT,
    READING_COMPREHENSION
}
