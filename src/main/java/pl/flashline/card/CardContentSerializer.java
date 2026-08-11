package pl.flashline.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

/**
 * Most między kolumną TEXT w bazie (String) a typowanym CardContent
 * w kodzie Javy. ObjectMapper to wstrzyknięty bean Springa (Spring Boot
 * sam go konfiguruje — masz to za darmo, nie musisz nic dodatkowo pisać).
 */

@Component
@RequiredArgsConstructor
public class CardContentSerializer {

    private final ObjectMapper objectMapper;

    public String toJson(CardContent content) {
        return objectMapper.writeValueAsString(content);
    }

    public CardContent fromJson(String json) {
        return objectMapper.readValue(json, CardContent.class);
    }
}
