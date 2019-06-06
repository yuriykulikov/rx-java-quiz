package rx.quiz.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProStreamsKoanTest extends ProStreamsKoan {

    @NotNull
    @Override
    public String sortStringWithMultipleCriteria(@NotNull String input) {
        Comparator<String> compareByLengthThenValue = Comparator.comparing(String::length)
                .thenComparing(Function.identity());

        return Stream.of(input.toLowerCase().split(" "))
                .map(s -> s.chars().mapToObj(c -> String.valueOf((char) c)))
                .map(s -> s.distinct().collect(Collectors.joining()))
                .sorted(compareByLengthThenValue)
                .collect(Collectors.joining(" "));
    }
}
