package com.example.bus_api_client_xml.domain.model

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


