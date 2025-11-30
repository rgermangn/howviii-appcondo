package com.rggn.appcondominio.info

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rggn.appcondominio.R
import com.rggn.appcondominio.info.InformationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InformationActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(InformationActivity::class.java)

    @Test
    fun TDD_INFOT1_deveRenderizarConteudoRegrasECustos() {
        onView(withText("Informações das Áreas Comuns"))
            .check(matches(isDisplayed()))

        onView(withText("Regras Gerais de Uso"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.text_regras_gerais))
            .check(matches(isDisplayed()))
            .check(matches(withSubstring("no mínimo 48 horas de antecedência")))

        onView(withText("Custos e Taxas de Reserva"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.text_custos_taxas))
            .check(matches(isDisplayed()))
            .check(matches(withSubstring("R$ 150,00")))

        onView(withText("Itens e Equipamentos Disponíveis"))
            .check(matches(isDisplayed()))
    }
}