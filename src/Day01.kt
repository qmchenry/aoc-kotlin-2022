fun main() {

    fun sums(input: List<String>): List<Int> {
        return input
            .flatMapIndexed { index, x ->
                when {
                    index == 0 || index == input.lastIndex -> listOf(index)
                    x.isEmpty() -> listOf(index - 1, index + 1)
                    else -> emptyList()
                }
            }
            .windowed(size = 2, step = 2) { (from, to) ->
                input.slice(from..to)
                    .map { it.toInt() }
            }
            .map { it.sum() }
    }

    fun part1(input: List<String>): Int {
        return sums(input).maxOf { it }
    }

    fun part2(input: List<String>): Int {
        return sums(input)
            .sorted()
            .takeLast(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_sample")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val realInput = readInput("Day01_test")
    println("Part 1: ${part1(realInput)}")
    println("Part 2: ${part2(realInput)}")
}
