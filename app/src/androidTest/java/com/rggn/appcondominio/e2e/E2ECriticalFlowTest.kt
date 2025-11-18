package com.rggn.appcondominio.e2e

import android.view.View
import android.widget.CalendarView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rggn.appcondominio.R
import com.rggn.appcondominio.home.DashboardActivity
import com.rggn.appcondominio.auth.LoginActivity
import com.rggn.appcondominio.reservation.CalendarActivity
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class E2ECriticalFlowTest {

    // A jornada começa na tela de Login
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun cleanup() {
        Intents.release()
    }

    @Test
    fun TDD_E2E1_jornadaCritica_deveNavegarEExibirStatus() {
        // --- ETAPA 1: LOGIN ---

        // 1. Digitar e-mail
        onView(withId(R.id.email_edit_text))
            .perform(typeText("user@app.com"), closeSoftKeyboard())

        // 2. Digitar senha
        onView(withId(R.id.password_edit_text))
            .perform(typeText("password"), closeSoftKeyboard())

        // 3. Clicar no botão de Login
        onView(withId(R.id.login_button))
            .perform(click())

        // Assert 1: Navegação para DashboardActivity
        intended(hasComponent(DashboardActivity::class.java.name))


        // --- ETAPA 2: DASHBOARD ---

        // 4. Clicar no primeiro item ("Salão de Festas")
        onView(allOf(withText("Salão de Festas"), isDisplayed()))
            .perform(click())

        // Assert 2: Navegação para CalendarActivity
        intended(hasComponent(CalendarActivity::class.java.name))


        // --- ETAPA 3: CALENDÁRIO ---

        // Assert 3: Título carregado
        onView(withId(R.id.area_title_text))
            .check(matches(withText("Salão de Festas")))

        // 5. Simular a seleção de uma data que está RESERVADA (05/12/2025 no DataService)
        onView(allOf(withId(R.id.reservation_calendar_view), isDisplayed()))
            .perform(simulateDateChange(2025, Calendar.DECEMBER, 5))

        // Assert 4: Status de "RESERVADO" é exibido
        onView(withId(R.id.status_text))
            .check(matches(withText("RESERVADO")))

        // Assert 5: Nome do morador (João Silva) é exibido
        onView(withId(R.id.resident_name_text))
            .check(matches(withText("João Silva")))

        // Assert 6: Unidade (A-101) é exibida
        onView(withId(R.id.resident_unit_text))
            .check(matches(withText("Unidade: A-101")))

        // FIM DA JORNADA
    }

    // Ação customizada para simular a seleção de uma data no CalendarView
    private fun simulateDateChange(year: Int, month: Int, day: Int) = object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return isAssignableFrom(CalendarView::class.java)
        }

        override fun getDescription(): String {
            return "Simulate date change on CalendarView"
        }

        override fun perform(uiController: UiController?, view: View?) {
            val calendarView = view as CalendarView
            val calendar = Calendar.getInstance().apply { set(year, month, day) }
            calendarView.date = calendar.timeInMillis
            // Aciona o listener
            calendarView.onDateChangeListener?.onSelectedDayChange(
                calendarView,
                year,
                month,
                day
            )
        }
    }
}