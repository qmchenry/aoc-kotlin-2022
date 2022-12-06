fun main() {
    fun scanForStartOfPacket(input: String, windowLength: Int = 4): Int {
        return input
            .windowed(windowLength, 1)
            .indexOfFirst {
                it.toSet().count() == windowLength
            } + windowLength
    }

    // test if implementation meets criteria from the description, like:
    check(scanForStartOfPacket("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 7)
    check(scanForStartOfPacket("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    check(scanForStartOfPacket("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    check(scanForStartOfPacket("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    check(scanForStartOfPacket("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)

    check(scanForStartOfPacket("mjqjpqmgbljsphdztnvjfqwrcgsmlb", windowLength = 14) == 19)
    check(scanForStartOfPacket("bvwbjplbgvbhsrlpgdmjqwftvncz", windowLength = 14) == 23)
    check(scanForStartOfPacket("nppdvjthqldpwncqszvftbrmjlhg", windowLength = 14) == 23)
    check(scanForStartOfPacket("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", windowLength = 14) == 29)
    check(scanForStartOfPacket("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", windowLength = 14) == 26)

    val realInput = readInput("Day06_input")
    println("Part 1: ${scanForStartOfPacket(realInput.first())}")
    println("Part 2: ${scanForStartOfPacket(realInput.first(), windowLength = 14)}")
}


