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

        `when`(mockDataService.getCommonAreas()).thenReturn(areaList)

        viewModel = DashboardViewModel(mockDataService)
    }

    @Test
    fun loadAreas_deveChamarDataServiceEExporResultado() {

        verify(mockDataService).getCommonAreas()

        val areasLiveData = viewModel.areas.value
        assertNotNull(areasLiveData)
        assertEquals(2, areasLiveData?.size)
        assertEquals("Salão de Festas", areasLiveData?.get(0)?.name)
    }
}