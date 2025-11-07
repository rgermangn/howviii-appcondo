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

    // Regra para executar tarefas assíncronas imediatamente no LiveData
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockDataService: DataService
    private lateinit var viewModel: ReservationViewModel

    // Dados simulados para o teste
    private val areaId = 10
    private val testDate: Calendar = Calendar.getInstance().apply {
        set(2025, Calendar.DECEMBER, 5) // 05/12/2025
    }
    private val expectedDateString = "05/12/2025" // Formato esperado pelo DataService

    // Status de MOCK 1: Disponível
    private val mockAvailableStatus = AvailabilityStatus(isAvailable = true, residentName = null, residentUnit = null)

    // Status de MOCK 2: Reservado (Indisponível)
    private val mockReservedStatus = AvailabilityStatus(
        isAvailable = false,
        residentName = "João Silva",
        residentUnit = "A-101"
    )

    @Before
    fun setup() {
        mockDataService = mock(DataService::class.java)

        // Configura o mock do DataService para retornar um status DISPONÍVEL por padrão
        // (O teste de indisponibilidade irá reconfigurar o mock)
        `when`(mockDataService.checkAvailability(areaId, expectedDateString))
            .thenReturn(mockAvailableStatus)

        // Inicializa o View Model com o mock
        viewModel = ReservationViewModel(mockDataService)
    }

    @Test
    fun checkAvailability_deveChamarDataServiceComDataFormatadaEAtualizarLiveDataParaDisponivel() {
        // Arrange: O mock já está configurado para retornar mockAvailableStatus

        // Act
        viewModel.checkAvailability(areaId, testDate)

        // 1. Assert: Verifica se o DataService foi chamado com os argumentos corretos
        verify(mockDataService).checkAvailability(areaId, expectedDateString)

        // 2. Assert: Verifica se o LiveData de status foi atualizado corretamente
        val statusLiveData = viewModel.availabilityStatus.value
        assertNotNull(statusLiveData)
        // Deve ser 'true' para o status de 'disponível'
        assertTrue(statusLiveData!!.isAvailable)
    }

    // CAL-T4 NOVO TESTE: Verifica se o ViewModel passa o objeto AvailabilityStatus completo (com dados do residente)
    @Test
    fun checkAvailability_deveRetornarIndisponivelComDadosDoResidente() {
        // Arrange: Reconfigura o mock para forçar o cenário de RESERVADO
        `when`(mockDataService.checkAvailability(areaId, expectedDateString))
            .thenReturn(mockReservedStatus)

        // Act
        viewModel.checkAvailability(areaId, testDate)

        // Assert 1: Verifica se o LiveData foi atualizado
        val statusLiveData = viewModel.availabilityStatus.value
        assertNotNull(statusLiveData)

        // Assert 2: Verifica a indisponibilidade e os dados do residente
        assertFalse(statusLiveData!!.isAvailable)
        assertEquals("João Silva", statusLiveData.residentName)
        assertEquals("A-101", statusLiveData.residentUnit)
    }
}
