package rx.quiz.multicast;

import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;

public class IntermediateMulticastKoanTest extends IntermediateMulticastKoan {

    @NotNull
    @Override
    public Observable<String> distributeWeatherForecasts(@NotNull ObservableApi api) {
        return api.observeWeather(0)
                .replay(1)
                .refCount();
    }
}
