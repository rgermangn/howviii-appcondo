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
    // CAL-T2.2 LiveData ATUALIZADO: Agora expõe o objeto AvailabilityStatus (resolve erro de referência)
    private val _availabilityStatus = MutableLiveData<AvailabilityStatus>()
    val availabilityStatus: LiveData<AvailabilityStatus> = _availabilityStatus
    // LiveData para expor o status de disponibilidade para a Activity
    private val _isAvailable = MutableLiveData<Boolean>()
    val isAvailable: LiveData<Boolean> = _isAvailable

    // LiveData para expor a data selecionada para a Activity
    private val _selectedDate = MutableLiveData<Calendar>()
    val selectedDate: LiveData<Calendar> = _selectedDate

    // LiveData para expor o ID da Área Comum
    private val _areaId = MutableLiveData<Int>()
    val areaId: LiveData<Int> = _areaId

    // Inicializa o ID (Chamado pela Activity quando o Intent chega)
    fun setAreaId(id: Int) {
        _areaId.value = id
    }

    // Método para formatar a data para o formato esperado pelo DataService
    private fun formatDate(calendar: Calendar): String {
        // Formato dd/MM/yyyy esperado pelo DataService (que está mockado)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    // CAL T2.2 MÉTODO CORRIGIDO: Chama checkAvailability (resolve erro de referência)
    fun checkAvailability(areaId: Int, date: Calendar) {
        val dateString = formatDate(date)
        val status = dataService.checkAvailability(areaId, dateString)
        _availabilityStatus.value = status
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
