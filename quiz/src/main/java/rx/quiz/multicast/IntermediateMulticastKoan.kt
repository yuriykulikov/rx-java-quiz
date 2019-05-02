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

 * ## Additional tests compared to [BeginnerMulticastKoan]
 *
 * In addition to [BeginnerMulticastKoan], the Observable which you return must implement following behaviour:
 * * Subscribe for the weather forecasts only when at lease one subscriber arrives
 * * If later more subscribers arrive (after some values were already emitted), your Observable must replay 1 last value
 * * You Observable must **unsubscribe from the upstream** when last subscriber leaves
 *
 * ## Requires
 *
 * * Advanced knowledge about [Observable] multicasting
 * * Advanced knowledge about [io.reactivex.observables.ConnectableObservable]
 */
abstract class IntermediateMulticastKoan {
    /** A typical reactive API*/
    interface ObservableApi {
        /** Queries a remote server - this is an **expensive** operation */
        fun observeWeather(zipCode: Int): Observable<String>
    }

    /**
     * @see [IntermediateMulticastKoan]
     */
    abstract fun distributeWeatherForecasts(api: ObservableApi): Observable<String>

    @Test
    fun `Multicasted API Observable supplies many observers when they come but only uses the input once`() {
        val subject: PublishSubject<String> = PublishSubject.create()
        var refCount = 0
        val result = distributeWeatherForecasts(object : ObservableApi {
            override fun observeWeather(zipCode: Int) = Observable.create<String> { emitter ->
                refCount++
                emitter.setCancellable { refCount-- }
                subject.subscribe { emitter.onNext(it) }
            }
        })

        // this goes nowhere
        subject.onNext("25C, Sunny")
        assertThat(refCount).describedAs("Amount of subscribers before anyone subscribes").isEqualTo(0)
        val test1 = result.test()
        assertThat(refCount).describedAs("Amount of subscribers after 1 subscribes").isEqualTo(1)
        subject.onNext("26C, Sunny")
        test1.assertValue("26C, Sunny")

        val test2 = result.test()
        assertThat(refCount).describedAs("Amount of subscribers after 2 subscribes").isEqualTo(1)
        test2.assertValue("26C, Sunny")

        val test3 = result.test()
        assertThat(refCount).describedAs("Amount of subscribers after 3 subscribes").isEqualTo(1)
        test3.assertValue("26C, Sunny")

        subject.onNext("27C, Sunny")

        listOf(test1, test2, test3).forEach {
            it.assertValueAt(1, "27C, Sunny")
        }

        test1.dispose()
        assertThat(refCount).describedAs("Amount of subscribers after 1 unsubscribe").isEqualTo(1)
        listOf(test2, test3).forEach { it.dispose() }
        assertThat(refCount).describedAs("Amount of subscribers after all unsubscribe").isEqualTo(0)
    }
}
