package de.eso;

import io.reactivex.Single;
import org.jetbrains.annotations.NotNull;
import org.junit.Assume;

import java.util.function.Supplier;

public interface ReactiveCreationFixture {
    @NotNull
    default Single<Integer> createLazyObservable(@NotNull Supplier<Integer> supplier) {
        Assume.assumeTrue(false);
        return Single.just(1);
    }
}
