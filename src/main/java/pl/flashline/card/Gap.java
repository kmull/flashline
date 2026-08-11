package pl.flashline.card;

import java.util.List;

/**
 * Pojedyncza luka w zdaniu. acceptedAnswers to LISTA (nie pojedynczy
 * string) — inspirowane realnym formatem SuperMemo, gdzie jeden gap może
 * mieć kilka poprawnych wariantów gramatycznych naraz (np. "couldn't" i
 * "could not"). options (opcjonalne) włącza tryb radio button zamiast
 * pola tekstowego. hint (opcjonalne) to podpowiedź, np. pierwsza litera.
 */

public record Gap(
        int id,
        List<String> acceptedAnswers,
        List<String> options,
        String hint
) {
}
