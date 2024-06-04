package com.example.busapiclienttokenscompose.domain.model

data class LocationPoint(
    val x: Double,
    val y: Double
) {
    override fun toString(): String {
        return """
            x=$x
            y=$y
        """.trimIndent()
    }
}


