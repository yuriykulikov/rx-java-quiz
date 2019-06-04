import io.reactivex.Observable;
import io.reactivex.functions.Cancellable;
import org.jetbrains.annotations.NotNull;
import rx.quiz.observables.IntermediateCallbackKoan;

public class Callback extends IntermediateCallbackKoan {
    @NotNull
    @Override
    public Observable<Integer> createLazyObservableFromCallback(@NotNull CallbackBasedInterface callbackBasedInterface) {
        return Observable.create(emitter -> {
            Cancellable callback = callbackBasedInterface.registerCallback(value -> emitter.onNext(value));
            emitter.setCancellable(() -> callback.cancel());
        });
    }
}
