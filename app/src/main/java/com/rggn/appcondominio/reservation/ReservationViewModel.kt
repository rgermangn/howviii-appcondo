package com.rggn.appcondominio.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rggn.appcondominio.data.AvailabilityStatus
import com.rggn.appcondominio.data.DataService
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ReservationViewModel(private val dataService: DataService) : ViewModel() {

    private val _availabilityStatus = MutableLiveData<AvailabilityStatus>()
    val availabilityStatus: LiveData<AvailabilityStatus> = _availabilityStatus

    fun checkAvailability(areaId: Int, date: Calendar) {
        val dateString = formatDate(date)
        val status = dataService.checkAvailability(areaId, dateString)
        _availabilityStatus.value = status
    }

    private fun formatDate(calendar: Calendar): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}

class ReservationViewModelFactory(private val dataService: DataService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReservationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReservationViewModel(dataService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
