import io.reactivex.Observable;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import rx.quiz.basics.BeginnerOperatorsKoan;

public class Operators extends BeginnerOperatorsKoan {
    @NotNull
    @Override
    public Observable<Integer> allowOnlyExistingOptionalsThrough(@NotNull Observable<Optional<Integer>> input) {
        return input.filter(value -> value.isPresent()).map(value -> value.get());
    }
}
