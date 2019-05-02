@file:Suppress("FunctionName")

package rx.quiz.collections

import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test

/**
 * # Task
 * Calculate the sum of all EVEN numbers (even means 2, 4, 6, 8, etc) represented by the given integers.
 * Avoid using for-loops - use one of the streaming libraries (e.g. [java.util.stream.Stream]) instead.
 *
 * ## Hint
 * If you are having trouble with modifying a variable outside of a lambda, you are doing it wrong! This is meant to be
 * implemented using Java or Vavr streams.
 *
 * ## Requires
 *
 * * Basic math skills (odd/even numbers)
 * * Basic knowledge about [java.util.stream.Stream] operators
 * * Basic knowledge about [java.util.stream.Stream] terminal operators
 * * Basic knowledge about [java.util.stream.IntStream]
 */
abstract class BeginnerTerminalOperationsKoan {
    /**
     * @see [BeginnerTerminalOperationsKoan]
     */
    abstract fun sumOfEvenNumbers(numbers: List<String>): Int

    @Test
    fun `Use stream operators to find a sum of even numbers`() {
        val input = (0..100).toList().map { it.toString() }
        val result = sumOfEvenNumbers(input)
        assertThat(result).isEqualTo(2550)
    }
}
