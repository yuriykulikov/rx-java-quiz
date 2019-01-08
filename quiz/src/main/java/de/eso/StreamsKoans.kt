@file:Suppress("FunctionName")

package de.eso

import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import java.util.*

abstract class StreamsKoans {
    @Test
    fun `knows how to map`() {
        val input = listOf("1", "2", "3", "4")
        val result = createFixture().sumOfStrings(input)
        assertThat(result).isEqualTo(10)
    }

    @Test
    fun `knows how to filter`() {
        val input = (0..100).toList()
        val result = createFixture().sumOfEvenNumbers(input)
        assertThat(result).isEqualTo(2550)
    }

    @Test
    fun `knows how to read from optional`() {
        val input = (0..100).toList().map { it: Int ->
            if (it.rem(2) == 0) Optional.of(it)
            else Optional.empty()
        }
        val result = createFixture().existingOptionals(input)
        val expected = 0..100 step 2
        assertThat(result).isEqualTo(expected.toList())
    }

    @Test
    fun `Knows how to use flatMap`() {
        val input = (0..3).toList().map { (0..3).toList() }
        val result = createFixture().flattenList(input)
        assertThat(result).containsExactly(0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3)
    }

    @Test
    fun `Knows how to combine multiple operators - advanced`() {
        val input = "The Observer pattern done right ReactiveX is a combination of the best ideas from the Observer pattern the Iterator pattern and functional programming"

        val result = createFixture().removeDuplicatesInString(input)
        assertThat(result).isEqualTo("the observ patern done right reactivx is a combinat of best ideas from iterao and functioal progamin")
    }

    @Test
    fun `Knows how to combine multiple operators - very advanced`() {
        val input = "The Observer pattern done right ReactiveX is a combination of the best ideas from the Observer pattern the Iterator pattern and functional programming"

        val result = createFixture().sortStringWithMultipleCriteria(input)
        assertThat(result).isEqualTo("a is of and the the the the best done from ideas right iterao observ observ patern patern patern combinat progamin reactivx functioal")
    }

    protected abstract fun createFixture(): StreamsKoanFixture
}
