package com.rggn.appcondominio.auth

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
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
class LoginUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun login_withValidCredentials_showsSuccessMessage() {
        // Arrange
        onView(withId(R.id.email_edit_text)).perform(replaceText("morador@teste.com"))
        onView(withId(R.id.password_edit_text)).perform(replaceText("123"))

        // Act
        onView(withId(R.id.login_button)).perform(click())

        // Assert
        onView(withId(R.id.status_text_view)).check(matches(withText("Login bem-sucedido")))
    }

    @Test
    fun login_withInvalidCredentials_showFailMessage() {
        // Arrange
        onView(withId(R.id.email_edit_text)).perform(replaceText("visitante@teste.com"))
        onView(withId(R.id.password_edit_text)).perform(replaceText("senhaerrada"))

        // Act
        onView(withId(R.id.login_button)).perform(click())

        // Assert
        onView(withId(R.id.status_text_view)).check(matches(withText("Credenciais inválidas")))

    }
}
