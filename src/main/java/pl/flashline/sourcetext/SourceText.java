package pl.flashline.sourcetext;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import pl.flashline.card.CefrLevel;

@Entity
@Table(name = "source_text")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SourceText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long deckId;

    @Column(nullable = false)
    private String title;

    private String category;

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private CefrLevel level;

    private String audioUrl;

    @Column(nullable = false)
    private boolean introShown = false;

    @Builder
    private SourceText(Long deckId, String title, String category, String content,
                       CefrLevel level, String audioUrl) {
        this.deckId = deckId;
        this.title = title;
        this.category = category;
        this.content = content;
        this.level = level;
        this.audioUrl = audioUrl;
        this.introShown = introShown;
    }

    /**
     * Wywoływane po pierwszym przejściu przez krok 2 (tekst z podświetleniem).
     */
    public void markIntroShown() {
        this.introShown = true;
    }
}
