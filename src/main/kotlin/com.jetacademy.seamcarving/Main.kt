package com.jetacademy.seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

fun main() {
    val scanner = Scanner(System.`in`)
    print("Enter rectangle width:")
    val width = scanner.nextInt()
    print("Enter rectangle height:")
    val height = scanner.nextInt()
    print("Enter output image name:")
    val outputFileName = scanner.next()
    val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val graphics2d = bufferedImage.createGraphics()
    graphics2d.apply {
        background = Color.BLACK
        color = Color.RED
        drawLine(0, 0, width - 1, height - 1)
        drawLine(0, height - 1, width - 1, 0)
    }.also {
        ImageIO.write(bufferedImage, "png", File(outputFileName))
    }
    println("image created")

}
