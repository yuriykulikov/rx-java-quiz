import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import org.jetbrains.annotations.NotNull;
import rx.quiz.multicast.BeginnerMulticastKoan;

public class BeginnerMulticast extends BeginnerMulticastKoan {
    @NotNull
    @Override
    public Observable<String> distributeWeatherForecasts(@NotNull ObservableApi api) {
        PublishSubject<String> subject = PublishSubject.create();
        api.observeWeather(91052)
                .subscribe(subject::onNext);
        return subject;
    }
}
