package com.rggn.appcondominio.auth

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.rggn.appcondominio.home.DashboardActivity
import com.rggn.appcondominio.R
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun login_withInvalidCredentials_showFailMessage() {
        onView(withId(R.id.email_edit_text)).perform(replaceText("visitante@teste.com"))
        onView(withId(R.id.password_edit_text)).perform(replaceText("senhaerrada"))

        onView(withId(R.id.login_button)).perform(click())

        onView(withId(R.id.status_message)).check(matches(withText("Credenciais inválidas")))

    }

    @Test
    fun login_withValidCredentials_shouldNavigateToDashboard() {
        onView(withId(R.id.email_edit_text)).perform(replaceText("morador@teste.com"))
        onView(withId(R.id.password_edit_text)).perform(replaceText("123"))

        Intents.init()

        onView(withId(R.id.login_button)).perform(click())

        intended(hasComponent(DashboardActivity::class.java.name))

        Intents.release()
    }

    @Test
    fun loginButton_shouldBeDisabled_whenFieldsAreEmpty() {
        onView(withId(R.id.login_button))
            .check(matches(not(isEnabled())))
    }

    @Test
    fun loginButton_shouldBeEnabled_whenBothFieldsAreFilled() {
        onView(withId(R.id.email_edit_text)).perform(replaceText("a"))

        onView(withId(R.id.login_button))
            .check(matches(not(isEnabled())))

        onView(withId(R.id.password_edit_text)).perform(replaceText("b"))

        onView(withId(R.id.login_button))
            .check(matches(isEnabled()))
    }
}