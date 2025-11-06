package com.rggn.appcondominio.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rggn.appcondominio.data.CommonArea
import com.rggn.appcondominio.data.DataService
import org.junit.Rule
import org.junit.Test
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertEquals

class DashboardViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockDataService: DataService
    private lateinit var viewModel: DashboardViewModel

    private val areaList = listOf(
        CommonArea(id = 10, name = "Salão de Festas", capacity = 40),
        CommonArea(id = 20, name = "Churrasqueira Gourmet", capacity = 20)
    )

    @Before
    fun setup() {
        mockDataService = mock(DataService::class.java)

        // NOVO: Configura o mock para getCommonAreas()
        `when`(mockDataService.getCommonAreas()).thenReturn(areaList)

        // Inicializa o View Model com o mock
        viewModel = DashboardViewModel(mockDataService)
    }

    @Test
    fun loadAreas_deveChamarDataServiceEExporResultado() {
        // Arrange
        // (O ViewModel já chamou loadAreas() no seu bloco init{})

        // Act (Não precisamos chamar novamente, pois o init() já fez)

        // Assert: Verifica se o serviço correto foi chamado APENAS UMA VEZ
        verify(mockDataService).getCommonAreas()

        // Assert: Verifica se o LiveData 'areas' foi atualizado
        val areasLiveData = viewModel.areas.value
        assertNotNull(areasLiveData)
        assertEquals(2, areasLiveData?.size)
        assertEquals("Salão de Festas", areasLiveData?.get(0)?.name)
    }
}
