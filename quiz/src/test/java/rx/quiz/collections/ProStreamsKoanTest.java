package rx.quiz.collections;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProStreamsKoanTest extends ProStreamsKoan {

    @NotNull
    @Override
    public String sortStringWithMultipleCriteria(@NotNull String input) {
        return Stream.of(input.toLowerCase().split(" "))
                .map(s -> s.chars().mapToObj(c -> String.valueOf((char) c)))
                .map(s -> s.distinct().collect(Collectors.joining()))
                .sorted((s1, s2) -> {
                    if (s1.length() != s2.length()) {
                        return Integer.compare(s1.length(), s2.length());
                    }
                    return s1.compareTo(s2);
                }).collect(Collectors.joining(" "));
    }
}
