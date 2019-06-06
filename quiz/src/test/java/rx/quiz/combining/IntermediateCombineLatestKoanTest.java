package rx.quiz.combining;

import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;

public class IntermediateCombineLatestKoanTest extends IntermediateCombineLatestKoan {

    @NotNull
    @Override
    public Observable<Sound> parkingAssistant(@NotNull ParkingApi api) {
        return Observable.combineLatest(
                api.isSwitchedOn(),
                api.isRearSpeedOn(),
                api.frontLeft(),
                api.frontRight(),
                api.rearLeft(),
                api.rearRight(),
                (isSwitchedOn, isRearSpeedOn, frontLeft, frontRight, rearLeft, rearRight) -> {
                    if (!isSwitchedOn) {
                        return Sound.NONE;
                    }
                    if (isRearSpeedOn) {
                        return soundByDistance(Math.min(rearLeft, rearRight));
                    }

                    return soundByDistance(Math.min(frontLeft, frontRight));
                }
        );
    }

    private Sound soundByDistance(int distance) {
        if (distance > 3) {
            return Sound.NONE;
        } else if (distance == 3) {
            return Sound.BEEEP_BEEEP;
        } else if (distance == 2) {
            return Sound.BEEP_BEEP_BEEP;
        } else if (distance == 1) {
            return Sound.BIP_BIP_BIP_BIP;
        } else if (distance == 0) {
            return Sound.BEEEEEEEEEEEE;
        }

        throw new IllegalArgumentException("Incorrect distance " + distance);
    }
}
