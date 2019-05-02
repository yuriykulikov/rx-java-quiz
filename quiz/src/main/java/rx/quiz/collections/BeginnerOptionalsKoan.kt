@file:Suppress("FunctionName")

package rx.quiz.collections

import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import java.util.*

/**
 * # Task
 * Given a [List]<[Optional]>, create a new list which contains all present values from the first list.
 * Avoid using for-loops - use one of the streaming libraries (e.g. [java.util.stream.Stream]) instead.
 *
 * ## Requires
 *
 * * Basic knowledge about [Optional]
 * * Basic knowledge about Java streams
 */
abstract class BeginnerOptionalsKoan {
    /**
     * @see [BeginnerOptionalsKoan]
     */
    abstract fun existingOptionals(input: List<Optional<Int>>): List<Int>

    @Test
    fun `Use stream operators to read from optional`() {
        val input = (0..100).toList().map { it: Int ->
            if (it.rem(2) == 0) Optional.of(it)
            else Optional.empty()
        }
        val result = existingOptionals(input)
        val expected = 0..100 step 2
        assertThat(result).isEqualTo(expected.toList())
    }
}
