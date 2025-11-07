package com.rggn.appcondominio.data

data class Resident(val id: Int, val name: String, val unit: String)
data class CommonArea(val id: Int, val name: String, val capacity: Int)
data class AvailabilityStatus(val isAvailable: Boolean, val residentName: String?, val residentUnit: String?)

class DataService {
    // Função que retorna a lista de moradores (dados simulados)
    fun getResidents(): List<Resident> {
        return listOf(
            Resident(id = 1, name = "João Silva", unit = "A-101"),
            Resident(id = 2, name = "Maria Souza", unit = "B-202"),
            Resident(id = 3, name = "Pedro Santos", unit = "C-281")
            // O teste DATA-T1 espera 3 itens com esses dados
        )
    }

    fun getCommonAreas(): List<CommonArea> {
        return listOf(
            CommonArea(id = 10, name = "Salão de Festas", capacity = 40),
            CommonArea(id = 20, name = "Churrasqueira Gourmet", capacity = 20)
            // O teste DATA-T2 espera 2 itens com esses nomes
        )
    }
    // CAL-T2.2 MÉTODO CORRIGIDO: Retorna o status detalhado
    // CAL-T4 IMPLEMENTAÇÃO: Simula a chamada à API para verificar disponibilidade
    fun checkAvailability(areaId: Int, date: String): AvailabilityStatus {
        // Implementação Mock: Assume-se que a área 10 (Salão de Festas) está indisponível
        // na data "05/12/2025", reservada por Maria Souza.
        return if (areaId == 10 && date == "05/12/2025") {
            // Cenário 1: Reservado (dados de João Silva)
            AvailabilityStatus(
                isAvailable = false,
                residentName = "João Silva",
                residentUnit = "A-101"
            )
        } else if (areaId == 20 && date == "10/12/2025") {
            // Cenário 2: Outra reserva (dados de Maria Souza)
            AvailabilityStatus(
                isAvailable = false,
                residentName = "Maria Souza",
                residentUnit = "B-202"
            )
        } else {
            // Cenário 3: Disponível
            AvailabilityStatus(
                isAvailable = true,
                residentName = null,
                residentUnit = null
            )
        }
    }

    // Refatorado em CAL-D2.3: Método para buscar todas as datas reservadas para uma área
    // Atualizado em CAL-T4
    fun getReservedDatesForArea(areaId: Int): List<String> {
        return when (areaId) {
            10 -> listOf("01/12/2025", "02/12/2025", "03/12/2025", "05/12/2025")
            20 -> listOf("10/12/2025", "15/12/2025")
            else -> emptyList()
        }
    }

    fun getAreaNameById(areaId: Int): String {
        return getCommonAreas().find { it.id == areaId }?.name ?: "Área desconhecida"
    }
}