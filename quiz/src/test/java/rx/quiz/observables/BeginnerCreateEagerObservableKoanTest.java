package rx.quiz.observables;

import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BeginnerCreateEagerObservableKoanTest extends BeginnerCreateEagerObservableKoan {

    @NotNull
    @Override
    public Observable<String> createEagerObservableFromSupplier(@NotNull Supplier<String> supplier) {
        return Observable.just(supplier.get(), supplier.get(), supplier.get());
    }
}
