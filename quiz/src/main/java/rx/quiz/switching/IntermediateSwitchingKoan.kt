@file:Suppress("FunctionName")

package rx.quiz.switching

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test

/**
 * # Task
 * You are given an API, which you can use to query information current weather at the given location.
 * You have the second API which can be used to track the current location.
 * Use both APIs to continuously monitor weather at you current location (as you move across the country).
 *
 * ## Detailed description
 * Unlike [BasicSwitchingKoan], these are not Singles, these are [Observable]. Most intuitive solution may not work.
 *
 * ## Requires
 *
 * * Advanced knowledge about switching operators
 *
 */
abstract class IntermediateSwitchingKoan {
    /**
     * An API for the test. See [IntermediateSwitchingKoan] description.
     */
    interface LocationApi {
        /**
         * Current location
         */
        fun currentLocation(): Observable<String>
    }

    /**
     * An API for the test. See [IntermediateSwitchingKoan] description.
     */
    interface WeatherApi {
        /**
         * Weather at given location
         */
        fun weatherAt(location: String): Observable<String>
    }

    /**
     * @see [IntermediateSwitchingKoan]
     */
    abstract fun monitorWeather(locationApi: LocationApi, weatherApi: WeatherApi): Observable<String>

    @Test
    fun `Use operators to monitor weather at current location`() {
        val location = BehaviorSubject.createDefault("Erlangen")

        val germany = BehaviorSubject.createDefault("3C, rain")
        val austria = BehaviorSubject.createDefault("-10C, snow")
        val madeira = BehaviorSubject.createDefault("14C, sunny")

        val test = monitorWeather(locationApi = object : LocationApi {
            override fun currentLocation(): Observable<String> {
                return location.hide()
            }

        }, weatherApi = object : WeatherApi {
            override fun weatherAt(location: String): Observable<String> {
                return when (location) {
                    "Erlangen", "Nürnberg", "München" -> germany
                    "Funchal", "Ribeira Brava" -> madeira
                    else -> austria
                }
            }
        }
        ).test()

        germany.onNext("2C, rain")
        austria.onNext("-11C, snow")
        madeira.onNext("13C, sunny")
        germany.onNext("3C, rain")
        austria.onNext("-12C, snow")
        madeira.onNext("15C, sunny")
        location.onNext("Nürnberg")
        germany.onNext("1C, rain")
        austria.onNext("-10C, snow")
        madeira.onNext("11C, sunny")
        germany.onNext("2C, rain")
        austria.onNext("-11C, snow")
        madeira.onNext("14C, sunny")
        location.onNext("München")
        germany.onNext("2C, rain")
        austria.onNext("-11C, snow")
        madeira.onNext("13C, sunny")
        germany.onNext("3C, rain")
        austria.onNext("-12C, snow")
        madeira.onNext("15C, sunny")
        location.onNext("Neustift")
        germany.onNext("2C, rain")
        austria.onNext("-11C, snow")
        madeira.onNext("13C, sunny")
        germany.onNext("3C, rain")
        austria.onNext("-12C, snow")
        madeira.onNext("15C, sunny")
        location.onNext("Funchal")
        germany.onNext("2C, rain")
        austria.onNext("-11C, snow")
        madeira.onNext("13C, sunny")
        germany.onNext("3C, rain")
        austria.onNext("-12C, snow")
        madeira.onNext("15C, sunny")
        location.onNext("Ribeira Brava")
        germany.onNext("2C, rain")
        austria.onNext("-11C, snow")
        madeira.onNext("13C, sunny")
        germany.onNext("3C, rain")
        austria.onNext("-12C, snow")
        madeira.onNext("15C, sunny")
        location.onNext("Nürnberg")
        germany.onNext("1C, clouds")
        austria.onNext("-10C, sunny")
        madeira.onNext("11C, sunny")
        germany.onNext("2C, clouds")
        austria.onNext("-11C, sunny")
        madeira.onNext("14C, sunny")

        assertThat(test.values()).containsExactly(
                "3C, rain",
                "2C, rain",
                "3C, rain",
                "3C, rain",
                "1C, rain",
                "2C, rain",
                "2C, rain",
                "2C, rain",
                "3C, rain",
                "-12C, snow",
                "-11C, snow",
                "-12C, snow",
                "15C, sunny",
                "13C, sunny",
                "15C, sunny",
                "15C, sunny",
                "13C, sunny",
                "15C, sunny",
                "3C, rain",
                "1C, clouds",
                "2C, clouds")
    }

    @Test
    fun `Use operators to monitor weather at current location without leaking anything`() {
        val location = BehaviorSubject.createDefault("Erlangen")

        val germany = BehaviorSubject.createDefault("3C, rain")
        val austria = BehaviorSubject.createDefault("-10C, snow")
        val madeira = BehaviorSubject.createDefault("14C, sunny")

        val test = monitorWeather(locationApi = object : LocationApi {
            override fun currentLocation(): Observable<String> {
                return location.hide()
            }

        }, weatherApi = object : WeatherApi {
            override fun weatherAt(location: String): Observable<String> {
                return when (location) {
                    "Erlangen", "Nürnberg", "München" -> germany
                    "Funchal", "Ribeira Brava" -> madeira
                    else -> austria
                }
            }
        }
        ).test()

        germany.onNext("2C, rain")
        austria.onNext("-11C, snow")
        madeira.onNext("13C, sunny")
        germany.onNext("3C, rain")
        austria.onNext("-12C, snow")
        madeira.onNext("15C, sunny")
        location.onNext("Nürnberg")
        germany.onNext("1C, rain")
        austria.onNext("-10C, snow")
        madeira.onNext("11C, sunny")
        germany.onNext("2C, rain")
        austria.onNext("-11C, snow")
        madeira.onNext("14C, sunny")
        location.onNext("München")
        germany.onNext("2C, rain")
        austria.onNext("-11C, snow")
        madeira.onNext("13C, sunny")
        germany.onNext("3C, rain")
        austria.onNext("-12C, snow")
        madeira.onNext("15C, sunny")
        location.onNext("Neustift")
        germany.onNext("2C, rain")
        austria.onNext("-11C, snow")
        madeira.onNext("13C, sunny")
        germany.onError(RuntimeException("No need to check weather here while on vacation"))
        austria.onNext("-12C, snow")
        madeira.onNext("15C, sunny")
        location.onNext("Funchal")
        germany.onNext("2C, rain")
        austria.onNext("-11C, snow")
        madeira.onNext("13C, sunny")
        germany.onNext("3C, rain")
        austria.onNext("-12C, snow")
        madeira.onNext("15C, sunny")
        location.onNext("Ribeira Brava")
        germany.onNext("2C, rain")
        austria.onNext("-11C, snow")
        madeira.onNext("13C, sunny")
        germany.onNext("3C, rain")
        austria.onNext("-12C, snow")
        madeira.onNext("15C, sunny")

        assertThat(test.values()).containsExactly(
                "3C, rain",
                "2C, rain",
                "3C, rain",
                "3C, rain",
                "1C, rain",
                "2C, rain",
                "2C, rain",
                "2C, rain",
                "3C, rain",
                "-12C, snow",
                "-11C, snow",
                "-12C, snow",
                "15C, sunny",
                "13C, sunny",
                "15C, sunny",
                "15C, sunny",
                "13C, sunny",
                "15C, sunny")
    }
}
