import io.reactivex.Observable;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import rx.quiz.observables.BeginnerCreateEagerObservableKoan;

public class EagerObservable extends BeginnerCreateEagerObservableKoan {
    @NotNull
    @Override
    public Observable<String> createEagerObservableFromSupplier(@NotNull Supplier<String> supplier) {
        String val1 = supplier.get();
        String val2 = supplier.get();
        String val3 = supplier.get();
        //return Observable.just(val1, val2, val3);
        return Observable.create(emitter -> {
            emitter.onNext(val1);
            emitter.onNext(val2);
            emitter.onNext(val3);
            emitter.onComplete();
        });
    }
}
