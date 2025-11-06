package com.rggn.appcondominio.data

data class AvailabilityStatus(
    val isAvailable: Boolean,
    val residentName: String? = null,
    val residentUnit: String? = null
)