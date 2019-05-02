package rx.quiz.observables

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import org.junit.Test

/**
 * # Task
 * Using **only [Observable.create]** implement [Observable.combineLatest] **for onNext emits only** (no onComplete/error handling).
 *
 * ## Detailed description
 *
 * You can use the documentation for [Observable.combineLatest], but not the operator itself. Please implement
 * an [Observable] which behaves exactly the same for onNext emits.
 *
 * ## Requires
 *
 * * Basic knowledge about [Observable.combineLatest]
 * * Advanced knowledge about [Observable] creation
 */
abstract class IntermediateCreateCombineLatestKoan {
    /**
     * @see [IntermediateCreateCombineLatestKoan]
     */
    abstract fun <T, D, I> combineLatest(first: Observable<T>, second: Observable<D>, combiner: BiFunction<T, D, I>): Observable<I>

    @Test
    fun `Implement combineLatest and make it emit values the same way as real combineLatest does`() {
        val a = PublishSubject.create<String>()
        val b = PublishSubject.create<String>()

        val combiner = io.reactivex.functions.BiFunction { x: String, y: String ->
            x + y
        }

        var original = io.reactivex.Observable.combineLatest(a.hide(), b.hide(), combiner).test()

        combineLatest(a.hide(), b.hide(), combiner).test().apply {
            a.onNext("A")
            b.onNext("1")
            b.onNext("2")
            a.onNext("B")
            b.onNext("3")
            a.onNext("C")
            a.onNext("D")
            assertValues("A1", "A2", "B2", "B3", "C3", "D3")
            original.assertValues("A1", "A2", "B2", "B3", "C3", "D3")
            b.onNext("4")
            assertValues("A1", "A2", "B2", "B3", "C3", "D3", "D4")
            original.assertValues("A1", "A2", "B2", "B3", "C3", "D3", "D4")
        }
    }
}