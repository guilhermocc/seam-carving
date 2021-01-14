package com.jetacademy.seamcarving

import com.jetacademy.seamcarving.extensions.pixelEnergy
import com.jetacademy.seamcarving.shortestpathalgorithms.DijkstraAlgorithmAdjacencyArray
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {


    val timeInMillis = measureTimeMillis {

        val inputFile = args[args.indexOf("-in") + 1]
        val outputFile = args[args.indexOf("-out") + 1]
        val bufferedInputImage: BufferedImage = ImageIO.read(File(inputFile))
        val imageWithVerticalSeam = bufferedInputImage

        val pixelsEnergiesGraph = buildPixelsEnergiesGraph(imageWithVerticalSeam)


        ImageIO.write(imageWithVerticalSeam, "png", File(outputFile))

    }

    println("(Total time took ${timeInMillis / 1000.0} s)")


}


fun buildPixelsEnergiesGraph(image: BufferedImage): Any {
    val numberOfNodes = (image.height * image.width) + 2 * image.width
    val nodes: Array<List<Pair<Int, Double>>> = Array(numberOfNodes) { listOf() }
    val timeInMillisBuildNodes = measureTimeMillis {
        var nodeIndex = 0
        repeat(image.height + 2) { y ->
            repeat(image.width) { x ->
                nodes[nodeIndex++] = getConnectedNodes(y, x, image)
            }
        }

    }
    println("(Build Nodes time took ${timeInMillisBuildNodes / 1000.0} s)")


    var result  = ""
    val timeInMillisDijkstra = measureTimeMillis {
        result =
            DijkstraAlgorithmAdjacencyArray(
                graph = nodes,
                target = numberOfNodes-1,
                source = 0
            ).findShortestPath()

    }
    println("(Dijkstra time took ${timeInMillisDijkstra / 1000.0} s)")



    var resultIndexes = listOf<Pair<Int,Int>>()

    val timeInMillisFormatting = measureTimeMillis {
        val resultNodes = result.split(" ")?.mapNotNull { it.toIntOrNull() }
        val resultNodeWithoutZeroeds = resultNodes!!.filter {
            it > image.width - 1 && it < numberOfNodes - image.width
        }

        resultIndexes = resultNodeWithoutZeroeds.map {
            getIndexes(it, image.width)
        }

    }

    println("(Dijkstra result formatting time took ${timeInMillisFormatting / 1000.0} s)")


    val timeInMillisPainting = measureTimeMillis {
        paintSeam(resultIndexes, image)

    }

    println("(Painting time took ${timeInMillisPainting / 1000.0} s)")

    return Any()

}

fun paintSeam(resultIndexes: List<Pair<Int, Int>>, image: BufferedImage) {
    resultIndexes.map {
        image.setRGB(it.first, it.second, Color.RED.rgb)
    }
}

fun getIndexes(element: Int, width: Int): Pair<Int, Int> {
    val y = element / width - 1
    val x = element - (y + 1) * width
    return Pair(x, y)
}

fun getConnectedNodes(y: Int, x: Int, image: BufferedImage): List<Pair<Int, Double>> {
    val adjacencyList = mutableListOf<Pair<Int, Double>>()
    when (y) {
        0 -> {
            if (x > 0) {
                adjacencyList.add(getAdjacencyPair(x - 1, y, image))
                adjacencyList.add(getAdjacencyPair(x - 1, y + 1, image))
            }
            if (x < image.width - 1) {
                adjacencyList.add(getAdjacencyPair(x + 1, y, image))
                adjacencyList.add(getAdjacencyPair(x + 1, y + 1, image))
            }
            adjacencyList.add(getAdjacencyPair(x, y + 1, image))
        }
        image.height + 1 -> {
            if (x > 0) {
                adjacencyList.add(getAdjacencyPair(x - 1, y, image))
            }
            if (x < image.width - 1) {
                adjacencyList.add(getAdjacencyPair(x + 1, y, image))
            }
        }
        else -> {
            if (x > 0) {
                adjacencyList.add(getAdjacencyPair(x - 1, y + 1, image))
            }
            if (x < image.width - 1) {
                adjacencyList.add(getAdjacencyPair(x + 1, y + 1, image))
            }
            adjacencyList.add(getAdjacencyPair(x, y + 1, image))
        }
    }

    return adjacencyList.toList()
}


fun getAdjacencyPair(x: Int, y: Int, image: BufferedImage) = Pair((image.width - 1) * y + x + y, pixelEnergyWithZeroesConsidered(x, y, image))

fun pixelEnergyWithZeroesConsidered(x: Int, y: Int, image: BufferedImage): Double =
    when (y) {
        0 -> 0.0
        image.height + 1 -> 0.0
        else -> pixelEnergy(x, y - 1, image)
    }




