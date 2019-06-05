package rx.quiz.observables;

import io.reactivex.Observable;
import io.reactivex.functions.Cancellable;
import org.jetbrains.annotations.NotNull;

public class IntermediateCallbackKoanTest extends IntermediateCallbackKoan {

    @NotNull
    @Override
    public Observable<Integer> createLazyObservableFromCallback(@NotNull CallbackBasedInterface callbackBasedInterface) {
        return Observable.create(emitter -> {
            Cancellable cancellable = callbackBasedInterface.registerCallback(emitter::onNext);
            emitter.setCancellable(cancellable);
        });
    }
}
