package rx.quiz.observables

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import java.io.IOException

/**
 * # Task
 * Using only [Observable.create] implement [Observable.combineLatest]
 *
 * ## Detailed description
 *
 * You can use the documentation for [Observable.combineLatest], but not the operator itself. Please implement
 * an [Observable] which behaves exactly the same.
 *
 * ## Requires
 *
 * * Advanced knowledge about [Observable.combineLatest]
 * * Expert knowledge about [Observable] creation
 */
abstract class ProCreateCombineLatestKoan {
    /**
     * @see [ProCreateCombineLatestKoan]
     */
    abstract fun <T, D, I> combineLatest(first: Observable<T>, second: Observable<D>, combiner: BiFunction<T, D, I>): Observable<I>

    @Test
    fun `Implement combineLatest and make it emit values the same way as real combineLatest does`() {
        val a = PublishSubject.create<String>()
        val b = PublishSubject.create<String>()

        val combiner = io.reactivex.functions.BiFunction { x: String, y: String ->
            x + y
        }

        var a2: Observable<String> = a.hide()
        var b2: Observable<String> = b.hide()
        var original = io.reactivex.Observable.combineLatest(a2, b2, combiner).test()

        combineLatest(a2, b2, combiner).test().apply {
            a.onNext("A")
            b.onNext("1")
            b.onNext("2")
            a.onNext("B")
            b.onNext("3")
            a.onNext("C")
            a.onNext("D")
            assertValues("A1", "A2", "B2", "B3", "C3", "D3")
            original.assertValues("A1", "A2", "B2", "B3", "C3", "D3")
            a.onComplete()
            b.onNext("4")
            assertValues("A1", "A2", "B2", "B3", "C3", "D3", "D4")
            original.assertValues("A1", "A2", "B2", "B3", "C3", "D3", "D4")
        }
    }

    @Test
    fun `Implement combineLatest and make terminate the same way as real combineLatest does`() {
        val a = PublishSubject.create<String>()
        val b = PublishSubject.create<String>()

        val combiner = io.reactivex.functions.BiFunction { x: String, y: String ->
            x + y
        }

        var a2: Observable<String> = a.hide()
        var b2: Observable<String> = b.hide()
        var original = io.reactivex.Observable.combineLatest(a2, b2, combiner).test()

        combineLatest(a2, b2, combiner).test().apply {
            a.onNext("A")
            b.onNext("1")
            b.onNext("2")
            a.onNext("B")
            a.onComplete()
            assertNotComplete()
            b.onNext("4")
            assertValues("A1", "A2", "B2", "B4")
            original.assertValues("A1", "A2", "B2", "B4")

            b.onComplete()
            original.assertComplete()
            assertComplete()
        }
    }

    @Test
    fun `Implement combineLatest and make it error same way as real combineLatest does`() {
        val a = PublishSubject.create<String>()
        val b = PublishSubject.create<String>()

        val combiner = io.reactivex.functions.BiFunction { x: String, y: String ->
            x + y
        }

        var a2: Observable<String> = a.hide()
        var b2: Observable<String> = b.hide()
        var original = io.reactivex.Observable.combineLatest(a2, b2, combiner).test()

        combineLatest(a2, b2, combiner).test().apply {
            a.onNext("A")
            b.onNext("1")
            b.onNext("2")
            b.onError(IOException("out of service"))
            original.assertError(IOException::class.java)
            assertError(IOException::class.java)
        }
    }

    @Test
    fun `Implement combineLatest and make it clean up same way as real combineLatest does`() {
        val a: PublishSubject<String> = PublishSubject.create<String>()
        val b: PublishSubject<String> = PublishSubject.create<String>()

        val combiner = io.reactivex.functions.BiFunction { x: String, y: String ->
            x + y
        }

        combineLatest(a.hide(), b.hide(), combiner)
                .apply {
                    assertThat(a.hasObservers())
                            .describedAs("Must not subscribe to upstream before itself is subscribed")
                            .isFalse()
                }
                .test()
                .apply {
                    assertThat(a.hasObservers())
                            .describedAs("Must be subscribed")
                            .isTrue()
                    assertThat(b.hasObservers())
                            .describedAs("Must be subscribed")
                            .isTrue()
                    a.onNext("A")
                    b.onNext("1")
                    b.onNext("2")
                    cancel()
                    assertThat(a.hasObservers())
                            .describedAs("Must be disposed (no subscriptions)")
                            .isFalse()
                    assertThat(b.hasObservers())
                            .describedAs("Must be disposed (no subscriptions)")
                            .isFalse()
                }
    }
}