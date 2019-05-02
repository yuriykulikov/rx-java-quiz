package rx.quiz.combining

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import rx.quiz.combining.IntermediateCombineLatestKoan.ParkingApi
import rx.quiz.combining.IntermediateCombineLatestKoan.Sound

/**
 * # Task
 * Create an [Observable] which helps the user by providing audible feedback about the distance when parking
 *
 * ## Detailed description
 * Using [ParkingApi] evaluate real-time information from multiple sensors using the following algorithm:
 * * If parking is off ([ParkingApi.isSwitchedOn]), emit [Sound.NONE]
 * * Otherwise, depending on the [ParkingApi.isRearSpeedOn], evaluate distances to front or rear sensors
 * * if distance is more than 3 meters emit [Sound.NONE]
 * * if distance is 3 meters emit [Sound.BEEEP_BEEEP]
 * * if distance is 2 meters emit [Sound.BEEP_BEEP_BEEP]
 * * if distance is 1 meter emit [Sound.BIP_BIP_BIP_BIP]
 * * if distance is 0 meters emit [Sound.BEEEEEEEEEEEE]
 *
 * ## Requires
 *
 * * Advanced knowledge about combining operators
 */
abstract class IntermediateCombineLatestKoan {
    enum class Sound {
        NONE, BEEEP_BEEEP, BEEP_BEEP_BEEP, BIP_BIP_BIP_BIP, BEEEEEEEEEEEE;
    }

    interface ParkingApi {
        fun isSwitchedOn(): Observable<Boolean>
        fun frontLeft(): Observable<Int>
        fun frontRight(): Observable<Int>
        fun rearLeft(): Observable<Int>
        fun rearRight(): Observable<Int>
        fun isRearSpeedOn(): Observable<Boolean>
    }

    abstract fun parkingAssistant(parkingApi: ParkingApi): Observable<Sound>

    @Test
    fun `use data from multiple sensors to create a parking assistant`() {
        val isSwitchedOn = BehaviorSubject.createDefault(false)
        val frontLeft = BehaviorSubject.createDefault(10)
        val frontRight = BehaviorSubject.createDefault(10)
        val rearLeft = BehaviorSubject.createDefault(10)
        val rearRight = BehaviorSubject.createDefault(10)
        val isRearSpeedOn = BehaviorSubject.createDefault(false)
        val result = parkingAssistant(object : ParkingApi {
            override fun isSwitchedOn() = isSwitchedOn
            override fun frontLeft() = frontLeft
            override fun frontRight() = frontRight
            override fun rearLeft() = rearLeft
            override fun rearRight() = rearRight
            override fun isRearSpeedOn() = isRearSpeedOn
        }).test()

        isRearSpeedOn.onNext(true)
        isRearSpeedOn.onNext(false)
        frontLeft.onNext(6)
        frontLeft.onNext(1)
        frontLeft.onNext(4)
        isSwitchedOn.onNext(true)
        frontRight.onNext(3)
        frontRight.onNext(2)
        frontLeft.onNext(1)
        frontLeft.onNext(0)
        isRearSpeedOn.onNext(true)
        rearRight.onNext(3)
        rearLeft.onNext(2)
        rearLeft.onNext(1)
        frontLeft.onNext(0)
        frontLeft.onNext(3)
        isRearSpeedOn.onNext(false)
        frontRight.onNext(2)
        frontLeft.onNext(1)
        rearRight.onNext(0)
        frontLeft.onNext(0)
        frontRight.onNext(0)

        val values = result.values().iterator()
        assertThat(values.next()).describedAs("is off").isEqualTo(Sound.NONE)
        assertThat(values.next()).describedAs("is off").isEqualTo(Sound.NONE)
        assertThat(values.next()).describedAs("is off").isEqualTo(Sound.NONE)
        assertThat(values.next()).describedAs("is off").isEqualTo(Sound.NONE)
        assertThat(values.next()).describedAs("is off").isEqualTo(Sound.NONE)
        assertThat(values.next()).describedAs("is off").isEqualTo(Sound.NONE)
        assertThat(values.next()).describedAs("too far").isEqualTo(Sound.NONE)
        assertThat(values.next()).describedAs("closer").isEqualTo(Sound.BEEEP_BEEEP)
        assertThat(values.next()).describedAs("closer").isEqualTo(Sound.BEEP_BEEP_BEEP)
        assertThat(values.next()).describedAs("very close").isEqualTo(Sound.BIP_BIP_BIP_BIP)
        assertThat(values.next()).describedAs("almost hit the wall").isEqualTo(Sound.BEEEEEEEEEEEE)
        assertThat(values.next()).describedAs("switched gears").isEqualTo(Sound.NONE)
        assertThat(values.next()).describedAs("something coming from behind").isEqualTo(Sound.BEEEP_BEEEP)
        assertThat(values.next()).describedAs("something coming from behind").isEqualTo(Sound.BEEP_BEEP_BEEP)
        assertThat(values.next()).describedAs("something coming from behind").isEqualTo(Sound.BIP_BIP_BIP_BIP)
        assertThat(values.next()).describedAs("something coming from behind").isEqualTo(Sound.BIP_BIP_BIP_BIP)
        assertThat(values.next()).describedAs("something coming from behind").isEqualTo(Sound.BIP_BIP_BIP_BIP)
        assertThat(values.next()).describedAs("switched gear again").isEqualTo(Sound.BEEP_BEEP_BEEP)
        assertThat(values.next()).describedAs("going forwards").isEqualTo(Sound.BEEP_BEEP_BEEP)
        assertThat(values.next()).describedAs("going forwards").isEqualTo(Sound.BIP_BIP_BIP_BIP)
        assertThat(values.next()).describedAs("going forwards").isEqualTo(Sound.BIP_BIP_BIP_BIP)
        assertThat(values.next()).describedAs("going forwards").isEqualTo(Sound.BEEEEEEEEEEEE)
        assertThat(values.next()).describedAs("done").isEqualTo(Sound.BEEEEEEEEEEEE)
    }
}