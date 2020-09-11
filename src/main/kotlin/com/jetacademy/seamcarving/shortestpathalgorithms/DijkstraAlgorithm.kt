package com.jetacademy.seamcarving.shortestpathalgorithms

import java.lang.Double

interface ShortestPathAlgorithm {
    val graph: List<List<Int>>
    val source: Int

    fun findShortestPath(): MutableMap<String, Map<String, Any>>
}



class DijkstraAlgorithm(
    override val graph: List<List<Int>>,
    override val source: Int
) : ShortestPathAlgorithm {

    init {
        initiateControlTable()
        setSourceDistToZero()
    }

    private fun setSourceDistToZero() {
        controlTable[source.toString()]!!["dist"] = 0
    }

    private lateinit var controlTable: MutableMap<String, MutableMap<String, Any>>
    private lateinit var shortPathHash: MutableMap<String, MutableMap<String, Any>>


    override fun findShortestPath(): MutableMap<String, Map<String, Any>> {
        val smallestUnprocessedNode = findUnprocessedNodeWithSmallestDistance()
        updateNeighborsDistancesAndPaths(smallestUnprocessedNode)
        markAsKnown(smallestUnprocessedNode)
        return if (allNodesAreKnow()) {
            var resultHash = mutableMapOf<String, Map<String, Any>>()
            controlTable.map {
                resultHash[it.key] = mapOf<String, Any>(
                    "shortestPath" to getPath(it.key.toInt()),
                    "shortestPathCost" to it.value["dist"]!!
                )
            }
            resultHash
        } else
            findShortestPath()
    }

    fun getPath(node: Int): String {
        if (node < 0)
            return ""
        val path = controlTable[node.toString()]!!["path"]
        if (path == -1 && node != source)
            return ""
        return "${getPath(path.toString().toInt())} $node"
    }

    private fun allNodesAreKnow(): Boolean =
        controlTable.all { it.value["isKnown"]!! == true }


    private fun markAsKnown(smallestUnprocessedNode: Int) {
        controlTable[smallestUnprocessedNode.toString()]!!["isKnown"] = true
    }

    private fun updateNeighborsDistancesAndPaths(smallestUnprocessedNode: Int) {
        graph[smallestUnprocessedNode]
            .forEachIndexed { index, weight ->
                if (weight >= 0)
                    updateDistanceAndPathFromSource(smallestUnprocessedNode, weight, index)
            }
    }

    private fun updateDistanceAndPathFromSource(smallestUnprocessedNode: Int, weight: Int, index: Int) {
        val currentDistanceFromSource = controlTable[smallestUnprocessedNode.toString()]!!["dist"].toString().toDouble()
        val currentDistance = controlTable[index.toString()]!!["dist"].toString().toDouble()
        if (currentDistanceFromSource + weight < currentDistance) {
            controlTable[index.toString()]!!["dist"] = currentDistanceFromSource + weight
            controlTable[index.toString()]!!["path"] = smallestUnprocessedNode
        }
    }

    private fun findUnprocessedNodeWithSmallestDistance(): Int =
        controlTable
            .filter { it.value["isKnown"] == false }
            .minBy { it.value["dist"].toString().toDouble() }!!.key.toInt()


    private fun initiateControlTable() {
        this.controlTable = mutableMapOf<String, MutableMap<String, Any>>().apply {
            graph.forEachIndexed { index, element ->
                this[index.toString()] = mutableMapOf(
                    "dist" to Double.POSITIVE_INFINITY,
                    "isKnown" to false,
                    "path" to -1
                )
            }
        }

    }
}