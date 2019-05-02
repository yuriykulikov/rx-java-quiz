@file:Suppress("FunctionName")

package rx.quiz.multicast

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test

/**
 * # Task
 *
 * Given an API which allows to create Observables, provide an Observable, which will **mirror** the elements of the given
 * Observable to all its subscribers, but only uses the given API **ONCE**.
 *
 * ## Detailed description
 *
 * [ObservableApi.observeWeather] represents an expensive subscription, which must exist only once in the system. You have
 * to create something like a distribution point. Until this point, there must be only **one** flow. This means the the
 * method [ObservableApi.observeWeather] must be called only once and the returned Observable can be used only once.
 *
 * The Observable which you return will be used by the test code **multiple times**. It must **mirror** all the events
 * of the initial Observable to all subscribers which will be created by the test code.
 *
 * ## Requires
 *
 * * Basic knowledge about [Observable] multicasting operators
 * * Basic knowledge about [io.reactivex.observables.ConnectableObservable]
 */
abstract class BeginnerMulticastKoan {
    /** A typical reactive API*/
    interface ObservableApi {
        /** Queries a remote server - this is an **expensive** operation */
        fun observeWeather(zipCode: Int): Observable<String>
    }

    /**
     * @see [BeginnerMulticastKoan]
     */
    abstract fun distributeWeatherForecasts(api: ObservableApi): Observable<String>

    @Test
    fun `Multicasted API Observable supplies many observers but only uses the input once`() {
        val subject: PublishSubject<String> = PublishSubject.create()
        var refCount = 0
        val result = distributeWeatherForecasts(object : ObservableApi {
            override fun observeWeather(zipCode: Int) = Observable.defer {
                refCount++
                subject
            }
        })

        val tests = (0..3).map { result.test() }

        subject.onNext("25C, Sunny")

        tests.forEach{
            it.assertValue("25C, Sunny")
        }

        assertThat(refCount).describedAs("Amount of subscribers").isEqualTo(1)
    }
}
