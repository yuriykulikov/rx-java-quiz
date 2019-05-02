package rx.quiz

import io.reactivex.Single
import org.junit.Assume
import rx.quiz.switching.BasicSwitchingKoan

class BasicSwitchingSolution : BasicSwitchingKoan() {
    override fun queryPinpointLocation(locationApi: LocationApi): Single<Int> {
        Assume.assumeFalse(true)
        TODO()
    }
}