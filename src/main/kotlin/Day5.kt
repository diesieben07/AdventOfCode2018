package de.takeweiland.aoc2018

import java.util.*

fun main(args: Array<String>) {
    part1()
    part2()
}

private fun annihilatePairs(input: String): String {
    if (input.length <= 1) return input

    val toRemove = BitSet(input.length)
    var i = 0
    while (i < input.lastIndex) {
        val a = input[i]
        val b = input[i + 1]
        if (a.toLowerCase() == b.toLowerCase() && a.isLowerCase() != b.isLowerCase()) {
            toRemove.set(i)
            toRemove.set(i+1)
            i += 2
        } else {
            i++
        }
    }

    return input.filterIndexed { index, _ -> !toRemove[index] }
}

private fun fullyAnnihilate(input: String): String {
    var current = input
    do {
        val next = annihilatePairs(current)
        if (next == current) return current
        current = next
    } while (true)
}

private fun part1() {
    val input = input(5)
    val annihilated = fullyAnnihilate(input)
    println(annihilated.length)
}

private fun part2() {
    val input = input(5)
    val all = input.asSequence().map { it.toLowerCase() }.distinct().toList()
    val lengths = all.associateWith { char ->
        val stripped = input.filter { it.toLowerCase() != char }
        fullyAnnihilate(stripped).length
    }
    val shortest = lengths.values.min()
    println(shortest)
}