package com.jetacademy.seamcarving.shortestpathalgorithms

import java.util.*


class DijkstraAlgorithmAdjacencyArray(
    val graph: Array<List<Pair<Int, Double>>>,
    val target: Int,
    override val source: Int
) : ShortestPathAlgorithm {

    init {

        initiateControlTable()
        setSourceDistToZero()
    }


    private fun setSourceDistToZero() {
        nodeDataPriorityQueue.remove(nodeDataMap[source])
        nodeDataMap[source]!!.distance = 0.0
        nodeDataPriorityQueue.add(nodeDataMap[source])
    }

    private lateinit var controlTable: MutableMap<String, MutableMap<String, Any>>
    private lateinit var shortPathHash: MutableMap<String, MutableMap<String, Any>>
    private lateinit var nodeDataPriorityQueue: PriorityQueue<NodeData>
    private lateinit var nodeDataMap: MutableMap<Int, NodeData>
    private var numberOfNodes: Int = graph.size
    private var knownNodesCount: Int = 0


    override tailrec fun findShortestPath(): String {
        val smallestUnprocessedNode = findUnprocessedNodeWithSmallestDistance()
        updateNeighborsDistancesAndPaths(smallestUnprocessedNode)
        markAsKnown(smallestUnprocessedNode)
        return if (allNodesAreKnow() || targetNodeIsKnown())
            getPath(target)
        else
            findShortestPath()
    }

    private fun targetNodeIsKnown(): Boolean = nodeDataMap[target]!!.isKnown

    private fun getPath(node: Int): String {
        if (node < 0)
            return ""
        val path = nodeDataMap[node]!!.path
        if (path == -1 && node != source)
            return ""
        return "${getPath(path.toString().toInt())} $node"
    }

    private fun allNodesAreKnow(): Boolean = knownNodesCount == numberOfNodes


    private fun markAsKnown(smallestUnprocessedNode: Int) {
        knownNodesCount += 1
        nodeDataMap[smallestUnprocessedNode]!!.isKnown = true
    }

    private fun updateNeighborsDistancesAndPaths(smallestUnprocessedNode: Int) {
        graph[smallestUnprocessedNode]
            .map { connectionPair ->
                val index = connectionPair.first
                val weight = connectionPair.second
                updateDistanceAndPathFromSource(smallestUnprocessedNode, weight, index)
            }
    }

    private fun hasConnectionWithNode(weight: Int) = weight >= 0

    private fun updateDistanceAndPathFromSource(smallestUnprocessedNode: Int, weight: Double, index: Int) {
        val currentDistanceFromSource = nodeDataMap[smallestUnprocessedNode]!!.distance
        val currentDistance = nodeDataMap[index]!!.distance
        if (currentDistanceFromSource + weight < currentDistance) {
            nodeDataPriorityQueue.remove(nodeDataMap[index])
            nodeDataMap[index]!!.distance = currentDistanceFromSource + weight
            nodeDataMap[index]!!.path = smallestUnprocessedNode
            nodeDataPriorityQueue.add(nodeDataMap[index])
        }
    }

    private fun findUnprocessedNodeWithSmallestDistance(): Int = nodeDataPriorityQueue.poll().index

    private fun initiateControlTable() {
        nodeDataMap = mutableMapOf()
        nodeDataPriorityQueue = PriorityQueue(NodeDataComparator()).apply {
            graph.forEachIndexed { index, element ->
                val nodeData = NodeData(
                    index = index,
                    distance = Double.POSITIVE_INFINITY,
                    isKnown = false,
                    path = -1,
                )
                add(nodeData)
                nodeDataMap[index] = nodeData
            }
        }

        controlTable = mutableMapOf<String, MutableMap<String, Any>>().apply {
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

class NodeDataComparator : Comparator<NodeData> {
    override fun compare(o1: NodeData, o2: NodeData): Int {
        return if (o1.distance > o2.distance) 1 else -1
    }
}

class NodeData(
    val index: Int,
    var distance: Double,
    var isKnown: Boolean,
    var path: Int
)
