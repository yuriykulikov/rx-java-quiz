import java.util.List;
import org.jetbrains.annotations.NotNull;
import rx.quiz.collections.BeginnerTerminalOperationsKoan;

public class TerminalOperators extends BeginnerTerminalOperationsKoan {
    @Override
    public int sumOfEvenNumbers(@NotNull List<String> numbers) {
        return numbers.stream()
                .map(string -> Integer.parseInt(string))
                .filter(number -> number % 2 == 0)
                .reduce(0, (a, b) -> a + b);
    }
}
