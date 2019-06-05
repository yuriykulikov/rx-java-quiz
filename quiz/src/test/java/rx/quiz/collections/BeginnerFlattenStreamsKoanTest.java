package rx.quiz.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BeginnerFlattenStreamsKoanTest extends BeginnerFlattenStreamsKoan {

    @NotNull
    @Override
    public List<Integer> flattenList(@NotNull List<? extends List<Integer>> input) {
        return input.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
