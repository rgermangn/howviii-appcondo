package com.rggn.appcondominio.reservation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rggn.appcondominio.data.AvailabilityStatus
import com.rggn.appcondominio.data.DataService
import org.junit.Rule
import org.junit.Test
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertEquals
import java.util.Calendar

class ReservationViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockDataService: DataService
    private lateinit var viewModel: ReservationViewModel

    private val areaId = 10
    private val testDate: Calendar = Calendar.getInstance().apply {
        set(2025, Calendar.DECEMBER, 5)
    }
    private val expectedDateString = "05/12/2025"

    private val mockAvailableStatus = AvailabilityStatus(isAvailable = true, residentName = null, residentUnit = null)

    private val mockReservedStatus = AvailabilityStatus(
        isAvailable = false,
        residentName = "João Silva",
        residentUnit = "A-101"
    )

    @Before
    fun setup() {
        mockDataService = mock(DataService::class.java)
        viewModel = ReservationViewModel(mockDataService)
    }

    @Test
    fun checkAvailability_quandoDisponivel_deveAtualizarLiveData() {
        `when`(mockDataService.checkAvailability(areaId, expectedDateString)).thenReturn(mockAvailableStatus)

        viewModel.checkAvailability(areaId, testDate)

        verify(mockDataService).checkAvailability(areaId, expectedDateString)

        val statusLiveData = viewModel.availabilityStatus.value
        assertNotNull(statusLiveData)
        assertTrue(statusLiveData!!.isAvailable)
    }

    @Test
    fun checkAvailability_quandoReservado_deveAtualizarLiveDataComDadosDoResidente() {
        `when`(mockDataService.checkAvailability(areaId, expectedDateString)).thenReturn(mockReservedStatus)

        viewModel.checkAvailability(areaId, testDate)

        val statusLiveData = viewModel.availabilityStatus.value
        assertNotNull(statusLiveData)
        assertFalse(statusLiveData!!.isAvailable)
        assertEquals("João Silva", statusLiveData.residentName)
        assertEquals("A-101", statusLiveData.residentUnit)
    }
}
