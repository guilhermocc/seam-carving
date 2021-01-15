package seamcarving.shortestpath

interface ShortestPathAlgorithm {
    val source: Int

    fun findShortestPath(): List<Int>
}