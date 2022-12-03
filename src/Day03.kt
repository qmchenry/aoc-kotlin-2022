fun main() {

    fun bagOverlap(contents: String): Char {
        val (first, second) = contents.chunked(contents.length / 2)
        return first.toSet().intersect(second.toSet()).first()
    }

    fun priority(item: Char): Int {
        return when (item) {
            in 'a'..'z' -> item - 'a' + 1
            in 'A'..'Z' -> item - 'A' + 27
            else -> throw IllegalArgumentException("Unknown item: $item")
        }
    }

    fun part1(input: List<String>): Int {
        return input
            .map { bagOverlap(it) }
            .map { priority(it) }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input
            .chunked(3)
            .map { it.map { it.toSet() } }
            .map { (a, b, c) -> a.intersect(b).intersect(c).first() }
            .map { priority(it) }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_sample")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val realInput = readInput("Day03_input")
    println("Part 1: ${part1(realInput)}")
    println("Part 2: ${part2(realInput)}")
}
