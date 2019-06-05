package rx.quiz.combining;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ProCombineTimersKoanTest extends ProCombineTimersKoan {

    @NotNull
    @Override
    protected ObservableTransformer<String, String> timeoutIfFirstElementDoesNotArriveIn(
            @NotNull Scheduler scheduler, long delay, @NotNull TimeUnit unit) {

        return upstream -> {
            Observable<String> timeout = Observable.create(emitter ->
                    scheduler.scheduleDirect(() -> emitter.onError(new TimeoutException()), delay, unit));

            return upstream.ambWith(timeout);
        };
    }
}
