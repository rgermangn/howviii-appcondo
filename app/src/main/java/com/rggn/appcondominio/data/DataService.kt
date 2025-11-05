package com.rggn.appcondominio.data

class DataService {
    // Função que retorna a lista de moradores (dados simulados)
    fun getResidents(): List<Resident> {
        return listOf(
            Resident(id = 1, name = "João Silva", unit = "Apt 101"),
            Resident(id = 2, name = "Maria Souza", unit = "Apt 205"),
            Resident(id = 3, name = "Pedro Santos", unit = "Apt 301")
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
}