package com.rggn.appcondominio.reservation

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rggn.appcondominio.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReservationUITest {
    // DATA-T5
    @Test
    fun reservationScreen_deveExibirAreaIdRecebido() {
        // Arrange
        val expectedAreaId = 10
        val expectedText = "ID da Área: $expectedAreaId"

        // 1. Cria uma Intent simulando a navegação da Dashboard
        val intent = Intent(ApplicationProvider.getApplicationContext(), ReservationActivity::class.java).apply {
            putExtra(ReservationActivity.EXTRA_AREA_ID, expectedAreaId)
        }

        // 2. Lança a Activity com a Intent simulada
        ActivityScenario.launch<ReservationActivity>(intent)

        // Act & Assert
        onView(withId(R.id.area_id_text_view))
            .check(matches(withText(expectedText)))
    }

    // DATA-T6 / CAL-T2
    @Test
    fun selectDateButton_deveAbrirSeletorEExibirData() {
        // Arrange: Lança a ReservationActivity
        val intent = Intent(ApplicationProvider.getApplicationContext(), ReservationActivity::class.java).apply {
            putExtra(ReservationActivity.EXTRA_AREA_ID, 1) // ID de área simulado
        }
        ActivityScenario.launch<ReservationActivity>(intent)

        // 1. Act: Clica no botão de selecionar data
        onView(withId(R.id.select_date_button)).perform(click())

        // 2. Clica em "OK" no DatePickerDialog que foi aberto
        onView(withText("OK")).perform(click())

        // 3. Assert: Verifica se o campo de texto foi atualizado com a data mockada
        //    pela ReservationActivity: "05/12/2025".
        onView(withId(R.id.selected_date_text_view))
            .check(matches(withText("05/12/2025")))
    }

    // NOVO TESTE: CAL-T2.1 (Teste Vermelho)
    @Test
    fun checkAvailability_indisponivel_deveExibirStatus() {
        // Arrange: Prepara a Intent para a área 10 (Salão de Festas)
        // O DataService está mockado no teste unitário para que a área 10
        // seja INDISPONÍVEL em 05/12/2025
        val areaId = 10
        val intent = Intent(ApplicationProvider.getApplicationContext(), ReservationActivity::class.java).apply {
            putExtra(ReservationActivity.EXTRA_AREA_ID, areaId)
        }
        ActivityScenario.launch<ReservationActivity>(intent)

        // 1. Act: Seleciona a data mockada (05/12/2025)
        onView(withId(R.id.select_date_button)).perform(click())
        onView(withText("OK")).perform(click())

        // 2. Act: Clica no botão Verificar Disponibilidade.
        // O código verde (CAL-D4) da Activity será responsável por habilitá-lo.
        onView(withId(R.id.check_availability_button))
            .perform(click())

        // 3. Assert (TESTE VERMELHO): Verifica se o status foi atualizado para INDISPONÍVEL
        // Esta asserção falhará porque a Activity ainda não está observando o ViewModel
        onView(withId(R.id.availability_status_text_view))
            .check(matches(withText("Status: INDISPONÍVEL")))
    }
}
