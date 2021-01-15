package com.jetacademy.seamcarving

import com.jetacademy.seamcarving.NodeData
import java.util.Comparator

class NodeDataComparator : Comparator<NodeData> {
    override fun compare(o1: NodeData, o2: NodeData): Int {
        return if (o1.distance > o2.distance) 1 else -1
    }
}