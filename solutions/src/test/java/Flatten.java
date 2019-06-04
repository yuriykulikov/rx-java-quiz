import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import rx.quiz.collections.BeginnerFlattenStreamsKoan;

public class Flatten extends BeginnerFlattenStreamsKoan {
    @NotNull
    @Override
    public List<Integer> flattenList(@NotNull List<? extends List<Integer>> input) {
        return input.stream()
                .flatMap(list -> list.stream())
                .collect(Collectors.toList());
    }
}
