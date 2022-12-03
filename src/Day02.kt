private sealed class RPS {
    object Rock : RPS()
    object Paper : RPS()
    object Scissors : RPS()

    fun beats(other: RPS): Boolean {
        return when (this) {
            Rock -> other == Scissors
            Paper -> other == Rock
            Scissors -> other == Paper
        }
    }

    val winVs: RPS
        get() = when (this) {
            Rock -> Paper
            Paper -> Scissors
            Scissors -> Rock
        }

    val loseVs: RPS
        get() = when (this) {
            Rock -> Scissors
            Paper -> Rock
            Scissors -> Paper
        }

    val drawVs: RPS
        get() = when (this) {
            Rock -> Rock
            Paper -> Paper
            Scissors -> Scissors
        }

    val points: Int
        get() = when (this) {
            Rock -> 1
            Paper -> 2
            Scissors -> 3
        }

    fun compete(other: RPS): Int {
        return points + when {
            this == other -> 3
            this.beats(other) -> 6
            else -> 0
        }
    }

    companion object {
        fun fromString(string: String): RPS {
            return when (string.lowercase()) {
                "a", "x" -> Rock
                "b", "y" -> Paper
                "c", "z" -> Scissors
                else -> throw IllegalArgumentException("Unknown RPS: $string")
            }
        }
    }
}

private sealed class RPSOutcome {
    object Win : RPSOutcome()
    object Draw : RPSOutcome()
    object Lose : RPSOutcome()

    fun rpsForOutcomeVs(otherPlayer: RPS): RPS {
        return when (this) {
            Win -> otherPlayer.winVs
            Draw -> otherPlayer.drawVs
            Lose -> otherPlayer.loseVs
        }
    }

    val points: Int
        get() = when (this) {
            Win -> 6
            Draw -> 3
            Lose -> 0
        }

    companion object {
        fun fromString(string: String): RPSOutcome {
            return when (string.lowercase()) {
                "x" -> Lose
                "y" -> Draw
                "z" -> Win
                else -> throw IllegalArgumentException("Unknown RPSOutcome: $string")
            }
        }
    }
}

fun main() {

    fun naiveScore(input: List<String>): Int {
        return input
            .map { row ->
                row
                    .split(" ")
                    .map { RPS.fromString(it) }
            }
            .map {
                it[1].compete(it[0])
            }
            .sum()
    }

    fun strategicScore(input: List<String>): Int {
        return input
            .map {
                val round = it.split(" ")
                val player1 = RPS.fromString(round[0])
                val outcome = RPSOutcome.fromString(round[1])
                val player2 = outcome.rpsForOutcomeVs(player1)
                outcome.points + player2.points
            }
            .sum()
    }

    fun part1(input: List<String>): Int {
        return naiveScore(input)
    }

    fun part2(input: List<String>): Int {
        return strategicScore(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_sample")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val realInput = readInput("Day02_input")
    println("Part 1: ${part1(realInput)}")
    println("Part 2: ${part2(realInput)}")
}
