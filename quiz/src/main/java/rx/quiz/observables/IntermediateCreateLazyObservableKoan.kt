@file:Suppress("FunctionName")

package rx.quiz.observables

import io.reactivex.Observable
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import java.util.function.Supplier

/**
 * # Task
 *
 * Create a lazy Observable which uses the given [Supplier.get] to emit 3 values to each new subscriber.
 *
 * ## Detailed description
 *
 * Create an Observable which, **when subscribed to**, calls the method [Supplier.get] 3 times and emits results to the
 * downstream. After emitting 3 values the Observable must complete.
 *
 * ## Requires
 *
 * * Advanced knowledge about creation operators
 * * Advanced knowledge about Hot and Cold
 */
abstract class IntermediateCreateLazyObservableKoan {
    /** @see [IntermediateCreateLazyObservableKoan] */
    abstract fun createLazyObservableFromSupplier(supplier: Supplier<String>): Observable<String>

    @Test
    fun `Create a lazy Observable using the given supplier`() {
        var state = "Before subscribe"
        var calls = 0
        val result = createLazyObservableFromSupplier(Supplier {
            assertThat(state).describedAs("Supplier may be only accessed after subscription event").isEqualTo("Subscribed")
            calls++
            "$calls"
        })

        state = "Subscribed"
        result.test()
                .assertComplete()
                .assertNoErrors()
                .assertValues("1", "2", "3")

        result.test()
                .assertComplete()
                .assertNoErrors()
                .assertValues("4", "5", "6")
    }
}
