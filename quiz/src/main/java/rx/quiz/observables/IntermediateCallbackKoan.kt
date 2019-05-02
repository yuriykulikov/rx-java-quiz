@file:Suppress("FunctionName")

package rx.quiz.observables

import io.reactivex.Observable
import io.reactivex.functions.Cancellable
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import rx.quiz.observables.IntermediateCallbackKoan.CallbackBasedInterface
import java.util.function.Consumer

/**
 * # Task
 *
 * Create an Observable which uses the given [CallbackBasedInterface] to register for the events.
 *
 * ## Detailed description
 *
 * You should create a lazy [Observable], which will only register a [Consumer] when someone subscribes to the [Observable] you created
 *
 * ## Requires
 *
 * * Advanced knowledge about [Observable.create]
 * * Advanced knowledge about Hot and Cold
 */
abstract class IntermediateCallbackKoan {
    /**
     * Represents a typical callback-based interface, which allow to register callbacks to receive some events.
     */
    interface CallbackBasedInterface {
        /**
         * Register a callback
         * @param callback an event listener
         * @return [Cancellable] which can be used to unregister the callback
         */
        fun registerCallback(callback: Consumer<Int>): Cancellable
    }

    /**
     * @see [IntermediateCallbackKoan]
     * @see [CallbackBasedInterface]
     */
    abstract fun createLazyObservableFromCallback(callbackBasedInterface: CallbackBasedInterface): Observable<Int>

    @Test
    fun `Create a lazy Observable from a callback`() {
        var called = 0
        var cancelled = 0

        val callbackBasedInterface: CallbackBasedInterface = object : CallbackBasedInterface {
            override fun registerCallback(callback: Consumer<Int>): Cancellable {
                called++
                callback.accept(1)
                callback.accept(2)
                callback.accept(3)
                return Cancellable { cancelled++ }
            }
        }
        val result = createLazyObservableFromCallback(callbackBasedInterface)

        assertThat(called)
                .describedAs("The Observable must be lazy, it must not register the callback before someone subscribes")
                .isEqualTo(0)

        (1..3).forEach {
            result.test()
                    .assertNotComplete()
                    .assertNoErrors()
                    .assertValues(1, 2, 3)

            assertThat(called)
                    .describedAs("The supplier must be called $it times after the subscription is done")
                    .isEqualTo(it)
        }
    }

    @Test
    fun `Create a lazy Observable from a callback with cancellation`() {
        var cancelled = 0

        val result = createLazyObservableFromCallback(object : CallbackBasedInterface {
            override fun registerCallback(callback: Consumer<Int>): Cancellable {
                callback.accept(1)
                callback.accept(2)
                callback.accept(3)
                return Cancellable { cancelled++ }
            }
        })

        assertThat(cancelled)
                .describedAs("The Observable must be alive until it is cancelled")
                .isEqualTo(0)
        (1..3).forEach {
            result.test()
                    .assertNotComplete()
                    .assertNoErrors()
                    .assertValues(1, 2, 3)
                    .cancel()

            assertThat(cancelled)
                    .describedAs("The Observable must be cancel the callback registration $it times")
                    .isEqualTo(it)
        }
    }
}
