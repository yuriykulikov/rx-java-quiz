import io.reactivex.Single;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import rx.quiz.observables.BeginnerCreateLazySingleKoan;

public class FirstTask extends BeginnerCreateLazySingleKoan {
    @NotNull
    @Override
    public Single<Integer> createLazySingle(@NotNull Supplier<Integer> supplier) {
        Integer value = supplier.get();

        return Single.create(emitter -> {
            emitter.onSuccess(value);
        });
    }
}
