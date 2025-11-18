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

    // Regra para lançar a InformationActivity antes de cada teste
    @get:Rule
    val activityRule = ActivityScenarioRule(InformationActivity::class.java)

    @Test
    // INFO-T1: Verifica se a tela renderiza corretamente os títulos e o conteúdo de Regras e Custos (INFO-D1)
    fun TDD_INFOT1_deveRenderizarConteudoRegrasECustos() {
        // 1. Verificar o Título Principal
        onView(withText("Informações das Áreas Comuns"))
            .check(matches(isDisplayed()))

        // 2. Verificar o título da seção de Regras
        onView(withText("Regras Gerais de Uso"))
            .check(matches(isDisplayed()))

        // 3. Verificar o conteúdo das Regras (Usando o ID)
        onView(withId(R.id.text_regras_gerais))
            .check(matches(isDisplayed()))
            .check(matches(withSubstring("no mínimo 48 horas de antecedência"))) // Verifica parte do conteúdo

        // 4. Verificar o título da seção de Custos
        onView(withText("Custos e Taxas de Reserva"))
            .check(matches(isDisplayed()))

        // 5. Verificar o conteúdo dos Custos (Usando o ID)
        onView(withId(R.id.text_custos_taxas))
            .check(matches(isDisplayed()))
            .check(matches(withSubstring("R$ 150,00"))) // Verifica parte do conteúdo

        // 6. Verificar se o Placeholder de Itens e Equipamentos (INFO-D2) está visível
        onView(withText("Itens e Equipamentos Disponíveis (A ser detalhado em INFO-D2)"))
            .check(matches(isDisplayed()))
    }
}