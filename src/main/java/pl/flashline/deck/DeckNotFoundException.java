package pl.flashline.deck;

public class DeckNotFoundException extends RuntimeException {

    public DeckNotFoundException(Long id) {
        super("Nie znaleziono talii o id: " + id);
    }
}
