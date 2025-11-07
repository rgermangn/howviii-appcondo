package com.rggn.appcondominio.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rggn.appcondominio.data.AvailabilityStatus // Importação necessária
import com.rggn.appcondominio.data.DataService
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// Classe que contém a lógica de negócios para a tela de reserva
class ReservationViewModel(private val dataService: DataService) : ViewModel() {

    // LiveData para expor o status COMPLETO de disponibilidade para a Activity
    private val _availabilityStatus = MutableLiveData<AvailabilityStatus>()
    val availabilityStatus: LiveData<AvailabilityStatus> = _availabilityStatus // CORRIGIDO: Tipo e nome alterados

    // LiveData para expor a data selecionada para a Activity
    private val _selectedDate = MutableLiveData<Calendar>()
    val selectedDate: LiveData<Calendar> = _selectedDate

    // LiveData para expor o ID da Área Comum
    private val _areaId = MutableLiveData<Int>()
    val areaId: LiveData<Int> = _areaId

    // LiveData para expor as datas reservadas (usado em CalendarActivity)
    private val _reservedDates = MutableLiveData<List<String>>()
    val reservedDates: LiveData<List<String>> = _reservedDates

    // Inicializa o ID (Chamado pela Activity quando o Intent chega)
    fun setAreaId(id: Int) {
        _areaId.value = id
        // Assim que o ID é definido, carregamos as datas reservadas
        loadReservedDates(id)
    }

    // Carrega as datas reservadas para a área
    private fun loadReservedDates(areaId: Int) {
        val dates = dataService.getReservedDates(areaId)
        _reservedDates.value = dates
    }

    // Método para formatar a data para o formato esperado pelo DataService
    private fun formatDate(calendar: Calendar): String {
        // Formato dd/MM/yyyy esperado pelo DataService (que está mockado)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    // Lógica principal: Verifica a disponibilidade da área em uma data
    // CAL-D2.3: Método alterado para usar o novo DataService.checkAvailability
    fun checkAvailability(areaId: Int, date: Calendar) {
        // 1. Formata a data para a API
        val dateString = formatDate(date)

        // 2. Chama o DataService e recebe o Status Detalhado
        val status = dataService.checkAvailability(areaId, dateString) // CORRIGIDO: Chamada ao novo método

        // 3. Atualiza o LiveData com o objeto AvailabilityStatus completo
        _availabilityStatus.value = status // CORRIGIDO: Atualiza o novo LiveData
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