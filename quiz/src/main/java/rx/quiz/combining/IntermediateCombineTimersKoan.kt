package rx.quiz.combining

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * # Task
 *
 * Create an [ObservableTransformer], which will emit an exception if the stream does not emit the first item in time.
 *
 * **Use the given scheduler to schedule the timed events!**
 *
 * ## Detailed description
 *
 * Behaviour is similar to [Observable.timeout], but instead of checking for timeouts every time an event is emitted,
 * all timers are stopped after the first value arrives.
 *
 * * If the stream continuously emits values, no Timeout must be emitted. **Behaviour similar to [Observable.timeout]**
 * * If first element was emitted within the given time, no Timeouts must be emitted, even if no more values are emitted
 * * If first element was NOT emitted within the given time, a Timeout Exception should be emitted
 *
 * ## Requires
 *
 * * Basic knowledge about [Observable.compose]
 * * Basic knowledge about [Scheduler]
 * * Advanced knowledge about [Observable.timeout]
 * * Intermediate knowledge about combining operators
 */
abstract class IntermediateCombineTimersKoan {
    /**
     * @see [IntermediateCombineTimersKoan]
     */
    protected abstract fun timeoutIfFirstElementDoesNotArriveIn(scheduler: Scheduler, delay: Long, unit: TimeUnit): ObservableTransformer<String, String>

    @Test
    fun `Use the correct scheduler`() {
        val scheduler = TestScheduler()
        RxJavaPlugins.setComputationSchedulerHandler { throw RuntimeException("Implicit scheduler must not be used. Instead, scheduler must be given explicitly") }
        Observable.interval(1, TimeUnit.SECONDS, scheduler)
                .map { it.toString() }
                .compose(timeoutIfFirstElementDoesNotArriveIn(scheduler, 3, TimeUnit.SECONDS))
                .test()
                .assertNoErrors()
    }

    @Test
    fun `No errors if the stream keeps emitting values`() {
        val scheduler = TestScheduler()
        Observable.interval(1, TimeUnit.SECONDS, scheduler)
                .map { it.toString() }
                .compose(timeoutIfFirstElementDoesNotArriveIn(scheduler, 3, TimeUnit.SECONDS))
                .test().apply {
                    scheduler.advanceTimeBy(100, TimeUnit.SECONDS)
                    assertNoErrors()
                }
    }

    @Test
    fun `Emit an error if the first item is not emitted in time`() {
        val scheduler = TestScheduler()
        Observable.interval(10, TimeUnit.SECONDS, scheduler)
                .map { it.toString() }
                .compose(timeoutIfFirstElementDoesNotArriveIn(scheduler, 3, TimeUnit.SECONDS))
                .test().apply {
                    scheduler.advanceTimeBy(100, TimeUnit.SECONDS)
                    assertThat(errors())
                            .describedAs("An error must be emitted if the stream does not emit the first value in time")
                            .isNotEmpty()
                }
    }

    @Test
    fun `No errors if the stream has emitted the first value in time`() {
        val scheduler = TestScheduler()
        Observable.interval(10, TimeUnit.SECONDS, scheduler)
                .startWith(Observable.timer(2, TimeUnit.SECONDS, scheduler))
                .map { it.toString() }
                .compose(timeoutIfFirstElementDoesNotArriveIn(scheduler, 3, TimeUnit.SECONDS))
                .test().apply {
                    scheduler.advanceTimeBy(100, TimeUnit.SECONDS)
                    assertNoErrors()
                }
    }
}