package rx.quiz;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import org.jetbrains.annotations.NotNull;
import org.junit.Assume;
import rx.quiz.combining.BeginnerCombineTimersKoan;

public class Example extends BeginnerCombineTimersKoan {
    @NotNull
    @Override
    public Observable<Long> emitValuesAtGivenTime(@NotNull Scheduler scheduler) {
        Assume.assumeFalse(true); // TODO
        return null;
    }
}
