package pl.flashline.deck;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeckMapper {

    DeckResponse toResponse(Deck deck);
}
