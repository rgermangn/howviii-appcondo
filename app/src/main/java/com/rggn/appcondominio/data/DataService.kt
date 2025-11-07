package com.rggn.appcondominio.data

data class Resident(val id: Int, val name: String, val unit: String)
data class CommonArea(val id: Int, val name: String, val capacity: Int)
data class AvailabilityStatus(val isAvailable: Boolean, val residentName: String?, val residentUnit: String?)

class DataService {

    fun getCommonAreas(): List<CommonArea> {
        return listOf(
            CommonArea(id = 10, name = "Salão de Festas", capacity = 40),
            CommonArea(id = 20, name = "Churrasqueira Gourmet", capacity = 20)
        )
    }

    fun checkAvailability(areaId: Int, date: String): AvailabilityStatus {
        return when {
            areaId == 10 && date == "05/12/2025" -> {
                AvailabilityStatus(
                    isAvailable = false,
                    residentName = "João Silva",
                    residentUnit = "A-101"
                )
            }
            areaId == 20 && date == "10/12/2025" -> {
                AvailabilityStatus(
                    isAvailable = false,
                    residentName = "Maria Souza",
                    residentUnit = "B-202"
                )
            }
            else -> {
                AvailabilityStatus(
                    isAvailable = true,
                    residentName = null,
                    residentUnit = null
                )
            }
        }
    }

    fun getAreaNameById(areaId: Int): String {
        return getCommonAreas().find { it.id == areaId }?.name ?: "Área desconhecida"
    }
}