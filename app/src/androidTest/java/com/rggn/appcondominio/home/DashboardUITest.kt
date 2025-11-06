package com.rggn.appcondominio.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rggn.appcondominio.reservation.ReservationActivity // Importa a nova Activity
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(DashboardActivity::class.java)

    @Before
    fun setupIntents() {
        // Inicializa o Espresso-Intents para interceptar chamadas a startActivity()
        Intents.init()
    }

    @After
    fun tearDownIntents() {
        // Libera o Espresso-Intents
        Intents.release()
    }

    @Test
    fun commonAreasList_deveExibirPrimeiraAreaComum() {
        // Já passou no DATA-D3.
        onView(withText("Salão de Festas"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun clickArea_deveNavegarParaReservationActivityComID() {
        // Arrange: Encontra o item "Salão de Festas"
        val firstAreaName = "Salão de Festas"
        val expectedAreaId = 10 // ID mockado no DataService para o Salão de Festas

        // Act: Clica no item da lista
        onView(withText(firstAreaName)).perform(click())

        // Assert: Verifica se uma Intent foi disparada
        intended(
            allOf(
                // 1. Verifica se a Intent é para a ReservationActivity
                hasComponent(ReservationActivity::class.java.name),
                // 2. Verifica se a Intent carrega o ID correto da área (10)
                hasExtra(ReservationActivity.EXTRA_AREA_ID, expectedAreaId)
            )
        )
    }
}
