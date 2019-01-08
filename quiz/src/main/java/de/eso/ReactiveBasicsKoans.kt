@file:Suppress("FunctionName")

package de.eso

import io.reactivex.Observable
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import java.util.*

abstract class ReactiveBasicsKoans {
    @Test
    fun `knows how to map`() {
        val input = Observable.just("1", "2", "3", "4")
        val result = createFixture().sumOfStrings(input)
        result.test()
                .assertComplete()
                .assertNoErrors()
                .assertValue(10)
    }

    @Test
    fun `knows how to filter`() {
        val input = Observable.range(0, 102)
        val result = createFixture().sumOfEvenNumbers(input)
        result.test()
                .assertComplete()
                .assertNoErrors()
                .assertValue(2550)
    }

    @Test
    fun `knows how to read from optional`() {
        val input = Observable.range(0, 102).map {
            if (it.rem(2) == 0) Optional.of(it)
            else Optional.empty()
        }

        val result = createFixture().existingOptionals(input)
        val expected = 0..100 step 2
        assertThat(result.test()
                .assertComplete()
                .assertNoErrors()
                .values()).containsExactlyElementsOf(expected.toList())
    }

    @Test
    fun `Knows how to use flatMap`() {
        val input = Observable.range(0, 4)
        val result = createFixture().flattenList(input) {
            Observable.range(0, 4)
        }
        assertThat(result.test()
                .assertComplete()
                .assertNoErrors()
                .values()).containsExactly(0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3)
    }

    protected abstract fun createFixture(): ReactiveBasicsFixture
}
