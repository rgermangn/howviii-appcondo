package com.rggn.appcondominio.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rggn.appcondominio.data.DataService
import com.rggn.appcondominio.data.Resident

class DashboardViewModel(
    private val dataService: DataService = DataService()
) : ViewModel() {

    // 1. LiveData: Estado que será observado pela Activity (O teste procura por esta propriedade)
    private val _residents = MutableLiveData<List<Resident>>()
    val residents: LiveData<List<Resident>> = _residents

    // 2. Função de Carregamento (O teste procura por este método)
    fun loadResidents() {
        // Simplesmente obtém os dados mockados do serviço
        val residentList = dataService.getResidents()

        // Atualiza o LiveData, que notificará a UI
        _residents.value = residentList
    }

    // Opcional, mas útil: Carregar dados assim que o ViewModel é criado
    init {
        loadResidents()
    }
}