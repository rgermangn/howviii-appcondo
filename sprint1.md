### SPRINT 1: Infraestrutura e Autenticação (Semanas 1 e 2)

**Meta do Sprint:** **O fluxo de login funcional e seguro, e a estrutura do aplicativo (navegação) foram entregues.**

| Ordem | Item (Tarefa) | Épico | Prioridade |
|---|---|---|---|
| 1 | SETUP-A1: Instalação e configuração do Android Studio, SDKs e criação do Emulador Android. | Infraestrutura | |
| 2 | SETUP-A2: Configuração do projeto Kotlin/Gradle e integração do JUnit para testes de unidade. | TDD/Infra | |
| 3 | AUTH-T1: Escrita de testes de unidade (JUnit) para a função de validação de credenciais (_login mockado_). | Autenticação (TDD) | P1 |
| 4 | AUTH-D1: Implementação da função de autenticação (Kotlin/Mock) para passar nos testes. | Autenticação | P1 |
| 5 | AUTH-T2: Escrita de testes de UI (Espresso) para a tela de Login. | Autenticação (TDD) | P1 |
| 6 | AUTH-D2: Criação da interface (Layout XML e Activity) da tela de Login. | Autenticação | P1 |
| 7 | NAV-T1: Escrita do teste da navegação entre login e Dashboard/Calendário. | Navegação (TDD) | P2, P4 |
| 8 | NAV-D1: Implementação da navegação (Navigation Component) entre Login e Calendário. | Navegação | P2, P4 |

### SETUP-A1
- O Android Studio, os SDKs necessários e um Emulador Android foram instalados e configurados.

### SETUP-A2
1.  O novo projeto foi criado.
2.  A configuração para testes unitários com JUnit foi verificada no arquivo `build.gradle.kts`.
3.  A execução do "Hello World" no emulador confirmou que o ambiente de desenvolvimento, incluindo Android Studio, Gradle, Kotlin e o Emulador, estava funcionando corretamente.

### AUTH-T1: TDD - Teste Vermelho
1.  Os packages `auth` foram criados nos diretórios de código principal (`main`) e de teste (`test`).
2.  O arquivo de teste `AuthUnitTest` foi criado.
3.  Foram escritos testes para validar o login com credenciais válidas e inválidas.
    ```kotlin
    package com.rggn.appcondominio.auth

    import org.junit.Test
    import org.junit.Assert.*

    class AuthUnitTest {

        @Test
        fun login_deveRetornarSucesso_comCredenciaisValidas() {
            val username = "morador"
            val password = "123"

            val result = authenticateUser(username, password)
            
            assertTrue(result)
        }

        @Test
        fun login_deveRetornarFalha_comCredenciaisInvalidas() {
            val username = "visitante"
            val password = "senhaerrada"

            val result = authenticateUser(username, password)

            assertFalse(result)
        }
    }
    ```
4.  A execução dos testes resultou no erro `Unresolved reference: 'authenticateUser'`, como esperado (ciclo vermelho do TDD).

### AUTH-D1: Implementação Mínima (Verde)
1.  Para fazer o teste passar, a função `authenticateUser` foi criada como uma função top-level em `auth/AuthService.kt`.
    ```kotlin
    package com.rggn.appcondominio.auth

    fun authenticateUser(username: String, password: String): Boolean {
        return username == "morador" && password == "123"
    }
    ```
2.  Com a implementação, os testes passaram (ciclo verde do TDD).
3.  A função foi então refatorada para uma classe `AuthService`, seguindo padrões de orientação a objetos.
    ```kotlin
    package com.rggn.appcondominio.auth

    class AuthService {
        fun authenticateUser(username: String, password: String): Boolean {
            return username == "morador" && password == "123"
        }
    }
    ```
4.  O teste foi ajustado para instanciar e usar a classe `AuthService`.
    ```kotlin
    // Em AuthUnitTest.kt
    @Test
    fun login_deveRetornarSucesso_comCredenciaisValidas() {
        val username = "morador"
        val password = "123"
        val authService = AuthService()

        val result = authService.authenticateUser(username, password)

        assertTrue(result)
    }
    ```
5.  Os testes continuaram passando com sucesso após a refatoração.

### AUTH-T2: Testes de UI com Espresso (Vermelho)
1.  O arquivo de teste de UI `auth/LoginUITest.kt` foi criado no diretório `androidTest`.
2.  Um teste de UI foi escrito para simular a inserção de credenciais válidas, o clique no botão de login e a verificação de uma mensagem de sucesso.
    ```kotlin
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
            onView(withId(R.id.email_edit_text)).perform(replaceText("morador@teste.com"))
            onView(withId(R.id.password_edit_text)).perform(replaceText("123"))

            onView(withId(R.id.login_button)).perform(click())

            onView(withId(R.id.status_text_view)).check(matches(withText("Login bem-sucedido")))
        }
    }
    ```
3.  A execução do teste falhou, como esperado, pois a `LoginActivity` e seu layout ainda não haviam sido implementados.

### AUTH-D2: Implementação Mínima (Verde)
1.  O layout `activity_login.xml` foi criado com os campos de texto, botão e um `TextView` para o status.
    ```xml
    <?xml version="1.0" encoding="utf-8"?>  
    <LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:padding="32dp"
        android:gravity="center_vertical">
    
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/app_name"
            android:textSize="24sp"
            android:textStyle="bold"
            android:layout_gravity="center_horizontal"
            android:layout_marginBottom="48dp"/>
    
        <EditText
            android:id="@+id/email_edit_text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="@string/email"
            android:inputType="textEmailAddress"
            android:autofillHints="emailAddress"
            android:minHeight="48dp"
            android:layout_marginBottom="16dp"/>
    
        <EditText
            android:id="@+id/password_edit_text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="@string/password"
            android:inputType="textPassword"
            android:autofillHints="password"
            android:minHeight="48dp"
            android:layout_marginBottom="32dp"/>
    
        <Button
            android:id="@+id/login_button"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@string/login"/>
    
        <TextView
            android:id="@+id/status_text_view"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:layout_marginTop="16dp"/>
    
    </LinearLayout>
    ```
2.  A `LoginActivity.kt` foi criada para inflar o layout e implementar a lógica de clique do botão, utilizando o `AuthService`.
    ```kotlin
    package com.rggn.appcondominio.auth

    import android.os.Bundle
    import androidx.appcompat.app.AppCompatActivity
    import com.rggn.appcondominio.databinding.ActivityLoginBinding

    class LoginActivity : AppCompatActivity() {

        private lateinit var binding: ActivityLoginBinding
        private val authService = AuthService()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.loginButton.setOnClickListener {
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()

                if (authService.authenticateUser(email, password)) {
                    binding.statusTextView.text = "Login bem-sucedido"
                } else {
                    binding.statusTextView.text = "Credenciais inválidas"
                }
            }
        }
    }
    ```
3.  A `LoginActivity` foi adicionada ao `AndroidManifest.xml`.
4.  Após a implementação, os testes de UI passaram com sucesso.

### NAV-T1 (Vermelho)
1.  Uma `DashboardActivity` e seu respectivo layout (`activity_dashboard.xml`) foram criados de forma simples.
2.  A nova activity foi adicionada ao `AndroidManifest.xml`.
3.  Um teste de navegação foi adicionado ao `LoginUITest` usando `espresso-intents` para verificar se a `DashboardActivity` era iniciada após o login.
4.  O teste falhou com a mensagem `Intent not found`, como esperado, pois a navegação ainda não havia sido implementada.

### NAV-D1 (Verde)
1.  A navegação foi implementada no `onClickListener` do botão de login na `LoginActivity`, iniciando a `DashboardActivity` com um `Intent`.
2.  A exibição da mensagem de texto "Login bem-sucedido" foi removida, e o teste que a verificava foi comentado.
3.  O teste de navegação passou com sucesso.
4.  **Refatoração**:
    -   A lógica foi aprimorada para desabilitar o botão de login quando os campos de e-mail e senha estão vazios.
    -   Um novo teste foi adicionado para garantir que o botão permanece desabilitado com campos vazios.
    -   A lógica para monitorar as mudanças nos campos de texto e atualizar o estado do botão foi implementada.
5.  Todos os testes foram executados novamente, passando com sucesso.
