package com.rggn.appcondominio.data

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

class DataServiceTest {

    @Test
    fun getCommonAreas_deveRetornarListaNaoVazia() {
        // Arrange
        val dataService = DataService()

        // Act
        val areas = dataService.getCommonAreas()

        // Assert (Esperamos pelo menos 2 áreas comuns simuladas)
        assertNotNull(areas)
        assertEquals(2, areas.size)
        assertEquals("Salão de Festas", areas[0].name)
    }
}