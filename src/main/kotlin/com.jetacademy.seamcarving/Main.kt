package com.jetacademy.seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val inputFile = args[args.indexOf("-in") + 1]
    val outputFile = args[args.indexOf("-out") + 1]
    val bufferedInputImage: BufferedImage = ImageIO.read(File(inputFile))
    ImageIO.write(bufferedInputImage.invertImageColors(), "png", File(outputFile))
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

