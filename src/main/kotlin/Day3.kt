package de.takeweiland.aoc2018

fun main(args: Array<String>) {
    val claims = inputLines(3).map { parse(it) }.asSequence()

    val pointCounts = claims
        .flatMap { claim -> claim.allPoints().map { point -> Pair(claim.id, point) } }
        .groupingBy { it.second }
        .eachCount()

    println(pointCounts.values.count { it >= 2 })

    val claimWithNoOverlaps = claims.single {
        it.allPoints().all { point -> (pointCounts[point] ?: 0) <= 1 }
    }
    println(claimWithNoOverlaps.id)
}

private data class Claim(val id: Int, val x: Int, val y: Int, val width: Int, val height: Int)
private data class Point(val x: Int, val y: Int)

private val regex = Regex("""^#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)$""")

private fun parse(line: String): Claim {
    val (id, x, y, width, height) = regex.matchEntire(line)?.destructured ?: throw IllegalArgumentException()
    return Claim(id.toInt(), x.toInt(), y.toInt(), width.toInt(), height.toInt())
}

private fun Claim.allPoints(): Sequence<Point> {
    return (x until (x + width)).asSequence().flatMap { xx ->
        (y until y + height).asSequence().map { yy -> Point(xx, yy) }
    }
}