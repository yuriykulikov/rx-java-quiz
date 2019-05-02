@file:Suppress("FunctionName")

package rx.quiz.collections

import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test

/**
 * # Task
 * Convert each character to lowercase, remove duplicate letters **in words** and then remove **duplicate words**
 * Avoid using for-loops - use one of the streaming libraries (e.g. [java.util.stream.Stream]) instead.
 *
 * ## Detailed description
 *
 * You are given a String, which contains letters and spaces. Do following transformations:
 * * In each word please convert **all** characters to lower case
 * * Remove **all** duplicate characters **within** one word
 * * Remove all duplicate words
 * * Join remaining words by the space character
 *
 */
abstract class IntermediateStringsKoan {
    /**
     * @see [IntermediateStringsKoan]
     */
    abstract fun removeDuplicatesInString(input: String): String

    @Test
    fun `Combine multiple operators to remove duplicates in a string`() {
        val input = "The Observer pattern done right ReactiveX is a combination of the best ideas from the Observer pattern the Iterator pattern and functional programming"

        val result = removeDuplicatesInString(input)
        assertThat(result).isEqualTo("the observ patern done right reactivx is a combinat of best ideas from iterao and functioal progamin")
    }
}
