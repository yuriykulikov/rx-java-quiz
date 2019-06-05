package rx.quiz.switching;

import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;

public class IntermediateSwitchingKoanTest extends IntermediateSwitchingKoan {

    @NotNull
    @Override
    public Observable<String> monitorWeather(@NotNull LocationApi locationApi, @NotNull WeatherApi weatherApi) {
        return locationApi.currentLocation()
                .switchMap(weatherApi::weatherAt);
    }
}
