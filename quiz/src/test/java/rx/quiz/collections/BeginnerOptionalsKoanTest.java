package rx.quiz.collections;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BeginnerOptionalsKoanTest extends BeginnerOptionalsKoan {

    @NotNull
    @Override
    public List<Integer> existingOptionals(@NotNull List<Optional<Integer>> input) {
        return input.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
