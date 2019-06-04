import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;
import rx.quiz.multicast.IntermediateMulticastKoan;

public class InterMulticast extends IntermediateMulticastKoan {
    @NotNull
    @Override
    public Observable<String> distributeWeatherForecasts(@NotNull ObservableApi api) {
        return api.observeWeather(91052)
                .replay(1)
                .refCount();
    }
}
