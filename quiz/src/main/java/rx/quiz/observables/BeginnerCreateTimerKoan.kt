package rx.quiz.observables

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * # Task
 *
 * Using only [Observable.create] and the given Scheduler, implement [io.reactivex.Observable.timer].
 *
 * ## Detailed description
 * Emit value 0 and complete after the delay.
 *
 * **You can use [Scheduler.scheduleDirect] to schedule a delayed Runnable**
 *
 * ## Requires
 *
 * * Basic knowledge about [Observable.timer]
 */
abstract class BeginnerCreateTimerKoan {
    /**
     * @see [BeginnerCreateTimerKoan]
     */
    abstract fun timer(scheduler: Scheduler, delay: Long, unit: TimeUnit): Observable<Int>

    @Test
    fun `Create a Timer using only Observable#create`() {
        val testScheduler = TestScheduler()
        val timer = timer(testScheduler, 1, TimeUnit.SECONDS)

        timer.test().apply {
            testScheduler.triggerActions()
            assertNotComplete().assertNoValues()
            testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)
            assertComplete()
            assertValue(0)
        }

        timer.test().apply {
            testScheduler.triggerActions()
            assertNotComplete().assertNoValues()
            testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
            assertNoValues().assertNotComplete()
        }

        timer.test().apply {
            testScheduler.triggerActions()
            assertNotComplete().assertNoValues()
            dispose()
            testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)
            assertNoValues().assertNotComplete()
        }
    }
}