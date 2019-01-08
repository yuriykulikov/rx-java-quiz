package de.eso

import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import java.util.function.Function

class ReactiveBasicsSolution : ReactiveBasicsKoans(), ReactiveBasicsFixture {
    override fun createFixture(): ReactiveBasicsFixture {
        return this
    }

    override fun sumOfStrings(strings: Observable<String>): Single<Int> {
        return strings.map { it.toInt() }
                .reduce(0) { acc, next -> acc + next }
    }

    override fun sumOfEvenNumbers(numbers: Observable<Int>): Single<Int> {
        return numbers.filter { it.rem(2) == 0 }
                .reduce(0) { acc, next -> acc + next }
    }

    override fun flattenList(input: Observable<Int>, mapper: Function<Int, Observable<Int>>): Observable<Int> {
        return input.flatMap { mapper.apply(it) }
    }

    override fun existingOptionals(input: Observable<Optional<Int>>): Observable<Int> {
        return input.filter { it.isPresent }.map { it.get() }
    }
}