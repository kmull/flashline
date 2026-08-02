package pl.flashline.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("Zwykły użytkownik — widzi i edytuje tylko swoje talie"),
    ADMIN("Administrator — pełny dostęp do wszystkich danych");

    private final String description;
}
