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
class CalendarActivityTest {

    // PREPARAÇÃO CRUCIAL:
    // 1. Obtém o contexto da aplicação.
    private val targetContext: Context = ApplicationProvider.getApplicationContext()

    // 2. Cria a Intent necessária para iniciar a Activity.
    // Passamos o ID 10, que é a área que tem datas reservadas mockadas no DataService.
    private val intent = Intent(targetContext, CalendarActivity::class.java).apply {
        // Usa a constante para o EXTRA_AREA_ID
        putExtra(ReservationActivity.EXTRA_AREA_ID, 10)
    }

    // 3. Usa a Intent criada para configurar a regra de lançamento da Activity.
    @get:Rule
    val activityRule = ActivityScenarioRule<CalendarActivity>(intent)

    @Test
    fun fetchReservedDates_deveExibirListaDeDatasReservadas() {
        // Arrange: Datas mockadas no DataService para o ID 10
        val expectedText = "01/12/2025\n02/12/2025\n03/12/2025"

        // Act & Assert: Verifica se o TextView contém as datas esperadas
        onView(withId(R.id.reserved_dates_list_text))
            .check(matches(withText(expectedText)))
    }

    @Test
    fun fetchReservedDates_deveExibirTituloCorreto() {
        val expectedTitle = "Calendário de Reservas (Área ID: 10)"

        onView(withId(R.id.area_title_text))
            .check(matches(withText(expectedTitle)))
    }
}
