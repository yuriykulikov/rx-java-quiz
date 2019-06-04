import java.util.Arrays;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import rx.quiz.collections.IntermediateStringsKoan;

public class Strings extends IntermediateStringsKoan {
    @NotNull
    @Override
    public String removeDuplicatesInString(@NotNull String input) {
        return Arrays.stream(input.split(" "))
                .map(word -> word.toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
