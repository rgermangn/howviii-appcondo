package com.rggn.appcondominio.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rggn.appcondominio.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardUITest {

    // Regra para iniciar a DashboardActivity
    @get:Rule
    val activityRule = ActivityScenarioRule(DashboardActivity::class.java)

    @Test
    fun commonAreasList_deveExibirPrimeiraAreaComum() {
        // Assert (ESTE PASSO VAI FALHAR!)

        // O teste espera encontrar o texto "Salão de Festas" (o primeiro nome mockado no DataService)
        // visível na tela. A falha ocorrerá porque a Activity ainda não conectou o ViewModel
        // e não configurou o RecyclerView.
        onView(withText("Salão de Festas"))
            .check(matches(isDisplayed()))
    }
}