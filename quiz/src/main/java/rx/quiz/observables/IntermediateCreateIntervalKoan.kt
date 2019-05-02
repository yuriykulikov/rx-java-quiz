package rx.quiz.observables

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * # Task
 *
 * Using only [Observable.create] implement [Observable.interval] using the given Scheduler.
 *
 * ## Detailed description
 *
 * Implement [Observable.interval] which has initialDelay the same as the period. This means, that the first value (0)
 * is emitted after the first period, and then each consequent value is also emitted periodically.
 *
 * For example, if period is 5 seconds, the sequence looks this this:
 *
 * --- 5 sec ---( 0 )--- 5 sec ---( 1 )--- 5 sec ---( 2 )--- 5 sec ---( 3 )--- 5 sec ---( 4 )
 *
 * You can use either [Observable.timer] or [Scheduler.scheduleDirect] to schedule timed events.
 *
 * ## Requires
 *
 * * Basic knowledge about [Observable.interval]
 * * Advanced knowledge about operator composition
 */
abstract class IntermediateCreateIntervalKoan {
    /**
     * @see [IntermediateCreateIntervalKoan]
     */
    abstract fun interval(scheduler: Scheduler, period: Long, unit: TimeUnit): Observable<Int>

    @Test
    fun `Create an Interval using only Observable#create`() {
        val testScheduler = TestScheduler()
        val timer = interval(testScheduler, 1, TimeUnit.SECONDS)

        timer.test().apply {
            testScheduler.triggerActions()
            assertNotComplete()
            testScheduler.advanceTimeBy(10, TimeUnit.SECONDS)
            assertNotComplete()
            assertValueCount(10)
            assertValues(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        }
    }
}