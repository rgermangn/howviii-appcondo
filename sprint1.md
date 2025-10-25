\### SPRINT 1: Infraestrutura e Autenticação (Semanas 1 e 2)



\*\*Meta do Sprint:\*\* \*\*Entregar o fluxo de login funcional e seguro, e a estrutura do aplicativo (navegação).\*\*



| Ordem | \*\*Item (Tarefa)\*\*                                                                                        | \*\*Épico\*\*          | Prioridade |

| ----- | -------------------------------------------------------------------------------------------------------- | ------------------ | ---------- |

| \*\*1\*\* | SETUP-A1: Instalação e configuração do Android Studio, SDKs e criação do Emulador Android.               | Infraestrutura     |            |

| \*\*2\*\* | SETUP-A2: Configuração do projeto Kotlin/Gradle e integração do JUnit para testes de unidade.            | TDD/Infra          |            |

| \*\*3\*\* | AUTH-T1: Escrever testes de unidade (JUnit) para a função de validação de credenciais (\_login mockado\_). | Autenticação (TDD) | P1         |

| \*\*4\*\* | AUTH-D1: Implementar a função de autenticação (Kotlin/Mock) para passar nos testes.                      | Autenticação       | P1         |

| \*\*5\*\* | AUTH-T2: Escrever testes de UI (Espresso) para a tela de Login.                                          | Autenticação (TDD) | P1         |

| \*\*6\*\* | AUTH-D2: Criar a interface (Layout XML e Activity) da tela de Login (campos e botão).                    | Autenticação       | P1         |

| \*\*7\*\* | NAV-T1: Escrever o teste da navegação entre login e Dashboard/Calendário                                 | Navegação (TDD)    | P2, P4     |

| \*\*8\*\* | NAV-D1: Implementar a navegação (Navigation Component) entre Login e Calendário.                         | Navegação          | P2, P4     |



\### SETUP-A1

1\. \*\*Instalação Android Studio/SDKs\*\*



\### SETUP-A2

1\. \*\*Criar o novo projeto\*\*

2\. \*\*Verificar configuração de testes:\*

 	- no arquivo build.gradle.kts (:app) deve conter o junit nas dependências

3\. \*\*Run:\*\*

 	- Com o "Hello World" na tela do emulador, você confirmou:

 		- A instalação do Android Studio/SDKs está perfeita.

 		- O Gradle (sistema de build) está funcionando.

 		- O Kotlin está sendo compilado corretamente.

 		- O Emulador está integrado ao seu fluxo de trabalho.



\### AUTH-T1: TDD teste vermelho

1\. Criar o package `auth` em `app/src/main/java/com.rggn.appcondominio`

2\. Criar o package de teste `auth` em `app/src/test/java/com.rggn.appcondominio`

3\. Criar o arquivo `AuthUnitTest` dentro do pacote criado acima.

4\. Escrever o teste:

```Kotlin

package com.rggn.appcondominio.auth



import org.junit.Test

import org.junit.Assert.\\\*



class AuthUnitTest {



\&nbsp;   @Test

\&nbsp;   fun login\\\_deveRetornarSucesso\\\_comCredenciaisValidas() {

\&nbsp;       // Arrange

\&nbsp;       val username = "morador"

\&nbsp;       val password = "123"



\&nbsp;       // Act \\\& Assert (O erro vai acontecer aqui, pois a função não existe)

\&nbsp;       val result = authenticateUser(username, password)

\&nbsp;       

\&nbsp;       // Assert

\&nbsp;       assertTrue(result)

\&nbsp;   }



\&nbsp;   // Teste 2: Deve retornar falha com credenciais incorretas

\&nbsp;   @Test

\&nbsp;   fun login\\\_deveRetornarFalha\\\_comCredenciaisInvalidas() {

\&nbsp;       // Arrange

\&nbsp;       val username = "visitante"

\&nbsp;       val password = "senhaerrada"



\&nbsp;       // Act \\\& Assert

\&nbsp;       val result = authenticateUser(username, password)



\&nbsp;       // Assert

\&nbsp;       assertFalse(result)

\&nbsp;   }

}

```

5\. Executar o teste: Erros `Unresolved reference: 'authenticateUser'`



\### AUTH-D1: Implementação mínima (verde)

1\. Criar o arquivo de lógica `app/src/main/java/com/rggn.appcondominio/auth/AuthService.kt`

 	- Para simplicidade, vamos criar a função como uma \*\*função de topo de arquivo\*\* (Kotlin Top-Level Function), eliminando a necessidade da classe `AuthService` por enquanto.

```Kotlin

package com.rggn.appcondominio.auth



fun authenticateUser(username: String, password: String): Boolean {

\&nbsp;   return username == "morador" \\\&\\\& password == "123"

}

```

2\. Executar o teste: Sucesso

3\. Refatorar para classe, padrão orientado a objetos.

 	1. Alterar a função top-level para uma classe:

 	```Kotlin

 	package com.rggn.appcondominio.auth

 

 	class AuthService {

 	    fun authenticateUser(username: String, password: String): Boolean {

 	        return username == "morador" \&\& password == "123"

 	    }

 	}

 	```

 	1. Ajustar o arquivo de teste para reconhecer o método da classe:

```Kotlin

// Em AuthUnitTest.kt



// ...

@Test

fun login\\\_deveRetornarSucesso\\\_comCredenciaisValidas() {

\&nbsp;   // Arrange

\&nbsp;   val username = "morador"

\&nbsp;   val password = "123"

\&nbsp;   val authService = AuthService()



\&nbsp;   // Act

\&nbsp;   val result = authService.authenticateUser(username, password)



\&nbsp;   // Assert

\&nbsp;   assertTrue(result)

}

// ...

```

4\. Executar o teste: Sucesso



\### AUTH-T2: testes de UI espresso (vermelho)

1\. Criar o arquivo de teste

 	- `app/src/androidTest/java/com/rggn.appcondominio/auth/LoginUITest.kt`

2\. Escrever o teste

```Kotlin

package com.rggn.appcondominio.auth  

\&nbsp; 

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

\&nbsp; 

@RunWith(AndroidJUnit4::class)  

class LoginUITest {  

\&nbsp; 

\&nbsp;   @get:Rule  

\&nbsp;   val activityRule = ActivityScenarioRule(LoginActivity::class.java)  

\&nbsp; 

\&nbsp;   @Test  

fun login\\\_withValidCredentials\\\_showsSuccessMessage() {  

\&nbsp;   // Arrange

\&nbsp;   onView(withId(R.id.email\\\_edit\\\_text)).perform(replaceText("morador@teste.com"))  

\&nbsp;   onView(withId(R.id.password\\\_edit\\\_text)).perform(replaceText("123"))  

\&nbsp; 

\&nbsp;   // Act  

\&nbsp;   onView(withId(R.id.login\\\_button)).perform(click())  

\&nbsp; 

\&nbsp;   // Assert

\&nbsp;   onView(withId(R.id.status\\\_text\\\_view)).check(matches(withText("Login bem-sucedido")))  

}

```

3\. Executar o teste: Erro



\### AUTH-D2: Implementação mínima (verde)

1\. Criar layout XML (mock interface) `app/src/main/res/layout/activity\\\_login.xml`

 	```XML

<?xml version="1.0" encoding="utf-8"?>  

<LinearLayout

    xmlns:android="http://schemas.android.com/apk/res/android"

    android:layout\_width="match\_parent"

    android:layout\_height="match\_parent"

    android:orientation="vertical"

    android:padding="32dp"

    android:gravity="center\_vertical">

 

    <TextView        android:layout\_width="wrap\_content"

        android:layout\_height="wrap\_content"

        android:text="@string/app\_name"

        android:textSize="24sp"

        android:textStyle="bold"

        android:layout\_gravity="center\_horizontal"

        android:layout\_marginBottom="48dp"/>

 

    <EditText        android:id="@+id/email\_edit\_text"

        android:layout\_width="match\_parent"

        android:layout\_height="wrap\_content"

        android:hint="@string/email"

        android:inputType="textEmailAddress"

        android:autofillHints="emailAddress"

        android:minHeight="48dp"

        android:layout\_marginBottom="16dp"/>

 

    <EditText        android:id="@+id/password\_edit\_text"

        android:layout\_width="match\_parent"

        android:layout\_height="wrap\_content"

        android:hint="@string/password"

        android:inputType="textPassword"

        android:autofillHints="password"

        android:minHeight="48dp"

        android:layout\_marginBottom="32dp"/>

 

    <Button        android:id="@+id/login\_button"

        android:layout\_width="match\_parent"

        android:layout\_height="wrap\_content"

        android:text="@string/login"/>

 

    <TextView        android:id="@+id/status\_text\_view"

        android:layout\_width="wrap\_content"

        android:layout\_height="wrap\_content"

        android:layout\_gravity="center\_horizontal"

        android:layout\_marginTop="16dp"/>

 

</LinearLayout>

 	```

2\. Criar a classe ``app/src/main/java/com.rggn.appcondominio/auth/LoginActivity.kt

 	```Kotlin

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

1\. Ajustar o teste: Adicionar testes para credenciais invalidas, campos vazios

2\. Adicionar `<activity android:name=".auth.LoginActivity" android:exported="true"/>` dentro da tag `<application>` em `app/src/main/AndroidManifest.xml`

3\. Executar o teste de UI: Sucesso





\## NAV-T1 (vermelho)

1\. Criar a dashboard activity e  de layout, de modo simples:

 	-  `app/src/main/java/com/rggn.appcondominio/home/DashboardActivity.kt`

 	- `app/src/main/res/layout/activity\\\_dashboard.xml`

2\. Adicionar ao AndroidManifest

3\. Escrever teste da navegação do Login para Dashboard no LoginUITest com espresso-intents

4\. Executar o teste: Erro Intent not found



\## NAV-D1 (verde)

1\. Implementar navegação no LoginActivity

 	- importar a DashboardActivity e adicionar o Intent no setOnClickListener

2\. Comentar a antiga mensagem de "Login bem-sucedido" e o teste que verificava essa mensagem.

3\. Executar o teste: Sucesso

4\. Refatoração:

 	- Desabilitar ação do botão quando os campos estiverem vazios

 	- Adicionar teste que garante que o botão só pode ser clicado se ambos os campos estiverem preenchidos, testar e Error, OK

 	- Implementar a lógica para detectar mudanças de texto e atualizar o estado do botão.

 	- Comentar o antigo teste de campos vazios.

5\. Executar o teste: Sucesso

