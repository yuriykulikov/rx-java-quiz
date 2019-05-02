package rx.quiz.combining

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * # Task
 * Create an [Observable] which will emit values at defined points in time:
 *
 * * First value is emitted exactly after 5 seconds have passed
 * * Second value is emitted exactly after 15 seconds have passed
 * * Last value is emitted exactly after 60 seconds have passed
 *
 * ## Detailed description
 *
 * Each time is absolute (starting from 0)
 *
 * **Use the given scheduler!**
 *
 * ## Requires
 *
 * * Basic knowledge about [Observable.timer]
 * * Advanced knowledge about combining operators
 */
abstract class BeginnerCombineTimersKoan {
    /**
     * @see BeginnerCombineTimersKoan
     * @param scheduler use it for your timers
     */
    abstract fun emitValuesAtGivenTime(scheduler: Scheduler): Observable<Long>

    @Test
    fun `Create an Observable which emits values at given time`() {
        val scheduler = TestScheduler()
        val values = emitValuesAtGivenTime(scheduler)
                .timestamp(scheduler)
                .test()
                .apply {
                    repeat(15) {
                        scheduler.advanceTimeBy(1, TimeUnit.MINUTES)
                    }
                }
                .values()

        assertThat(values.map { it.time() / 1000 })
                .apply { describedAs("Timestamps") }
                .containsExactly(5L, 15L, 60L)
    }
}