package com.jetacademy.seamcarving.extensions

import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.pow
import kotlin.math.sqrt

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

fun Int.pow(x: Int) = this.toDouble().pow(x)

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


