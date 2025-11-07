package com.rggn.appcondominio.reservation

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.CalendarView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rggn.appcondominio.R
import com.rggn.appcondominio.data.DataService
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class CalendarActivityTest {

    private val targetContext: Context = ApplicationProvider.getApplicationContext()
    private val testAreaId = 10 // Churrasqueira

    private val intent: Intent = Intent(targetContext, CalendarActivity::class.java).apply {
        putExtra(ReservationActivity.EXTRA_AREA_ID, testAreaId)
    }

    @get:Rule
    val activityRule = ActivityScenarioRule<CalendarActivity>(intent)

    private fun selectDate(year: Int, month: Int, day: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(CalendarView::class.java)
            }

            override fun getDescription(): String {
                return "Set date on CalendarView"
            }

            override fun perform(uiController: UiController, view: View) {
                val calendarView = view as CalendarView
                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)
                calendarView.date = calendar.timeInMillis
                uiController.loopMainThreadForAtLeast(500) // Wait for listener
            }
        }
    }


    @Test
    fun CAL_T4_deveExibirNomeDaAreaNoTitulo() {
        val areaName = DataService().getAreaNameById(testAreaId)
        onView(withId(R.id.area_title_text)).check(matches(withText(areaName)))
    }

    @Test
    fun CAL_T4_cliqueEmDataDisponivel_deveExibirStatusDisponivel() {
        // Date known to be available
        onView(withId(R.id.reservation_calendar_view)).perform(selectDate(2025, Calendar.DECEMBER, 10))

        onView(withId(R.id.selected_date_text)).check(matches(withText("Data Selecionada: 10/12/2025")))
        onView(withId(R.id.availability_status_text)).check(matches(withText("DISPONÍVEL")))
        onView(withId(R.id.resident_name_label)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun CAL_T4_cliqueEmDataReservada_deveExibirStatusReservado() {
        // Date known to be reserved
        onView(withId(R.id.reservation_calendar_view)).perform(selectDate(2025, Calendar.DECEMBER, 1))

        onView(withId(R.id.selected_date_text)).check(matches(withText("Data Selecionada: 01/12/2025")))
        onView(withId(R.id.availability_status_text)).check(matches(withText("RESERVADO")))

        onView(withId(R.id.resident_name_label)).check(matches(isDisplayed()))
        onView(withId(R.id.resident_name_text)).check(matches(isDisplayed()))
        onView(withId(R.id.resident_unit_label)).check(matches(isDisplayed()))
        onView(withId(R.id.resident_unit_text)).check(matches(isDisplayed()))

        onView(withId(R.id.resident_name_text)).check(matches(withText("Carlos Pereira")))
        onView(withId(R.id.resident_unit_text)).check(matches(withText("Unidade: Apt 301")))
    }
}
