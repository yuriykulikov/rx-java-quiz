@file:Suppress("FunctionName")

package de.eso

import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import java.util.function.Supplier

abstract class ReactiveCreationKoans {
    protected abstract fun createFixture(): ReactiveCreationFixture

    @Test
    fun `knows how to create a lazy Observable`() {
        var called = false
        val supplier: Supplier<Int> = Supplier {
            called = true
            20
        }

        val result = createFixture().createLazyObservable(supplier)

        assertThat(called)
                .describedAs("The Single must be lazy, it must not execute before someone subscribes")
                .isFalse()

        result.test()
                .assertComplete()
                .assertNoErrors()
                .assertValue(20)
    }
}
