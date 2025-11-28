package com.rggn.appcondominio.data

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

class DataServiceTest {

    @Test
    fun getCommonAreas_deveRetornarListaNaoVazia() {
        val dataService = DataService()

        val areas = dataService.getCommonAreas()

        assertNotNull(areas)
        assertEquals(2, areas.size)
        assertEquals("Salão de Festas", areas[0].name)
    }
}