@file:Suppress("FunctionName")

package rx.quiz.basics

import io.reactivex.Observable
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import java.util.*

/**
 * # Task
 *
 * Given a stream of [Optional]s, create a new stream of present values
 *
 * ## Detailed description
 *
 * Emit all values of all existing Optionals in the incoming stream
 *
 * ## Requires
 *
 * * Basic knowledge about [Observable] operators
 * * Basic knowledge about [Optional]
 */
abstract class BeginnerOperatorsKoan {
    /**
     * @see [BeginnerOperatorsKoan]
     * @param input [Observable] of [Optional] numbers
     */
    abstract fun allowOnlyExistingOptionalsThrough(input: Observable<Optional<Int>>): Observable<Int>

    @Test
    fun `Use operators to allow only existing numbers through`() {
        val input = Observable.range(0, 102).map {
            if (it.rem(2) == 0) Optional.of(it)
            else Optional.empty()
        }

        val result = allowOnlyExistingOptionalsThrough(input)
        val expected = 0..100 step 2
        assertThat(result.test()
                .assertComplete()
                .assertNoErrors()
                .values())
                .containsExactlyElementsOf(expected.toList())
    }
}
