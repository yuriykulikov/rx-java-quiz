@file:Suppress("FunctionName")

package rx.quiz.basics

import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Test

/**
 * # Task
 * Calculate the sum of all EVEN numbers (even means 2, 4, 6, 8, etc) represented by the given strings
 *
 * ## Detailed description
 * You are given an [Observable] which will terminate after emitting some values. Your [Single] should emit a value
 * when this Observable completes. There are multiple operators this can be used for this (generally n to 1 operators,
 * i.e. Observable -> Single)
 *
 * ## Requires
 *
 * * Basic math skills (odd/even numbers)
 * * Intermediate knowledge about [Observable] events (onComplete)
 * * Intermediate knowledge about operators
 */
abstract class IntermediateOperatorsKoan {
    /**
     * @see [IntermediateOperatorsKoan]
     * @param numbers [Observable] of ints, which will emit some values and terminate
     */
    abstract fun sumOfEvenNumbers(numbers: Observable<String>): Single<Int>

    @Test
    fun `Calculate the sum of even numbers after stream terminates`() {
        val input = Observable.range(0, 101).map { it.toString() }
        val result = sumOfEvenNumbers(input)
        result.test()
                .assertComplete()
                .assertNoErrors()
                .assertValue(2550)
    }
}
