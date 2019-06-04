import io.reactivex.Observable;
import io.reactivex.Scheduler;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;
import rx.quiz.observables.IntermediateCreateIntervalKoan;

public class CreateInterval extends IntermediateCreateIntervalKoan {
    @NotNull
    @Override
    public Observable<Integer> interval(@NotNull Scheduler scheduler, long period, @NotNull TimeUnit unit) {
        return Observable.create(emitter -> {
                AtomicInteger i = new AtomicInteger(0);
                scheduler.scheduleDirect(() -> emitter.onNext(i.getAndIncrement()), period * (i.get()+1), unit);
                if(i.get() == period) {
                    emitter.onComplete();
                }
            }
        );
    }
}
