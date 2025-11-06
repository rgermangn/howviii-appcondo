package com.rggn.appcondominio.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rggn.appcondominio.data.DataService
import com.rggn.appcondominio.data.AvailabilityStatus
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// Classe que contém a lógica de negócios para a tela de reserva
class ReservationViewModel(private val dataService: DataService) : ViewModel() {

    // LiveData para expor o status DETALHADO de disponibilidade (CAL-D1)
    private val _availabilityStatus = MutableLiveData<AvailabilityStatus?>()
    val availabilityStatus: LiveData<AvailabilityStatus?> = _availabilityStatus

    // LiveData para expor a lista de DATAS RESERVADAS (NOVO - CAL-T2.3)
    private val _reservedDates = MutableLiveData<List<String>>()
    val reservedDates: LiveData<List<String>> = _reservedDates

    // LiveData para expor o ID da Área Comum
    private val _areaId = MutableLiveData<Int>()
    val areaId: LiveData<Int> = _areaId

    // Inicializa o ID (Chamado pela Activity quando o Intent chega)
    fun setAreaId(id: Int) {
        _areaId.value = id
        // Assim que o ID é setado, já buscamos a lista de datas reservadas
        fetchReservedDates(id)
    }

    // Método para formatar a data para o formato esperado pelo DataService
    private fun formatDate(calendar: Calendar): String {
        // Formato dd/MM/yyyy esperado pelo DataService (que está mockado)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    // Lógica principal: Verifica a disponibilidade detalhada da área em uma data (CAL-D1)
    fun checkAvailability(areaId: Int, date: Calendar) {
        val dateString = formatDate(date)
        // Em um projeto real, esta chamada seria assíncrona (com Coroutines/Flows)
        val status = dataService.checkAvailability(areaId, dateString)

        _availabilityStatus.value = status
    }

    // NOVO - Lógica para buscar as datas reservadas para a área (CAL-T2.3)
    fun fetchReservedDates(areaId: Int) {
        // Em um projeto real, esta chamada seria assíncrona
        val dates = dataService.getReservedDates(areaId)
        _reservedDates.value = dates
    }
}

// Classe Factory necessária para injetar o DataService no ViewModel
class ReservationViewModelFactory(private val dataService: DataService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReservationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReservationViewModel(dataService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
