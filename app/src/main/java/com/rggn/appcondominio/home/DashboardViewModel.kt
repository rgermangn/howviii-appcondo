package com.rggn.appcondominio.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rggn.appcondominio.data.CommonArea
import com.rggn.appcondominio.data.DataService

class DashboardViewModel(
    private val dataService: DataService = DataService()
) : ViewModel() {

    private val _areas = MutableLiveData<List<CommonArea>>()
    val areas: LiveData<List<CommonArea>> = _areas

    fun loadAreas() {
        val areaList = dataService.getCommonAreas()

        _areas.value = areaList
    }

    init {
        loadAreas()
    }
}