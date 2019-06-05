package rx.quiz.switching;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class IntermediateTimerKoanTest extends IntermediateTimerKoan {

    @NotNull
    @Override
    public Observable<Long> whoaSlowDown(@NotNull Scheduler scheduler) {
        return Observable.create(emitter -> emitValues(emitter, scheduler, 3L));
    }

    private void emitValues(@NonNull ObservableEmitter<Long> emitter, @NotNull Scheduler scheduler, @NonNull Long delay) {
        scheduler.scheduleDirect(() -> {
            emitter.onNext(0L);
            emitValues(emitter, scheduler, delay + 1);
        }, delay, TimeUnit.SECONDS);
    }
}
