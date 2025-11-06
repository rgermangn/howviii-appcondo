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
import org.junit.Assert.assertEquals
import java.util.Calendar

class ReservationViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockDataService: DataService
    private lateinit var viewModel: ReservationViewModel

    private val areaId = 10
    private val testDate: Calendar = Calendar.getInstance().apply {
        set(2025, Calendar.DECEMBER, 5) // 05/12/2025
    }
    private val expectedDateString = "05/12/2025"
    private val mockAvailableStatus = AvailabilityStatus(isAvailable = true)

    @Before
    fun setup() {
        // As dependências Mockito devem estar configuradas no build.gradle
        mockDataService = mock(DataService::class.java)

        // CORRIGIDO: Referencia o método checkAvailability
        `when`(mockDataService.checkAvailability(areaId, expectedDateString))
            .thenReturn(mockAvailableStatus)

        viewModel = ReservationViewModel(mockDataService)
    }

    @Test
    fun checkAvailability_deveChamarDataServiceComDataFormatadaEAtualizarLiveData() {
        // Act
        // CORRIGIDO: Referencia o método checkAvailability
        viewModel.checkAvailability(areaId, testDate)

        // 1. Assert: Verifica a chamada ao DataService
        verify(mockDataService).checkAvailability(areaId, expectedDateString)

        // 2. Assert: Verifica o LiveData de status
        // CORRIGIDO: Referencia o LiveData availabilityStatus
        val statusLiveData = viewModel.availabilityStatus.value
        assertNotNull(statusLiveData)
        assertTrue(statusLiveData!!.isAvailable)
        assertEquals(mockAvailableStatus, statusLiveData)
    }
}