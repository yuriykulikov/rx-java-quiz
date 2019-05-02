@file:Suppress("FunctionName")

package rx.quiz.observables

import io.reactivex.Observable
import org.junit.Test
import java.util.function.Supplier

/**
 * # Task
 *
 * Create an eager Observable which emits 3 pre-defined values
 *
 * ## Detailed description
 *
 * Use the given Supplier to get 3 values. Do it eagerly (without waiting for the subscribers).
 * ***You can only use the Supplier to call [Supplier.get] 3 times***
 *
 * Then create and return an Observable which will emit the 3 values and then complete. Test code will subscribe to your
 * Observable multiple times, and every subscriber must get the same set of values.
 *
 * ## Requires
 *
 * * Basic knowledge about [Observable] creation operators
 * * Basic knowledge about Hot and Cold
 */
abstract class BeginnerCreateEagerObservableKoan {
    /**
     * @see [BeginnerCreateEagerObservableKoan]
     */
    abstract fun createEagerObservableFromSupplier(supplier: Supplier<String>): Observable<String>

    @Test
    fun `Use Observable just to create an eager Observable`() {
        val value = "get"
        var calls = 0
        val result = createEagerObservableFromSupplier(Supplier {
            calls++
            "$value-$calls"
        })

        result.test()
                .assertComplete()
                .assertNoErrors()
                .assertValues("get-1", "get-2", "get-3")

        result.test()
                .assertComplete()
                .assertNoErrors()
                .assertValues("get-1", "get-2", "get-3")
    }
}
