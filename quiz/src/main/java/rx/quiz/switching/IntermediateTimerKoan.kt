package rx.quiz.switching

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * # Task
 * Create an [Observable] which will emit values with increasing time **between** emits:
 *
 * * 3 Seconds pause
 * * Emit any value
 * * 4 Seconds pause
 * * Emit any value
 * * 5 Seconds pause
 * * Emit last value
 *
 * ## Detailed description
 *
 * Unlike [rx.quiz.combining.BeginnerCombineTimersKoan], the pauses are not absolute. They are relative - time **between** emits
 * Take your time, there could be many possible solutions. You may find inserting sysouts useful for debugging
 *
 * ## Requires
 *
 * * Basic knowledge about [Observable.timer]
 * * Advanced knowledge about Hot and Cold
 */
abstract class IntermediateTimerKoan {
    /**
     * @see [IntermediateTimerKoan]
     * @param scheduler use it for your timers
     */
    abstract fun whoaSlowDown(scheduler: Scheduler): Observable<Long>

    @Test
    fun `Create an Observable which emits values distributed in time`() {
        val scheduler = TestScheduler()
        val values = whoaSlowDown(scheduler)
                .timeInterval(scheduler)
                .test()
                .apply {
                    repeat(15) {
                        println("Time tick: $it")
                        scheduler.advanceTimeBy(1, TimeUnit.SECONDS)
                    }
                }
                .values()

        assertThat(values.map { it.time() / 1000 })
                .apply { describedAs("Delays between emits in seconds") }
                .containsExactly(3L, 4L, 5L)
    }
}