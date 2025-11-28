package com.rggn.appcondominio.reservation

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rggn.appcondominio.R
import com.rggn.appcondominio.data.DataService
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class CalendarActivityTest {

    private val targetContext: Context = ApplicationProvider.getApplicationContext()
    private val testAreaId = 10

    private val intent: Intent = Intent(targetContext, CalendarActivity::class.java).apply {
        putExtra(CalendarActivity.EXTRA_AREA_ID, testAreaId)
    }

    @get:Rule
    val activityRule = ActivityScenarioRule<CalendarActivity>(intent)

    @Test
    fun CAL_T4_deveExibirNomeDaAreaNoTitulo() {
        val areaName = DataService().getAreaNameById(testAreaId)
        onView(withId(R.id.area_title_text)).check(matches(withText(areaName)))
    }

    @Test
    fun CAL_T4_cliqueEmDataDisponivel_deveExibirStatusDisponivel() {
        activityRule.scenario.onActivity { activity ->
            activity.runOnUiThread {
                activity.onDateSelected(2025, Calendar.DECEMBER, 10)
            }
        }

        onView(withId(R.id.selected_date_text)).check(matches(withText("Data Selecionada: 10/12/2025")))
        onView(withId(R.id.status_text)).check(matches(withText("DISPONÍVEL")))
        onView(withId(R.id.reserved_by_label)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun CAL_T4_cliqueEmDataReservada_deveExibirStatusReservado() {
        activityRule.scenario.onActivity { activity ->
            activity.runOnUiThread {
                activity.onDateSelected(2025, Calendar.DECEMBER, 5)
            }
        }

        onView(withId(R.id.selected_date_text)).check(matches(withText("Data Selecionada: 05/12/2025")))
        onView(withId(R.id.status_text)).check(matches(withText("RESERVADO")))

        onView(withId(R.id.reserved_by_label)).check(matches(isDisplayed()))
        onView(withId(R.id.resident_name_text)).check(matches(isDisplayed()))
        onView(withId(R.id.resident_unit_label)).check(matches(isDisplayed()))
        onView(withId(R.id.resident_unit_text)).check(matches(isDisplayed()))

        onView(withId(R.id.resident_name_text)).check(matches(withText("João Silva")))
        onView(withId(R.id.resident_unit_text)).check(matches(withText("Unidade: A-101")))
    }
}