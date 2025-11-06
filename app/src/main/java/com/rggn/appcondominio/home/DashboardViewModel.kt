package com.rggn.appcondominio.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rggn.appcondominio.data.CommonArea
import com.rggn.appcondominio.data.DataService

class DashboardViewModel(
    private val dataService: DataService = DataService()
) : ViewModel() {

    // 1. LiveData: Expondo a lista de ÁREAS COMUNS
    private val _areas = MutableLiveData<List<CommonArea>>()
    val areas: LiveData<List<CommonArea>> = _areas // Variável pública que a Activity observa

    // 2. Função de Carregamento
    fun loadAreas() {
        // Simplesmente obtém os dados mockados do serviço (CommonArea)
        val areaList = dataService.getCommonAreas()

        // Atualiza o LiveData
        _areas.value = areaList
    }

    // Carrega dados assim que o ViewModel é criado
    init {
        loadAreas()
    }
}