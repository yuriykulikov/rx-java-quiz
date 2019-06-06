package rx.quiz.observables;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class BeginnerCreateTimerKoanTest extends BeginnerCreateTimerKoan {

    @NotNull
    @Override
    public Observable<Integer> timer(@NotNull Scheduler scheduler, long delay, @NotNull TimeUnit unit) {
        return Observable.create(emitter -> scheduler.scheduleDirect(() -> {
            emitter.onNext(0);
            emitter.onComplete();
        }, delay, unit));
    }
}
