package pl.flashline.review;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Sm2CalculatorTest {

    private final Sm2Calculator calculator = new Sm2Calculator();

    private static final int FIRST_INTERVAL = 1;
    private static final int SECOND_INTERVAL = 6;
    private static final double MIN_EASE = 1.3;
    private static final int REQUEUE_THRESHOLD = 4;

    @Test
    void pierwsza_powtorka_z_dobra_ocena_daje_pierwszy_interwal() {
        CardState state = CardState.initial(2.5);

        ReviewResult result = calculator.calculate(
                state, 4, FIRST_INTERVAL, SECOND_INTERVAL, MIN_EASE, REQUEUE_THRESHOLD);

        assertThat(result.requeueToday()).isFalse();
        assertThat(result.intervalDays()).isEqualTo(FIRST_INTERVAL);
        assertThat(result.repetitions()).isEqualTo(1);
    }

    @Test
    void druga_powtorka_z_dobra_ocena_daje_drugi_interwal() {
        CardState state = new CardState(2.5, FIRST_INTERVAL, 1);

        ReviewResult result = calculator.calculate(
                state, 4, FIRST_INTERVAL, SECOND_INTERVAL, MIN_EASE, REQUEUE_THRESHOLD);

        assertThat(result.intervalDays()).isEqualTo(SECOND_INTERVAL);
        assertThat(result.repetitions()).isEqualTo(2);
    }

    @Test
    void trzecia_powtorka_mnozy_poprzedni_interwal_przez_ease_factor() {
        CardState state = new CardState(2.5, SECOND_INTERVAL, 2);

        ReviewResult result = calculator.calculate(
                state, 4, FIRST_INTERVAL, SECOND_INTERVAL, MIN_EASE, REQUEUE_THRESHOLD);

        assertThat(result.intervalDays()).isEqualTo(15); // 6 * 2.5
        assertThat(result.repetitions()).isEqualTo(3);
    }

    @Test
    void ocena_ponizej_progu_wymaga_requeue_ale_i_tak_liczy_kare() {
        CardState state = new CardState(2.5, SECOND_INTERVAL, 2);

        ReviewResult result = calculator.calculate(
                state, 3, FIRST_INTERVAL, SECOND_INTERVAL, MIN_EASE, REQUEUE_THRESHOLD);

        assertThat(result.requeueToday()).isTrue();
        assertThat(result.repetitions()).isEqualTo(3);
        assertThat(result.easeFactor()).isLessThan(2.5);
    }

    @Test
    void wyrazna_pomylka_zawsze_resetuje_powtorzenia_niezaleznie_od_progu_requeue() {
        CardState state = new CardState(2.8, 40, 5);

        ReviewResult result = calculator.calculate(
                state, 1, FIRST_INTERVAL, SECOND_INTERVAL, MIN_EASE, 0);

        assertThat(result.repetitions()).isEqualTo(0);
        assertThat(result.intervalDays()).isEqualTo(FIRST_INTERVAL);
        assertThat(result.easeFactor()).isLessThan(state.easeFactor());
        assertThat(result.requeueToday()).isFalse();
    }

    @Test
    void ease_factor_nie_spada_ponizej_minimum() {
        CardState state = new CardState(1.31, 10, 3);

        ReviewResult result = calculator.calculate(
                state, 3, FIRST_INTERVAL, SECOND_INTERVAL, MIN_EASE, REQUEUE_THRESHOLD);

        assertThat(result.easeFactor()).isGreaterThanOrEqualTo(MIN_EASE);
    }

    @Test
    void ocena_najwyzsza_podnosi_ease_factor() {
        CardState state = new CardState(2.5, 6, 2);

        ReviewResult result = calculator.calculate(
                state, 5, FIRST_INTERVAL, SECOND_INTERVAL, MIN_EASE, REQUEUE_THRESHOLD);

        assertThat(result.easeFactor()).isGreaterThan(2.5);
        assertThat(result.requeueToday()).isFalse();
    }

    @Test
    void niepoprawna_ocena_rzuca_wyjatek() {
        CardState state = CardState.initial(2.5);

        assertThatThrownBy(() ->
                calculator.calculate(state, 7, FIRST_INTERVAL, SECOND_INTERVAL, MIN_EASE, REQUEUE_THRESHOLD)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}