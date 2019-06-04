import io.reactivex.Observable;
import io.reactivex.Single;
import org.jetbrains.annotations.NotNull;
import rx.quiz.basics.IntermediateOperatorsKoan;

public class InterOperators extends IntermediateOperatorsKoan {
    @NotNull
    @Override
    public Single<Integer> sumOfEvenNumbers(@NotNull Observable<String> numbers) {
        return numbers
                .map(string -> Integer.parseInt(string))
                .filter(value -> value % 2 == 0)
                .reduce(0, (a, b) -> a + b);
    }
}
