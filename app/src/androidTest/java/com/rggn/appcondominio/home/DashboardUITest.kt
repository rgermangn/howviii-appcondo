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
import com.rggn.appcondominio.reservation.CalendarActivity
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
        Intents.init()
    }

    @After
    fun tearDownIntents() {
        Intents.release()
    }

    @Test
    fun commonAreasList_deveExibirPrimeiraAreaComum() {
        onView(withText("Salão de Festas"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun clickArea_deveNavegarParaCalendarActivityComID() {
        val firstAreaName = "Salão de Festas"
        val expectedAreaId = 10

        onView(withText(firstAreaName)).perform(click())

        intended(
            allOf(
                hasComponent(CalendarActivity::class.java.name),
                hasExtra(CalendarActivity.EXTRA_AREA_ID, expectedAreaId)
            )
        )
    }
}
