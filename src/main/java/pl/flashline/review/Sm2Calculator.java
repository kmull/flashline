package pl.flashline.review;

import org.springframework.stereotype.Component;

@Component
public class Sm2Calculator {

    /**
     * @param state              stan karty PRZED tą powtórką
     * @param quality            ocena użytkownika, 0-5
     * @param firstIntervalDays  z AlgorithmSettings (domyślnie 1)
     * @param secondIntervalDays z AlgorithmSettings (domyślnie 6)
     * @param minEaseFactor      z AlgorithmSettings (domyślnie 1.3)
     * @param requeueThreshold   z AlgorithmSettings (domyślnie 4) — próg,
     *                           PONIŻEJ którego karta dodatkowo wraca do
     *                           kolejki "Powtarzaj" w tej samej sesji.
     *                           NIE wpływa na to, czy kara się nalicza.
     */

    public ReviewResult calculate(CardState state, int quality,
                                  int firstIntervalDays, int secondIntervalDays,
                                  double minEaseFactor, int requeueThreshold) {

        if (quality < 0 || quality > 5) {
            throw new IllegalArgumentException("quality musi być w zakresie 0-5, było: " + quality);
        }

        int newRepetitions;
        int newIntervalDays;

        if (quality < 3) {
            // Realna kara za wyraźną pomyłkę: reset postępu do pierwszego
            // interwału, niezależnie od tego czy karta dodatkowo wróci
            // dziś jeszcze raz.
            newRepetitions = 0;
            newIntervalDays = firstIntervalDays;
        } else if (state.repetitions() == 0) {
            newRepetitions = state.repetitions() + 1;
            newIntervalDays = firstIntervalDays;
        } else if (state.repetitions() == 1) {
            newRepetitions = state.repetitions() + 1;
            newIntervalDays = secondIntervalDays;
        } else {
            newRepetitions = state.repetitions() + 1;
            newIntervalDays = (int) Math.round(state.intervalDays() * state.easeFactor());
        }

        // Ease factor przeliczany ZAWSZE, także przy złej odpowiedzi —
        // to jest właśnie mechanizm kary.
        double rawEaseFactor = state.easeFactor()
                + (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02));
        double newEaseFactor = Math.max(minEaseFactor, rawEaseFactor);

        boolean requeueToday = quality < requeueThreshold;

        return new ReviewResult(newEaseFactor, newIntervalDays, newRepetitions, requeueToday);
    }
}
