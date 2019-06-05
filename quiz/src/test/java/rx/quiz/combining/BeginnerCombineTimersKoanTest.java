package rx.quiz.combining;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import org.jetbrains.annotations.NotNull;

import static java.util.concurrent.TimeUnit.SECONDS;

public class BeginnerCombineTimersKoanTest extends BeginnerCombineTimersKoan {

    @NotNull
    @Override
    public Observable<Long> emitValuesAtGivenTime(@NotNull Scheduler scheduler) {
        return Observable.merge(
                Observable.timer(5, SECONDS, scheduler),
                Observable.timer(15, SECONDS, scheduler),
                Observable.timer(60, SECONDS, scheduler)
        );
    }
}
