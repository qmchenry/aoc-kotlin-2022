fun main() {

    fun loadInitialPositions(input: List<String>): List<String> {
        val asRead = input
            .takeWhile { it.isNotBlank() }
            .filter { it.contains("[") }
            .map {
                it.chunked(4)
                    .map { it[1] }
            }
        val longest = asRead.maxOfOrNull { it.size } ?: 0
        val transposed = (0 until longest)
            .map { i ->
                asRead.map { it.getOrNull(i) ?: "" }.joinToString("")
            }
            .map { it.reversed().trim() }
        return transposed
    }

    fun rearrange(stacks: List<String>, input: List<String>, multiLift: Boolean = false): List<String> {
        val localStacks = stacks.toMutableList()
        val movements = input
            .filter { it.startsWith("move") }
            .map { it.split(" ").mapNotNull { it.toIntOrNull() } }
        movements.forEach { (qty, from, to) ->
            val toMove = localStacks[from - 1].takeLast(qty)
            localStacks[to - 1] =
                (localStacks.elementAtOrNull(to - 1) ?: " ") + if (multiLift) toMove else toMove.reversed()
            localStacks[from - 1] = localStacks[from - 1].dropLast(qty)
        }
        return localStacks
    }

    fun rearrange(input: List<String>, multiLift: Boolean): String {
        val stacks = loadInitialPositions(input)
        val rearranged = rearrange(stacks, input, multiLift = multiLift)
        return rearranged.map { it.takeLast(1) }.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_sample")
    check(rearrange(testInput, false) == "CMZ")
    check(rearrange(testInput, true) == "MCD")

    val realInput = readInput("Day05_input")
    println("Part 1: ${rearrange(realInput, false)}")
    println("Part 2: ${rearrange(realInput, true)}")
}
