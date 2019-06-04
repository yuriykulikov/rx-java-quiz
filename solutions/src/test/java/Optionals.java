import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import rx.quiz.collections.BeginnerOptionalsKoan;

public class Optionals extends BeginnerOptionalsKoan {
    @NotNull
    @Override
    public List<Integer> existingOptionals(@NotNull List<Optional<Integer>> input) {
        return input.stream()
                .filter(value -> value.isPresent())
                .map(value -> value.get())
                .collect(Collectors.toList());
    }
}
