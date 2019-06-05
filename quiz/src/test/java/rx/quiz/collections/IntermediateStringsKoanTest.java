package rx.quiz.collections;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntermediateStringsKoanTest extends IntermediateStringsKoan {

    @NotNull
    @Override
    public String removeDuplicatesInString(@NotNull String input) {
        return Stream.of(input.toLowerCase().split(" "))
                .map(s -> s.chars().mapToObj(c -> String.valueOf((char) c)))
                .map(s -> s.distinct().collect(Collectors.joining()))
                .distinct()
                .collect(Collectors.joining(" "));
    }
}
