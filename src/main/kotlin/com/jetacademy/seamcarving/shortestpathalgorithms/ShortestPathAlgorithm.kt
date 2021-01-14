package com.jetacademy.seamcarving.shortestpathalgorithms

interface ShortestPathAlgorithm {
    val source: Int

    fun findShortestPath(): String
}