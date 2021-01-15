package com.jetacademy.seamcarvi

ng.extensions

import seamcarving.shortestpath.DijkstraAlgorithmAdjacencyArray
import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.sqrt


fun BufferedImage.drawVerticalSeam(): BufferedImage =
        this.apply {
            val numberOfNodes = (this.height * this.width) + 2 * this.width
            val graph: Array<List<Pair<Int, Double>>> = Array(numberOfNodes) { listOf() }
            generateImageNodesConnections(graph, this)

            val seamNodesList =
                DijkstraAlgorithmAdjacencyArray(
                        graph = graph,
                        target = numberOfNodes - 1,
                        source = 0
                ).findShortestPath()


            val seamNodesListWithoutZeroedNodes = seamNodesList.filter {
                it > this.width - 1 && it < numberOfNodes - this.width
            }

            val pixelsToPaint = seamNodesListWithoutZeroedNodes.map {
                getPixelCoordinatesFromNode(it, this.width)
            }

            paintSeam(pixelsToPaint, this)
        }

fun BufferedImage.energizeImage(): BufferedImage =
        this.apply {
            val pixelsEnergies = mutableListOf<Map<String, Double>>()
            repeat(this.width) { x ->
                repeat(this.height) { y ->
                    pixelsEnergies.add(
                            mapOf(
                                    "energy" to pixelEnergy(x, y, this),
                                    "x" to x.toDouble(),
                                    "y" to y.toDouble()
                            )
                    )
                }
            }
            val maxEnergy: Double =
                    pixelsEnergies.maxBy { selector -> selector["energy"]!! }?.get("energy")!!

            pixelsEnergies.map {
                val intensity = (255 * it["energy"]!! / maxEnergy).toInt()
                this.setRGB(it["x"]!!.toInt(), it["y"]!!.toInt(), Color(intensity, intensity, intensity).rgb)
            }
        }

fun BufferedImage.invertImageColors(): BufferedImage =
        this.apply {
            repeat(this.width) { x ->
                repeat(this.height) { y ->
                    val currentColor = Color(this.getRGB(x, y))
                    this.setRGB(
                            x, y, Color(255 - currentColor.red, 255 - currentColor.green, 255 - currentColor.blue).rgb
                    )
                }
            }
        }


private fun generateImageNodesConnections(graph: Array<List<Pair<Int, Double>>>, image: BufferedImage ) {
    var nodeIndex = 0

    repeat(image.height + 2) { y ->
        repeat(image.width) { x ->
            graph[nodeIndex++] = getConnectedNodes(y, x, image)
        }
    }
}

fun pixelEnergy(x: Int, y: Int, image: BufferedImage): Double {
    val xn = when (x) {
        0 -> 1
        image.width - 1 -> x - 1
        else -> x
    }
    val yn = when (y) {
        0 -> 1
        image.height - 1 -> y - 1
        else -> y
    }
    return sqrt(Δx(xn, y, image) + Δy(x, yn, image))
}

private fun paintSeam(resultIndexes: List<Pair<Int, Int>>, image: BufferedImage) {
    resultIndexes.map {
        image.setRGB(it.first, it.second, Color.RED.rgb)
    }
}


fun getPixelCoordinatesFromNode(element: Int, width: Int): Pair<Int, Int> {
    val y = element / width - 1
    val x = element - (y + 1) * width
    return Pair(x, y)
}

fun getConnectedNodes(y: Int, x: Int, image: BufferedImage): List<Pair<Int, Double>> =
        mutableListOf<Pair<Int, Double>>().apply {
            when (y) {
                0 -> {
                    if (x > 0) {
                        add(getAdjacencyPair(x - 1, y, image))
                        add(getAdjacencyPair(x - 1, y + 1, image))
                    }
                    if (x < image.width - 1) {
                        add(getAdjacencyPair(x + 1, y, image))
                        add(getAdjacencyPair(x + 1, y + 1, image))
                    }
                    add(getAdjacencyPair(x, y + 1, image))
                }
                image.height + 1 -> {
                    if (x > 0) {
                        add(getAdjacencyPair(x - 1, y, image))
                    }
                    if (x < image.width - 1) {
                        add(getAdjacencyPair(x + 1, y, image))
                    }
                }
                else -> {
                    if (x > 0) {
                        add(getAdjacencyPair(x - 1, y + 1, image))
                    }
                    if (x < image.width - 1) {
                        add(getAdjacencyPair(x + 1, y + 1, image))
                    }
                    add(getAdjacencyPair(x, y + 1, image))
                }
            }

        }


fun getAdjacencyPair(x: Int, y: Int, image: BufferedImage) = Pair((image.width - 1) * y + x + y, pixelEnergyWithZeroesConsidered(x, y, image))

fun pixelEnergyWithZeroesConsidered(x: Int, y: Int, image: BufferedImage): Double =
        when (y) {
            0 -> 0.0
            image.height + 1 -> 0.0
            else -> pixelEnergy(x, y - 1, image)
        }


fun Δx(x: Int, y: Int, image: BufferedImage) =
        Rx(x, y, image).pow(2) + Gx(x, y, image).pow(2) + Bx(x, y, image).pow(2)

fun Rx(x: Int, y: Int, image: BufferedImage) =
        Color(image.getRGB(x - 1, y)).red - Color(image.getRGB(x + 1, y)).red

fun Gx(x: Int, y: Int, image: BufferedImage) =
        Color(image.getRGB(x - 1, y)).green - Color(image.getRGB(x + 1, y)).green

fun Bx(x: Int, y: Int, image: BufferedImage) =
        Color(image.getRGB(x - 1, y)).blue - Color(image.getRGB(x + 1, y)).blue

fun Δy(x: Int, y: Int, image: BufferedImage) =
        Ry(x, y, image).pow(2) + Gy(x, y, image).pow(2) + By(x, y, image).pow(2)

fun Ry(x: Int, y: Int, image: BufferedImage) =
        Color(image.getRGB(x, y - 1)).red - Color(image.getRGB(x, y + 1)).red

fun Gy(x: Int, y: Int, image: BufferedImage) =
        Color(image.getRGB(x, y - 1)).green - Color(image.getRGB(x, y + 1)).green

fun By(x: Int, y: Int, image: BufferedImage) =
        Color(image.getRGB(x, y - 1)).blue - Color(image.getRGB(x, y + 1)).blue


