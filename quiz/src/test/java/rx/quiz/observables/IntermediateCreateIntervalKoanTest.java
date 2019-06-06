package rx.quiz.observables;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class IntermediateCreateIntervalKoanTest extends IntermediateCreateIntervalKoan {

    @NotNull
    @Override
    public Observable<Integer> interval(@NotNull Scheduler scheduler, long period, @NotNull TimeUnit unit) {
        AtomicInteger value = new AtomicInteger(0);

        return Observable.create(emitter -> scheduler.scheduleDirect(new Runnable() {
            @Override
            public void run() {
                emitter.onNext(value.getAndIncrement());
                scheduler.scheduleDirect(this, period, unit);
            }
        }, period, unit));
    }
}
