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
        // O erro 'Unresolved reference: getCommonAreas' aparecerá aqui!
        val areas = dataService.getCommonAreas()

        // Assert (Esperamos pelo menos 2 áreas comuns simuladas)
        assertNotNull(areas)
        assertEquals(2, areas.size)
        assertEquals("Salão de Festas", areas[0].name)
    }

    @Test
    fun getResidents_deveRetornarListaNaoVazia() {
        // Arrange
        val dataService = DataService()

        // Act
        // O erro 'Unresolved reference: getResidents' vai aparecer aqui!
        val residents = dataService.getResidents()

        // Assert (Esperamos que a lista tenha pelo menos 3 itens simulados)
        assertNotNull(residents)
        assertEquals(3, residents.size)
        assertEquals("João Silva", residents[0].name)
    }
}