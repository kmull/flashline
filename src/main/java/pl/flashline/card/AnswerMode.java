package pl.flashline.card;

/**
 * Tylko SUGESTIA z importu (np. od AI) — realny wybór trybu odpowiadania
 * robi się w panelu sesji na froncie i może się różnić od tej wartości.
 * contentJson jest uniwersalny, niezależny od trybu (patrz sekcja 2
 * dokumentu założeń) — front renderuje te same dane inaczej w zależności
 * od wybranego trybu.
 */
public enum AnswerMode {
    GAP_FILL,
    FULL_REWRITE,
    ORAL,
    DICTATION
}
