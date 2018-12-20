package de.takeweiland.aoc2018

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates

fun main(args: Array<String>) {
    part1()
}

private data class Event(val dateTime: LocalDateTime, val type: EventType)

private sealed class EventType {

    data class BeginShift(val id: Int) : EventType()

    object WakeUp : EventType() {
        override fun toString() = "WakeUp"
    }

    object FallAsleep : EventType() {
        override fun toString() = "FallAsleep"
    }

}

private val regex = Regex("""^\[([^\]]+)] (?:(?:Guard #([0-9]+) begins shift)|(falls asleep)|(wakes up))""")
private val dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

private fun parse(line: String): Event {
    val (datetime, guardId, sleep, wake) = regex.matchEntire(line)?.destructured ?: throw IllegalArgumentException()
    val type = when {
        guardId.isNotBlank() -> EventType.BeginShift(guardId.toInt())
        wake.isNotBlank() -> EventType.WakeUp
        sleep.isNotBlank() -> EventType.FallAsleep
        else -> throw IllegalArgumentException()
    }
    return Event(LocalDateTime.parse(datetime, dateTimeFormat), type)
}

private data class AsleepTime(val guardId: Int, val date: LocalDate, val minute: Int)

private fun part1() {
    val events = inputLines(4).map { parse(it) }.sortedBy { it.dateTime }

    val sleepyTimes = ArrayList<AsleepTime>()
    var currentGuardId: Int by Delegates.notNull()
    var fallAsleepTime: Int by Delegates.notNull()
    for (event in events) {
        when (event.type) {
            is EventType.BeginShift -> {
                currentGuardId = event.type.id
            }
            is EventType.FallAsleep -> {
                fallAsleepTime = event.dateTime.minute
            }
            is EventType.WakeUp -> {
                val wakeUpTime = event.dateTime.minute
                (fallAsleepTime until wakeUpTime).mapTo(sleepyTimes) { minute ->
                    AsleepTime(currentGuardId, event.dateTime.toLocalDate(), minute)
                }
            }
        }
    }

    val byGuard = sleepyTimes.groupBy { it.guardId }

    val guardMostAsleep = byGuard.maxBy { it.value.size }?.key ?: throw IllegalStateException()
    val mostAsleepMinute = byGuard[guardMostAsleep]!!
        .groupingBy { it.minute }.eachCount()
        .maxBy { it.value }!!
        .key

    println(guardMostAsleep * mostAsleepMinute)

    val maxGuardAndMinute = sleepyTimes.groupingBy { Pair(it.guardId, it.minute) }
        .eachCount()
        .maxBy { it.value }!!
        .key

    println(maxGuardAndMinute.first * maxGuardAndMinute.second)
}