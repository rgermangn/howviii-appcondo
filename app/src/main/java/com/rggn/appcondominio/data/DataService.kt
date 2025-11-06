package com.rggn.appcondominio.data

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

    // DATA-T7/CAL-T2
    //Método para verificar a disponibilidade de uma área em uma data
    // Retorna true se estiver disponível, false se estiver reservado.
    // Usaremos mocks para forçar cenários no teste.
    fun isAreaAvailable(areaId: Int, date: String): Boolean {
        // Implementação Mock: Assume-se que a área 10 (Salão de Festas) está sempre indisponível
        // na data "05/12/2025" (a mesma data mockada no teste de UI)
        return when (areaId) {
            10 -> date != "05/12/2025" // Salão de Festas está indisponível em 05/12/2025
            else -> true // Outras áreas/datas estão disponíveis
        }
    }
    // CAL-T2.2 MÉTODO CORRIGIDO: Retorna o status detalhado
    fun checkAvailability(areaId: Int, date: String): AvailabilityStatus {
        return when (areaId) {
            10 -> if (date == "05/12/2025") {
                AvailabilityStatus(
                    isAvailable = false,
                    residentName = "João Silva",
                    residentUnit = "A-101"
                )
            } else {
                AvailabilityStatus(isAvailable = true)
            }
            else -> AvailabilityStatus(isAvailable = true)
        }
    }

    // Refatorado em CAL-D2.3: Método para buscar todas as datas reservadas para uma área
    fun getReservedDates(areaId: Int): List<String> {
        return if (areaId == 10) {
            // CORRIGIDO: Mocks para o Salão de Festas (ID 10) para corresponder ao CalendarActivityTest
            listOf("01/12/2025", "02/12/2025", "03/12/2025")
        } else {
            // Mocks para outras áreas
            listOf("01/01/2026")
        }
    }

}