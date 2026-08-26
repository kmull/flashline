package pl.flashline.sourcetext;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/decks/{deckId}/import")
@Tag(name = "Import", description = "Import tekstu źródłowego z fiszkami wygenerowanymi przez AI")
@RequiredArgsConstructor
public class ImportController {

    private final ImportService importService;

    @PostMapping
    @Operation(summary = "Import tekstu + fiszek (Json wygenerowany przez AI)")
    public ResponseEntity<ImportResponse> importSourceText(
            @PathVariable Long deckId,
            @Valid @RequestBody ImportSourceTextRequest request) {
        return ResponseEntity.ok(importService.importSourceText(deckId, request));
    }
}
