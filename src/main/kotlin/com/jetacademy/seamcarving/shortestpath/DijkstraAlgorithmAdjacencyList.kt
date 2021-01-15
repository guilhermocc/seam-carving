package seamcarving.shortestpath

import com.jetacademy.seamcarving.NodeData
import com.jetacademy.seamcarving.NodeDataComparator
import java.util.*


class DijkstraAlgorithmAdjacencyArray(
        val graph: Array<List<Pair<Int, Double>>>,
        val target: Int,
        override val source: Int
) : ShortestPathAlgorithm {

    init {
        initializePriorityQueueAndMap()
        setSourceDistToZero()
    }

    private lateinit var nodeDataPriorityQueue: PriorityQueue<NodeData>
    private lateinit var nodeDataMap: MutableMap<Int, NodeData>


    override tailrec fun findShortestPath(): List<Int> {
        val smallestUnprocessedNode = findUnprocessedNodeWithSmallestDistance()
        updateNeighborsDistancesAndPaths(smallestUnprocessedNode)
        markAsKnown(smallestUnprocessedNode)
        return if (targetNodeIsKnown())
            getPath(target)
        else
            findShortestPath()
    }

    private fun targetNodeIsKnown(): Boolean = nodeDataMap[target]!!.isKnown

    private fun getPath(nodeIndex: Int): List<Int> {
        val path = nodeDataMap[nodeIndex]!!.path
        if (path == -1)
            return listOf()
        return mutableListOf(nodeIndex).apply { addAll(getPath(path)) }
    }

    private fun markAsKnown(smallestUnprocessedNode: Int) {
        nodeDataMap[smallestUnprocessedNode]!!.isKnown = true
    }

    private fun updateNeighborsDistancesAndPaths(smallestUnprocessedNode: Int) =
        graph[smallestUnprocessedNode]
            .map { connectionPair ->
                val index = connectionPair.first
                val weight = connectionPair.second
                updateDistanceAndPathFromSource(smallestUnprocessedNode, weight, index)
            }

    private fun updateDistanceAndPathFromSource(smallestUnprocessedNode: Int, weight: Double, index: Int) {
        val currentNode = nodeDataMap[index]!!
        val currentDistanceFromSource = nodeDataMap[smallestUnprocessedNode]!!.distance
        val currentDistance = currentNode.distance
        if (currentDistanceFromSource + weight < currentDistance) {
            currentNode.distance = currentDistanceFromSource + weight
            currentNode.path = smallestUnprocessedNode
            updateNodeInQueue(currentNode)
        }
    }

    private fun findUnprocessedNodeWithSmallestDistance(): Int = nodeDataPriorityQueue.poll().index

    private fun setSourceDistToZero() {
        val node = nodeDataMap[source]!!
        node.distance = 0.0
        updateNodeInQueue(node)
    }

    private fun updateNodeInQueue(node: NodeData) =
        nodeDataPriorityQueue.apply {
            remove(node)
            add(node)
        }

    private fun initializePriorityQueueAndMap() {
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
    }
}
