package rx.quiz.combining;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class IntermediateCombineTimersKoanTest extends IntermediateCombineTimersKoan {

    @NotNull
    @Override
    protected ObservableTransformer<String, String> timeoutIfFirstElementDoesNotArriveIn(
            @NotNull Scheduler scheduler, long delay, @NotNull TimeUnit unit) {
        return upstream -> {
            Observable<String> timeout = Observable.<String>never().timeout(delay, unit, scheduler);

            return upstream.ambWith(timeout);
        };
    }
}
