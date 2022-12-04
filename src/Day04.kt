fun main() {

    fun rangeToSet(range: String): Set<Int> {
        val (from, to) = range.split("-").map { it.toInt() }
        return (from..to).toSet()
    }

    fun rangePairs(pair: String): Pair<Set<Int>, Set<Int>> {
        val (left, right) = pair.split(",")
        return Pair(rangeToSet(left), rangeToSet(right))
    }

    fun isOneFullyOverlapped(left: Set<Int>, right: Set<Int>): Boolean {
        return left.containsAll(right) || right.containsAll(left)
    }

    fun arePartiallyOverlapped(left: Set<Int>, right: Set<Int>): Boolean {
        return left.intersect(right).isNotEmpty()
    }

    fun part1(input: List<String>): Int {
        return input
            .map { rangePairs(it) }
            .count { (left, right) -> isOneFullyOverlapped(left, right) }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { rangePairs(it) }
            .count { (left, right) -> arePartiallyOverlapped(left, right) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_sample")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val realInput = readInput("Day04_input")
    println("Part 1: ${part1(realInput)}")
    println("Part 2: ${part2(realInput)}")
}
