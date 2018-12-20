package de.takeweiland.aoc2018

import java.io.BufferedReader

private inline fun <T> parseInput(day: Int, handler: (BufferedReader) -> T): T {
    return Thread.currentThread().contextClassLoader.getResource("day$day.txt")
        .openStream().bufferedReader().use(handler)
}

fun input(day: Int): String {
    return parseInput(day) { it.readText() }
}

fun inputLines(day: Int): List<String> {
    return parseInput(day) { it.readLines() }
}