package com.rggn.appcondominio.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rggn.appcondominio.data.DataService
import com.rggn.appcondominio.data.Resident
import org.junit.Rule
import org.junit.Test
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertEquals

class DashboardViewModelTest {

    // Regra necessária para LiveData, pois o teste roda fora da thread principal do Android
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockDataService: DataService
    private lateinit var viewModel: DashboardViewModel

    // Dados simulados
    private val residentList = listOf(
        Resident(id = 10, name = "Carlos", unit = "202"),
        Resident(id = 20, name = "Ana", unit = "305")
    )

    @Before
    fun setup() {
        // Inicializa o mock do DataService
        mockDataService = mock(DataService::class.java)

        // Configura o mock: QUANDO o getResidents for chamado, RETORNE a nossa lista simulada
        `when`(mockDataService.getResidents()).thenReturn(residentList)

        // Inicializa o View Model com o mock
        // O erro 'Unresolved reference: residents' aparecerá AQUI
        viewModel = DashboardViewModel(mockDataService)
    }

    @Test
    fun loadResidents_deveChamarDataServiceEExporResultado() {
        // Arrange
        // (Já feito no setup)

        // Act
//        viewModel.loadResidents()

        // Assert: Verifica se o serviço foi chamado
        verify(mockDataService).getResidents()

        // Assert: Verifica se o LiveData foi atualizado com os dados
        // O erro 'Unresolved reference: residents' aparecerá AQUI
        val residentsLiveData = viewModel.residents.value
        assertNotNull(residentsLiveData)
        assertEquals(2, residentsLiveData?.size)
        assertEquals("Carlos", residentsLiveData?.get(0)?.name)
    }
}