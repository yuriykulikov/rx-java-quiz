package rx.quiz.switching;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class IntermediateTimerKoanTest extends IntermediateTimerKoan {

    @NotNull
    @Override
    public Observable<Long> whoaSlowDown(@NotNull Scheduler scheduler) {
        AtomicLong delay = new AtomicLong(3L);

        return Observable.create(emitter -> scheduler.scheduleDirect(new Runnable() {
            @Override
            public void run() {
                emitter.onNext(0L);
                scheduler.scheduleDirect(this, delay.getAndIncrement(), TimeUnit.SECONDS);
            }
        }, delay.getAndIncrement(), TimeUnit.SECONDS));
    }
}
