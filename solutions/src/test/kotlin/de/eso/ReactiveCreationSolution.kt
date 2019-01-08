package de.eso

import io.reactivex.Single
import java.util.function.Supplier

class ReactiveCreationSolution : ReactiveCreationKoans(), ReactiveCreationFixture {
    override fun createFixture(): ReactiveCreationFixture {
        return this
    }

    override fun createLazyObservable(supplier: Supplier<Int>): Single<Int> {
        return Single.fromCallable { supplier.get() }
    }
}