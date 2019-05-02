@file:Suppress("FunctionName")

package rx.quiz.observables

import io.reactivex.Single
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import java.util.function.Supplier

/**
 * # Task
 *
 * Create a single which uses the given supplier to emit a value if a subscriber subscribes
 *
 * ## Detailed description
 *
 * You should create a lazy [Single], which will only use the given [Supplier] when someone subscribes to the Single you created
 *
 * ## Requires
 *
 * * Advanced knowledge about [Single] creation operators
 * * Advanced knowledge about Hot and Cold
 */
abstract class BeginnerCreateLazySingleKoan {
    /**
     * @see [BeginnerCreateLazySingleKoan]
     */
    abstract fun createLazySingle(supplier: Supplier<Int>): Single<Int>

    @Test
    fun `Create a lazy Single`() {
        var called = 0
        val supplier: Supplier<Int> = Supplier {
            called++
            20
        }

        val result = createLazySingle(supplier)

        assertThat(called)
                .describedAs("The Single must be lazy, it must not execute before someone subscribes")
                .isEqualTo(0)

        (1..3).forEach {
            result.test()
                    .assertComplete()
                    .assertNoErrors()
                    .assertValue(20)

            assertThat(called)
                    .describedAs("Supplier must be called on each subscription. Expected: $it calls")
                    .isEqualTo(it)
        }
    }
}
