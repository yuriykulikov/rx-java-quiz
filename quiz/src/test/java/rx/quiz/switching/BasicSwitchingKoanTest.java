package rx.quiz.switching;

import io.reactivex.Single;
import org.jetbrains.annotations.NotNull;

public class BasicSwitchingKoanTest extends BasicSwitchingKoan {

    @NotNull
    @Override
    public Single<Integer> queryPinpointLocation(@NotNull LocationApi locationApi) {
        return locationApi.coarseLocation()
                .flatMap(locationApi::fineLocation)
                .flatMap(locationApi::pinpointLocation);
    }
}
