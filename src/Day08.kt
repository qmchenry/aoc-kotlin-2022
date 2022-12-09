fun main() {

    fun condensedString(map: List<List<Int>>): String {
        return map.map { it.joinToString(" ") }.joinToString("\n")
    }

    fun condensedString(map: List<List<Boolean>>): String {
        return map.map {
            it.map { if (it) "#" else "." }.joinToString("")
        }.joinToString("\n")
    }

    fun buildMap(input: List<String>): List<List<Int>> {
        return input.map {
            it.map { char -> char.digitToInt() }
        }
    }

    fun isHidden(input: List<Int>): List<Boolean> {
        var tallest = input.first()
        return input.map {
            if (it > tallest) {
                tallest = it
                false
            } else {
                true
            }
        }
    }

    fun isHiddenBidirectionally(input: List<Int>): List<Boolean> {
        val forward = isHidden(input)
        val backward = isHidden(input.reversed()).reversed()
        return forward.zip(backward).map { (f, b) -> f && b }
    }

    fun visibility(input: List<Int>): Int {
        val first = input.first()
        val lineOfSight = input.drop(1)
        val keepIndex = lineOfSight.indexOfFirst { first <= it }
        val keep = if (keepIndex == -1) lineOfSight.size else keepIndex + 1
        val trees = input.drop(1).take(keep)
        return trees.count()
    }

    fun visibilityScore(input: List<Int>): List<Int> {
        return input.mapIndexed { index, _ ->
            visibility(input.drop(index))
        }
    }

    fun visibilityScoreBidirectional(input: List<Int>): List<Int> {
        val forward = visibilityScore(input)
        val backward = visibilityScore(input.reversed()).reversed()
        return forward.zip(backward).map { (f, b) -> f * b }
    }

    fun <E> transpose(input: List<List<E>>): List<List<E>> {
        fun <E> List<E>.head(): E = this.first()
        fun <E> List<E>.tail(): List<E> = this.takeLast(this.size - 1)
        fun <E> E.append(xs: List<E>): List<E> = listOf(this).plus(xs)

        input.filter { it.isNotEmpty() }.let { ys ->
            return when (ys.isNotEmpty()) {
                true -> ys.map { it.head() }.append(transpose(ys.map { it.tail() }))
                else -> emptyList()
            }
        }
    }

    fun part1(input: List<String>): Int {
        val map = buildMap(input)
        val rowWise = map.map { isHiddenBidirectionally(it) }
        val columnWise = transpose(map).map { isHiddenBidirectionally(it) }
        val bothWays = rowWise.zip(transpose(columnWise)).map { (r, c) -> r.zip(c).map { (a, b) -> a && b } }
        val inner = bothWays.map { it.drop(1).dropLast(1) }.drop(1).dropLast(1)
        val visibleCount = inner.flatten().count { !it }
        val outerRing = (map.size + map.first().size) * 2 - 4
        return visibleCount + outerRing
    }

    fun part2(input: List<String>): Int {
        val map = buildMap(input)
        val rowWise = map.map { visibilityScoreBidirectional(it) }
        val columnWise = transpose(map).map { visibilityScoreBidirectional(it) }
        val bothWays = rowWise.zip(transpose(columnWise)).map { (r, c) -> r.zip(c).map { (a, b) -> a * b } }
        return bothWays.maxOf { it.max() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_sample")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val realInput = readInput("Day08_input")
    println("Part 1: ${part1(realInput)}")
    println("Part 2: ${part2(realInput)}")
}

