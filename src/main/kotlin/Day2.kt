package de.takeweiland.aoc2018

fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    val lines = inputLines(2)
    val counts = lines.map { line ->
        line.groupingBy { it }.eachCount()
    }

    val twice = counts.count { it.containsValue(2) }
    val thrice = counts.count { it.containsValue(3) }
    println(twice * thrice)
}

private fun part2() {
    val lines = inputLines(2)
    outer@for (a in lines) {
        for (b in lines) {
            if (a == b) continue

            if (a.indices.count { a[it] != b[it] } == 1) {
                println(a.filterIndexed { index, ac -> b[index] == ac })
                break@outer
            }
        }
    }
}