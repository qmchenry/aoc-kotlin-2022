fun main() {

    data class File(val name: String, val size: Int)

    class DirectoryTree(val name: String, val parent: DirectoryTree?, var children: List<DirectoryTree> = emptyList(), var files: List<File> = emptyList()) {
        fun size(): Int = children.sumOf { it.size() } + files.sumOf { it.size }
        fun dirsOfSizeAtMost(size: Int): List<DirectoryTree> = children.filter { it.size() <= size } + children.flatMap { it.dirsOfSizeAtMost(size) }
        fun dirsOfSizeAtLeast(size: Int): List<DirectoryTree> = children.filter { it.size() >= size } + children.flatMap { it.dirsOfSizeAtLeast(size) }
        fun description(): String = "$name (${size()} bytes) with ${children.size} children and ${files.size} files\n"
        fun recursiveDescription(): String = description() + children.joinToString("") { it.recursiveDescription().prependIndent("  ") }
    }

    fun parseTree(input: List<String>): DirectoryTree {
        val root = DirectoryTree("/", null)
        var cwd = root
        input.forEach { line ->
            when {
                line == "$ cd /" -> cwd = root
                line == "$ cd .." -> cwd = cwd.parent ?: root
                line == "$ ls" -> Unit
                line.startsWith("$ cd ") -> {
                    val dirName = line.substringAfter("$ cd ")
                    val dir = cwd.children.find { it.name == dirName } ?: DirectoryTree(dirName, cwd).also { cwd.children += it }
                    cwd = dir
                }
                else -> {
                    val (dirOrSize, name) = line.split(" ")
                    when (dirOrSize) {
                        "dir" -> {
                            val newDir = DirectoryTree(name, cwd)
                            cwd.children += newDir
                        }
                        else -> {
                            val newFile = File(name, dirOrSize.toInt())
                            cwd.files += newFile
                        }
                    }
                }
            }
        }
        return root
    }

    fun part1(input: List<String>): Int {
        val root = parseTree(input)
        return root.dirsOfSizeAtMost(size = 100000)
            .sumOf { it.size() }
    }

    fun part2(input: List<String>): Int {
        val totalSize = 70000000
        val requiredSize = 30000000
        val root = parseTree(input)
        val availableSize = totalSize - root.size()
        val neededSize = requiredSize - availableSize
        val dirSizes = root.dirsOfSizeAtLeast(size =  neededSize).sortedBy { it.size() }
        return dirSizes.first().size()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_sample")
    check(part1(testInput) == 95437)

    val realInput = readInput("Day07_input")
    println("Part 1: ${part1(realInput)}")
    println("Part 2: ${part2(realInput)}")
}
