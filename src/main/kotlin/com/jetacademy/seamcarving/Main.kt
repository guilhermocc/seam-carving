package com.jetacademy.seamcarving

import com.jetacademy.seamcarving.shortestpathalgorithms.DijkstraAlgorithm
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    // val inputFile = args[args.indexOf("-in") + 1]
    // val outputFile = args[args.indexOf("-out") + 1]
    // val bufferedInputImage: BufferedImage = ImageIO.read(File(inputFile))
    // ImageIO.write(bufferedInputImage.invertImageColors(), "png", File(outputFile))
    val graph = listOf<List<Int>>(
        listOf(-1, 7, 9, 6, -1, -1, -1, -1),
        listOf(7, -1, -1, 2, -1, 6, -1, -1),
        listOf(9, -1, -1, -1, 1, 5, -1, -1),
        listOf(6, 2, -1, -1, -1, -1, -1, -1),
        listOf(-1, -1, 1, -1, -1, -1, 7, -1),
        listOf(-1, 6, 5, -1, -1, -1, 1, -1),
        listOf(-1, -1, -1, -1, 7, 1, -1, -1),
        listOf(-1, -1, -1, -1, -1, -1, -1, -1)
    )
    print(
        DijkstraAlgorithm(
            graph = graph,
            source = 0
        ).findShortestPath())

}
