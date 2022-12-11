fun main() {

    data class Coordinate(val x: Int, val y: Int) {
        operator fun plus(other: Coordinate): Coordinate {
            return Coordinate(x + other.x, y + other.y)
        }
        operator fun minus(other: Coordinate): Coordinate {
            return Coordinate(x - other.x, y - other.y)
        }
        operator fun times(multiplier: Int): Coordinate {
            return Coordinate(x * multiplier, y * multiplier)
        }
        override operator fun equals(other: Any?): Boolean {
            return other is Coordinate && other.x == x && other.y == y
        }
    }

    data class Step(val direction: String, val distance: Int) {
        val coordinate: Coordinate
            get() = when (direction) {
                "U" -> Coordinate(0, 1)
                "D" -> Coordinate(0, -1)
                "L" -> Coordinate(-1, 0)
                "R" -> Coordinate(1, 0)
                else -> throw IllegalArgumentException("Unknown direction: $direction")
            }
        fun coordinates(from: Coordinate): List<Coordinate> {
            return (1..distance)
                .map { from + coordinate * it }
        }
        constructor(string: String) : this(string.substring(0, 1), string.substring(2).toInt())
    }


    fun path(input: List<String>): List<Coordinate> {
        var current = Coordinate(0, 0)
        return listOf(current) + input.map { Step(it) }
            .flatMap {
                val coordinates = it.coordinates(current)
                current = coordinates.last()
                coordinates
            }
    }

    fun followDelta(head: Coordinate, tail: Coordinate): Coordinate {
        val diff = tail - head
        return when {
            diff.x == 2 -> Coordinate(head.x + 1, head.y)
            diff.x == -2 -> Coordinate(head.x - 1, head.y)
            diff.y == 2 -> Coordinate(head.x, head.y + 1)
            diff.y == -2 -> Coordinate(head.x, head.y - 1)
            else -> tail
        }
    }

    fun follow(input: List<Coordinate>): List<Coordinate> {
        val head = input.first()
        var current = head
        return input.map {
            val next = followDelta(it, current)
            current = next
            current
        }
    }

    fun part1(input: List<String>): Int {
        val head = path(input)
        val tail = follow(head)
        return tail.distinct().size
    }

    fun part2(input: List<String>): Int {
        val head = path(input)
        var current = head
        (1..9).forEach {
            val next = follow(current)
            current = next
        }
        println(current.forEach(::println))
        println(current.distinct().size)
        return current.distinct().size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_sample")
    check(part1(testInput) == 13)
    val testInput2 = readInput("Day09_sample2")
    check(part2(testInput2) == 36)

    val realInput = readInput("Day09_input")
    println("Part 1: ${part1(realInput)}")
    println("Part 2: ${part2(testInput)}")
}
