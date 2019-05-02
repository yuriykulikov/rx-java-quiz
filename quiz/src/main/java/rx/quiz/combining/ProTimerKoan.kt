package rx.quiz.combining

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * # Task
 * Create an [Observable] which will emit words from the following phrase:
 * *The quick brown fox jumps over the lazy dog*, distributed over time.
 *
 * You will be given a list of pauses between the words. First word is emitted after the first pause.
 *
 * ## Detailed description
 * For example, given the list 0, 1, 2, 3, 4, 5:
 * * The
 * * 1 second pause
 * * quick
 * * 2 second pause
 * * brown
 * * 3 seconds pause
 * * etc
 *
 * ## Requires
 *
 * * Basic knowledge about [Observable.timer]
 * * Advanced knowledge about Hot and Cold
 * * Expert knowledge about operators
 */
abstract class ProTimerKoan {
    /**
     * @see [ProTimerKoan]
     * @param scheduler use it for your timers
     * @param phrase The quick brown fox jumps over the lazy dog
     * @param pauses list of pauses between words
     */
    abstract fun sayWordsOfTheSentenceWithPausesBetweenThem(scheduler: Scheduler, phrase: String, pauses: List<Long>): Observable<String>

    @Test
    fun `Create an Observable which emits values distributed in time using the given list of pauses`() {
        val scheduler = TestScheduler()
        val phrase = "The quick brown fox jumps over the lazy dog"
        val pauses: List<Long> = phrase.split(" ").map { it.length.toLong() }

        val values = sayWordsOfTheSentenceWithPausesBetweenThem(scheduler, phrase, pauses)
                .timeInterval(scheduler)
                .test()
                .apply {
                    repeat(60) {
                        scheduler.advanceTimeBy(1, TimeUnit.SECONDS)
                    }
                }
                .values()

        assertThat(values.joinToString(" ") { it.value() }).isEqualToIgnoringCase(phrase)

        assertThat(values.map { it.time() / 1000 })
                .apply { describedAs("Delays between emits in seconds") }
                .containsExactlyElementsOf(pauses)
    }
}