fun main() {

    data class RegisterState(val cycle: Int, val adjustment: Int)

    fun cycleStates(input: List<String>): List<RegisterState> {
        var cycle = 1
        return input
            .mapNotNull {
                val instruction = it.split(" ")
                when (instruction[0]) {
                    "noop" -> {
                        cycle += 1
                        null
                    }
                    "addx" -> {
                        cycle += 2
                        RegisterState(cycle, instruction[1].toInt())
                    }
                    else -> null
                }
            }
    }

    val keyCycles = listOf(20, 60, 100, 140, 180, 220)

    fun part1(input: List<String>): Int {
        val cycles = cycleStates(input)
        val signals = keyCycles.map { keyCycle ->
            val count = cycles
                .takeWhile { cycle -> cycle.cycle <= keyCycle }
                .sumOf { cycle -> cycle.adjustment }
            println("keyCycle: $keyCycle, count: ${count + 1} = ${(count + 1) * keyCycle}")
            (count + 1) * keyCycle
        }
        return signals.sum()
    }

    fun part2(input: List<String>): String {
        val cycles = cycleStates(input)
        val pixels = (0..239)
            .map {
                val position = 1 + cycles
                    .takeWhile { cycle -> cycle.cycle <= it }
                    .sumOf { cycle -> cycle.adjustment }
                val pixel = if ((it % 40) in position - 0  .. position + 2) {
                    "#"
                } else {
                    "-"
                }
                pixel
            }
        return pixels.chunked(40).map { it.joinToString("") }.joinToString("\n")
    }

    val testInput = readInput("Day10_sample")
    check(part1(testInput) == 13140)

    val realInput = readInput("Day10_input")
    println("Part 1: ${part1(realInput)}")
    println("Part 2: \n${part2(realInput)}")
}
