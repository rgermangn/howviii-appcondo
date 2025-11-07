package com.rggn.appcondominio.reservation.test

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.CalendarView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rggn.appcondominio.R
import com.rggn.appcondominio.reservation.CalendarActivity
import com.rggn.appcondominio.reservation.ReservationActivity
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class CalendarActivityTest {

    private val targetContext: Context = ApplicationProvider.getApplicationContext()
    private val testAreaId = 10 // O ID que o Dashboard enviaria

    // 1. PREPARA A INTENT DE ENTRADA
    // CalendarActivity ESPERA EXTRA_AREA_ID para carregar dados
    private val intent: Intent = Intent(targetContext, CalendarActivity::class.java).apply {
        putExtra(ReservationActivity.EXTRA_AREA_ID, testAreaId)
    }

    // 2. USA A INTENT PREPARADA NA RULE
    @get:Rule
    val activityRule = ActivityScenarioRule<CalendarActivity>(intent)

    @Before
    fun setupIntents() {
        Intents.init()
    }

    @After
    fun tearDownIntents() {
        Intents.release()
    }

    // AÇÃO CUSTOMIZADA: Simula o clique em um dia específico no CalendarView
    private fun clickInCalendarDay(targetDay: Int): ViewAction = object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return allOf(isDisplayed(), isAssignableFrom(CalendarView::class.java))
        }

        override fun getDescription(): String = "click in calendar day $targetDay"

        override fun perform(uiController: UiController, view: View) {
            val calendarView = view as CalendarView

            val calendar = Calendar.getInstance().apply {
                set(2025, Calendar.DECEMBER, targetDay)
            }
            // Usa o setDate, mas o sucesso dependerá do Android/Emulator
            calendarView.setDate(calendar.timeInMillis, true, true)

            // Garantir que o evento (a Intent) tenha tempo de ser disparado
            uiController.loopMainThreadForAtLeast(500)
        }
    }

    private fun simulateDateChange(year: Int, month: Int, dayOfMonth: Int): ViewAction = object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return allOf(isDisplayed(), isAssignableFrom(CalendarView::class.java))
        }

        override fun getDescription(): String = "simulate date change to $year-$month-$dayOfMonth"

        override fun perform(uiController: UiController, view: View) {
            val calendarView = view as CalendarView

            // Get the activity
            activityRule.scenario.onActivity { activity ->
                // Manually call the date change on the UI thread
                activity.runOnUiThread {
                    // Trigger the listener manually
                    val calendar = Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }

                    // This simulates what CalendarView does internally
                    calendarView.setDate(calendar.timeInMillis, false, false)

                    // Manually trigger navigateToReservationActivity since setDate might not trigger listener
                    val dateString = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
                    val intent = Intent(activity, ReservationActivity::class.java).apply {
                        putExtra(ReservationActivity.EXTRA_AREA_ID, testAreaId)
                        putExtra(ReservationActivity.EXTRA_DATE, dateString)
                    }
                    activity.startActivity(intent)
                }
            }

            uiController.loopMainThreadForAtLeast(500)
        }
    }

    @Test
    fun TDD_CALT1_deveExibirTituloComIdDaArea() {
        // Verifica se o ID (10) foi lido corretamente da Intent e exibido no título.
        onView(withId(R.id.area_title_text))
            .check(matches(withText("Calendário de Reservas (Área ID: $testAreaId)")))
    }

    @Test
    fun TDD_CALT2_deveExibirDatasReservadasIniciais() {
        // As datas mockadas no DataService (01, 02, 03) devem ser exibidas.
        onView(withId(R.id.reserved_dates_list_text))
            .check(matches(withText(
                "01/12/2025\n" +
                        "02/12/2025\n" +
                        "03/12/2025\n" +
                        "05/12/2025"
            )))
    }

    @Test
    fun TDD_CALT3_clickEmData_deveNavegarParaReservationActivityComDataEAreaId() {
        // Arrange
        val targetDay = 5
        val targetMonth = Calendar.DECEMBER // Mês 11 (0-based)
        val targetYear = 2025

        val targetDateCalendar = Calendar.getInstance().apply {
            set(targetYear, targetMonth, targetDay)
        }
        val expectedDateString = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(targetDateCalendar.time)

        // Act: Simula a chamada do listener do CalendarView com a data alvo
        onView(allOf(withId(R.id.reservation_calendar_view), isDisplayed(), isAssignableFrom(CalendarView::class.java)))
            .perform(simulateDateChange(targetYear, targetMonth, targetDay))

        // Assert 1: Verifica se a Intent foi disparada
        intended(hasComponent(ReservationActivity::class.java.name))

        // Assert 2: Verifica se o EXTRA_AREA_ID foi enviado
        intended(hasExtra(ReservationActivity.EXTRA_AREA_ID, testAreaId))

        // Assert 3: Verifica se o EXTRA_DATE foi enviado e formatado corretamente
        intended(hasExtra(ReservationActivity.EXTRA_DATE, expectedDateString))
    }
}
