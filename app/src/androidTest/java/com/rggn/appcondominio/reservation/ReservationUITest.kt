package com.rggn.appcondominio.reservation

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rggn.appcondominio.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReservationUITest {

    private val targetContext: Context = ApplicationProvider.getApplicationContext()
    private val testAreaId = 10
    private val testDateString = "05/12/2025"

    // Prepara a Intent para iniciar a ReservationActivity com os dados de área e data
    private val intent = Intent(targetContext, ReservationActivity::class.java).apply {
        putExtra(ReservationActivity.EXTRA_AREA_ID, testAreaId)
        putExtra(ReservationActivity.EXTRA_DATE, testDateString)
    }

    @get:Rule
    val activityRule = ActivityScenarioRule<ReservationActivity>(intent)

    @Test
    fun TDD_RESU1_deveExibirDadosRecebidosNaIntent() {
        // Arrange: Os dados esperados são injetados via Intent (setup acima)

        // ASSERT 1: Verifica se o ID da Área Comum foi exibido corretamente
        onView(withId(R.id.area_id_text_view))
            .check(matches(withText("ID da Área: $testAreaId")))

        // ASSERT 2: Verifica se a Data Selecionada foi exibida corretamente
        onView(withId(R.id.selected_date_text_view))
            .check(matches(withText("$testDateString")))

        // ASSERT 3: Verifica se o status inicial está como 'Carregando' (Antes de chamar o ViewModel)
        onView(withId(R.id.availability_status_text_view))
            .check(matches(withText("RESERVADO")))
    }
}
