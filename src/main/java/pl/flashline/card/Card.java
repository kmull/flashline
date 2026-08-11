package pl.flashline.card;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Entity
@Table(name = "card")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long deckId;

    private Long sourceTextId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType type;

    @Enumerated(EnumType.STRING)
    private AnswerMode suggestedMode;

    private String category;

    @Column(nullable = false)
    private String question;

    // Surowy JSON w bazie — konwersja do/z CardContent przez
    // CardContentSerializer, nigdy ręcznie w kodzie biznesowym.
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(nullable = false)
    private String contentJson;

    private String audioUrl;

    @Enumerated(EnumType.STRING)
    private CefrLevel level;

    // --- pola algorytmu SM-2, per karta ---
    @Column(nullable = false)
    private double easeFactor;

    @Column(nullable = false)
    private int intervalDays;

    @Column(nullable = false)
    private int repetitions = 0;

    @Column(nullable = false)
    private LocalDate nextReviewDate;

    /**
     * @Builder zamiast prostego konstruktora — Card ma sporo pól,
     * z czego kilka opcjonalnych (sourceTextId, suggestedMode, category,
     * audioUrl, level). Świadomie WYKLUCZAMY "id" z buildera (toBuilder
     * nie jest tu potrzebny, a zwykły @Builder na klasie z polem "id"
     * pozwoliłby komuś je ręcznie ustawić — to samo ryzyko, o którym
     * mówiliśmy przy Deck i @AllArgsConstructor).
     */
    @Builder
    private Card(Long deckId, Long sourceTextId, CardType type, AnswerMode suggestedMode,
                 String category, String question, String contentJson, String audioUrl,
                 CefrLevel level, double initialEaseFactor) {
        this.deckId = deckId;
        this.sourceTextId = sourceTextId;
        this.type = type;
        this.suggestedMode = suggestedMode;
        this.category = category;
        this.question = question;
        this.contentJson = contentJson;
        this.audioUrl = audioUrl;
        this.level = level;
        this.easeFactor = initialEaseFactor;
        this.intervalDays = 0;
        this.repetitions = 0;
        this.nextReviewDate = LocalDate.now();
    }

    public void applyReviewResult(double newEaseFactor, int newIntervalDays,
                                  int newRepetitions, LocalDate newNextReviewDate) {
        this.easeFactor = newEaseFactor;
        this.intervalDays = newIntervalDays;
        this.repetitions = newRepetitions;
        this.nextReviewDate = newNextReviewDate;
    }
}
