package de.eso;

import io.reactivex.annotations.NonNull;
import org.jetbrains.annotations.NotNull;
import org.junit.Assume;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface StreamsKoanFixture {
    /**
     * Calculate the sum of all numbers represented by the given strings
     */
    @NonNull
    default int sumOfStrings(@NonNull List<String> strings) {
        Assume.assumeTrue(false);
        return 0;
    }

    /**
     * Calculate the sum of all EVEN numbers (even means 2, 4, 6, 8, etc) represented by the given integers
     */
    @NotNull
    default int sumOfEvenNumbers(@NotNull List<Integer> numbers) {
        Assume.assumeTrue(false);
        return 0;
    }

    /**
     * You are given a list of lists. We would like to have all values in one list. Please retain the natural ordering.
     */
    @NotNull
    default List<Integer> flattenList(@NotNull List<List<Integer>> input) {
        Assume.assumeTrue(false);
        return new ArrayList<>();
    }

    /**
     * You are given a String, which contains letters and spaces. In each word please convert all characters to lower case
     * and remove all duplicate characters in one word. Then remove all duplicate words and join by the space character.
     */
    @NotNull
    default String removeDuplicatesInString(@NotNull String input) {
        Assume.assumeTrue(false);
        return "";
    }

    /**
     * You are given a String, which contains letters and spaces. In each word please convert all characters to lower case
     * and remove all duplicate characters in one word. Do not remove duplicate words, instead sort the words first by
     * the length (ascending, smaller words come first) and then alphabetically. Join them by the space character.
     */
    @NotNull
    default String sortStringWithMultipleCriteria(@NotNull String input) {
        Assume.assumeTrue(false);
        return "";
    }

    /**
     * Extract all values of existing optionals
     */
    @NotNull
    default List<Integer> existingOptionals(@NotNull List<Optional<Integer>> input) {
        Assume.assumeTrue(false);
        return new ArrayList<>();
    }
}
