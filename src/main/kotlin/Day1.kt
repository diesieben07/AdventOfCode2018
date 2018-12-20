package de.takeweiland.aoc2018

fun main(args: Array<String>) {
    part1()
    part2()
}

private fun part1() {
    val lines = inputLines(1)
    val result = lines.sumBy { it.toInt() }
    println(result)
}

private fun part2() {
    val lines = inputLines(1)
    val seen = HashSet<Int>()
    var curr = 0
    infinity@while (true) {
        for (c in lines) {
            if (!seen.add(curr)) {
                println(curr)
                break@infinity
            }
            curr += c.toInt()
        }
    }
}