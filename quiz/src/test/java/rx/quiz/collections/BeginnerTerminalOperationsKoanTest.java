package rx.quiz.collections;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BeginnerTerminalOperationsKoanTest extends BeginnerTerminalOperationsKoan {

    @Override
    public int sumOfEvenNumbers(@NotNull List<String> numbers) {
        return numbers.stream()
                .map(Integer::parseInt)
                .filter(value -> value % 2 == 0)
                .reduce(0, Integer::sum);
    }
}
