package pl.flashline.card;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CardContentSerializer.class)
public interface CardMapper {

    /**
     * @Mapping z "expression" — MapStruct nie potrafi automatycznie
     * dopasować pola "content" (typu CardContent) do pola encji
     * "contentJson" (typu String) — nazwy I typy się różnią. Musisz
     * jawnie powiedzieć, JAK to przeliczyć: przez wstrzykniętego
     * CardContentSerializer (stąd "uses = CardContentSerializer.class"
     * w @Mapper). To jest dokładnie ten przypadek "MapStruct nie ogarnie
     * wszystkiego samo", o którym mówiliśmy przy RegisterRequest — pola
     * o różnych kształtach zawsze wymagają jawnej wskazówki.
     */

//    @Mapping(target = "content", expression = "java(cardContentSerializer.fromJson(card.getContentJson()))")
    @Mapping(source = "contentJson", target = "content")
    CardResponse toResponse(Card card);
}
