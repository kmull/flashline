package pl.flashline.card;

import java.util.List;

public record DragOrderContent(
        List<String> items,
        List<Integer> correctOrder
) implements CardContent {
}
