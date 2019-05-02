package rx.quiz.basics

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.junit.Test

/**
 * # Task
 *
 * Create an [ObservableTransformer], which tokenizes the input Observable of [Char] and emits tokenized values.
 *
 * ## Detailed description
 *
 * The sequence you will get contains:
 * * LocationApi followed by the point, which represent the amount of letters which will follow
 * * Letters, exactly as many as specified previously
 *
 * ## Error handling
 * **It is possible, that the incoming string does not fulfill the contract. Emit an IllegalArgumentException in this case**
 *
 * ## Example:
 * 3.The5.quick5.brown3.fox5.jumps4.over3.the4.lazy3.dog
 * 3. means that 3 letters follow: "The", then comes 5. followed by "quick" and so on
 *
 * ## Requires
 *
 * * Expert knowledge about operator composition
 * * Expert stateful programming skills
 * * Expert task understanding
 */
abstract class ProStatefulScanKoan {
    /** @see [ProStatefulScanKoan] */
    protected abstract fun createStringTokenizer(): ObservableTransformer<Char, Result>

    /**
     * @param length length of the segment excluding the metadata part
     * @param value the payload value
     */
    data class Result(val length: Int, val value: String)

    @Test
    fun `Tokenize the sequence as described in the documentation`() {
        val s = "3.The5.quick5.brown3.fox5.jumps4.over3.the4.lazy3.dog"

        val test = Observable.fromIterable(s.toCharArray().toList())
                .compose(createStringTokenizer())
                .test()
        "The quick brown fox jumps over the lazy dog"
                .split(" ")
                .forEachIndexed { index, word -> test.assertValueAt(index, Result(word.length, word)) }

        test.assertValueCount(9)
    }

    @Test
    fun `Emit an IllegalArgumentException if the sequence is malformed`() {
        val s = "3x.bla4.test2.it"
        Observable.fromIterable(s.toCharArray().toList())
                .compose(createStringTokenizer())
                .test().assertError(IllegalArgumentException::class.java)
    }
}