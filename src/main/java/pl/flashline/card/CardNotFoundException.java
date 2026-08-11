package pl.flashline.card;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(Long id) {
        super("Nie znaleziono karty o id: " + id);
    }
}
