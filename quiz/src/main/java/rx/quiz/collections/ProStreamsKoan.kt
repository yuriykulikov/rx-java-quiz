@file:Suppress("FunctionName")

package rx.quiz.collections

import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test

/**
 * # Task
 * You are given a String, which contains letters and spaces. In each word please convert all characters to lower case
 * and remove all duplicate characters in one word. Do not remove duplicate words, instead sort the words first by
 * the length (ascending, smaller words come first) and then alphabetically. Join them by the space character.
 */
abstract class ProStreamsKoan {
    /**
     * @see [ProStreamsKoan]
     */
    abstract fun sortStringWithMultipleCriteria(input: String): String

    @Test
    fun `Combine multiple operators to remove duplicates in a string and sort the results`() {
        val input = "The Observer pattern done right ReactiveX is a combination of the best ideas from the Observer pattern the Iterator pattern and functional programming"

        val result = sortStringWithMultipleCriteria(input)
        assertThat(result).isEqualTo("a is of and the the the the best done from ideas right iterao observ observ patern patern patern combinat progamin reactivx functioal")
    }
}
