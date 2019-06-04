import io.reactivex.Observable;
import io.reactivex.Scheduler;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import rx.quiz.observables.BeginnerCreateTimerKoan;

public class CreateTimer extends BeginnerCreateTimerKoan {
    @NotNull
    @Override
    public Observable<Integer> timer(@NotNull Scheduler scheduler, long delay, @NotNull TimeUnit unit) {
        return Observable.create(emitter -> {
            scheduler.scheduleDirect(() -> {
                    emitter.onNext(0);
                    emitter.onComplete();
                }, delay, unit);
        });
    }
}
