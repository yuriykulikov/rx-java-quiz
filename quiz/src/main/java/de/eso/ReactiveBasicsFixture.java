package de.eso;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;
import org.jetbrains.annotations.NotNull;
import org.junit.Assume;

import java.util.Optional;
import java.util.function.Function;

/**
 * Basics are basic stateless transformation operators:
 * <ul>
 * <li> {@link Observable#filter(Predicate)}</li>
 * <li> {@link Observable#map(io.reactivex.functions.Function)}</li>
 * <li> {@link Observable#flatMap(io.reactivex.functions.Function)}</li>
 * </ul>
 */
public interface ReactiveBasicsFixture {
    /**
     * Calculate the sum of all numbers represented by the given strings
     */
    @NonNull
    default Single<Integer> sumOfStrings(@NonNull Observable<String> strings) {
        Assume.assumeTrue(false);
        return Single.just(0);
    }

    /**
     * Calculate the sum of all EVEN numbers (even means 2, 4, 6, 8, etc) represented by the given integers
     */
    @NotNull
    default Single<Integer> sumOfEvenNumbers(@NotNull Observable<Integer> numbers) {
        Assume.assumeTrue(false);
        return Single.just(0);
    }

    /**
     * You are given a stream and a function. We would like to have all values in one list. Please retain the natural ordering.
     */
    @NotNull
    default Observable<Integer> flattenList(@NotNull Observable<Integer> input, @NonNull Function<Integer, Observable<Integer>> mapper) {
        Assume.assumeTrue(false);
        return Observable.empty();
    }

    /**
     * Extract all values of existing optionals
     */
    @NotNull
    default Observable<Integer> existingOptionals(@NotNull Observable<Optional<Integer>> input) {
        Assume.assumeTrue(false);
        return Observable.empty();
    }
}
