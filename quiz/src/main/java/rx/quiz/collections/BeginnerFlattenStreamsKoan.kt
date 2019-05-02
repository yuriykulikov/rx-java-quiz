@file:Suppress("FunctionName")

package rx.quiz.collections

import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test

/**
 * # Task
 *
 * You are given a **list of lists**. We would like to have all values in one list. Please retain the natural ordering.
 * Avoid using for-loops - use one of the streaming libraries (e.g. [java.util.stream.Stream]) instead.
 *
 * ## Detailed description
 *
 * Given a list of lists: List { List {a, b, c], List {1, 2, 3}, List {+, -, /} }
 *
 * Create a new list containing all values: List {a, b, c, 1, 2, 3, +, -, /}
 */
abstract class BeginnerFlattenStreamsKoan {
    /**
     * @see [BeginnerFlattenStreamsKoan]
     */
    abstract fun flattenList(input: List<List<Int>>): List<Int>

    @Test
    fun `Use stream operators to flatten the list`() {
        val input = (0..3).toList().map { (0..3).toList() }
        val result = flattenList(input)
        assertThat(result).containsExactly(0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3)
    }
}
