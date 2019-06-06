package rx.quiz.observables;

import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class IntermediateCreateLazyObservableKoanTest extends IntermediateCreateLazyObservableKoan {

    @NotNull
    @Override
    public Observable<String> createLazyObservableFromSupplier(@NotNull Supplier<String> supplier) {
        return Observable.create(emitter -> {
            emitter.onNext(supplier.get());
            emitter.onNext(supplier.get());
            emitter.onNext(supplier.get());
            emitter.onComplete();
        });
    }
}
