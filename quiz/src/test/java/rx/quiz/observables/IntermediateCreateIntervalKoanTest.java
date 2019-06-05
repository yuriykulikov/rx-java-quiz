package rx.quiz.observables;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class IntermediateCreateIntervalKoanTest extends IntermediateCreateIntervalKoan {

    @NotNull
    @Override
    public Observable<Integer> interval(@NotNull Scheduler scheduler, long period, @NotNull TimeUnit unit) {
        return Observable.create(emitter -> emitValues(emitter, 0, scheduler, period, unit));
    }

    private void emitValues(@NonNull ObservableEmitter<Integer> emitter, int value, @NotNull Scheduler scheduler,
                            long period, @NotNull TimeUnit unit) {
        scheduler.scheduleDirect(() -> {
            emitter.onNext(value);
            emitValues(emitter, value + 1, scheduler, period, unit);
        }, period, unit);
    }
}
