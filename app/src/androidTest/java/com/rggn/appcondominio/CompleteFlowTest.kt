package com.rggn.appcondominio

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rggn.appcondominio.R
import com.rggn.appcondominio.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CompleteFlowTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testCompleteFlow() {
        // 1. Login
        onView(withId(R.id.username)).perform(typeText("morador"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("123"), closeSoftKeyboard())
        onView(withId(R.id.login_button)).perform(click())

        // 2. Navegar para Informações e verificar
        onView(withId(R.id.btn_information)).perform(click())
        onView(withText("Informações das Áreas Comuns")).check(matches(isDisplayed()))
        onView(withText("Regras Gerais de Uso")).check(matches(isDisplayed()))
        onView(withText("Custos e Taxas de Reserva")).check(matches(isDisplayed()))
        onView(withText("Itens e Equipamentos Disponíveis")).check(matches(isDisplayed()))

        // 3. Voltar para o Dashboard
        onView(withId(android.R.id.content)).perform(click()) // Maneira genérica de voltar

        // 4. Verificar as áreas comuns e navegar para o calendário
        // Área 1: Salão de Festas
        onView(withText("Salão de Festas")).perform(click())
        onView(withId(R.id.calendar_view)).check(matches(isDisplayed()))
        // TODO: Adicionar verificações de datas e reservas aqui
        onView(withId(android.R.id.content)).perform(click()) // Voltar

        // Área 2: Churrasqueira
        onView(withText("Churrasqueira")).perform(click())
        onView(withId(R.id.calendar_view)).check(matches(isDisplayed()))
        // TODO: Adicionar verificações de datas e reservas aqui
        onView(withId(android.R.id.content)).perform(click()) // Voltar
    }
}
