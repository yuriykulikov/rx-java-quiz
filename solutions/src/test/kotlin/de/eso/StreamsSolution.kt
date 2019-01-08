package de.eso

import java.util.*

class StreamsSolution : StreamsKoans(), StreamsKoanFixture {
    override fun createFixture(): StreamsKoanFixture {
        return this
    }

    override fun sumOfStrings(strings: MutableList<String>): Int {
        return strings.map { it.toInt() }.sum()
    }

    override fun sumOfEvenNumbers(numbers: MutableList<Int>): Int {
        return numbers.filter { it.rem(2) == 0 }.sum()
    }

    override fun flattenList(input: MutableList<MutableList<Int>>): List<Int> {
        return input.flatten()
    }

    override fun removeDuplicatesInString(input: String): String {
        return input
                .split(" ").map { word -> word.toLowerCase().toCharArray().distinct().joinToString("") }
                .distinct()
                .joinToString(" ")
    }

    override fun sortStringWithMultipleCriteria(input: String): String {
        val comparator = compareBy<String> { it.length }.thenBy { it }
        return input
                .split(" ").map { word -> word.toLowerCase().toCharArray().distinct().joinToString("") }
                .sortedWith(comparator)
                .joinToString(" ")
    }

    override fun existingOptionals(input: MutableList<Optional<Int>>): List<Int> {
        return input.mapNotNull { it.orElse(null) }
    }
}