package rx.quiz.switching;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class IntermediateTimerKoanTest extends IntermediateTimerKoan {

    @NotNull
    @Override
    public Observable<Long> whoaSlowDown(@NotNull Scheduler scheduler) {
        return emitValues(scheduler, 3L);
    }

    private Observable<Long> emitValues(@NotNull Scheduler scheduler, @NonNull Long delay) {
        return Observable.concat(Observable.timer(delay, TimeUnit.SECONDS, scheduler),
                Observable.defer(() -> emitValues(scheduler, delay + 1)));
    }
}
