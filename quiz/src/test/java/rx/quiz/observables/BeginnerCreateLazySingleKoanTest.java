package rx.quiz.observables;

import io.reactivex.Single;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BeginnerCreateLazySingleKoanTest extends BeginnerCreateLazySingleKoan {

    @NotNull
    @Override
    public Single<Integer> createLazySingle(@NotNull Supplier<Integer> supplier) {
        return Single.defer(() -> Single.just(supplier.get()));
    }
}
