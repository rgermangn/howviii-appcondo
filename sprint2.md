## DATA-1 (Obtenção de Dados)
Tela: DashboardActivity
Servidor: Simulado
O dashboard deve carregar e exibir as informações dos moradores.
Esta tarefa foi subdividida em tarefas menores para facilitar o desenvolvimento orientado a testes.

### DATA-T1/DET-T1 (Teste Vermelho - Serviço de Dados)
Criar o contrato para o serviço de dados e um teste de unidade para garantir que ele retorne dados mockados (simulados) corretamente.
- Criar arquivo  
	`app/src/main/java/com/rggn.appcondominio/data/Resident.kt`
- Criar o serviço de dados (interface e mock) 
	`app/src/main/java/com/rggn.appcondominio/data/DataService.kt`
- Escrever o teste de unidade (JUnit) 
	`app/src/test/java/com/rggn.appcondominio/data/DataServiceTest.kt`
Executar o teste: Falha, não encontra getResidents (Unresolved reference: getResidents)
### DATA-D1/DET-D1 (Teste Verde - Serviço de Dados)
- Implementar a função getResidents() com dados mockados no DataService.kt para fazer o teste passar.
Executar o teste: Sucesso.

### DATA-T2/CAL-T1 (Teste Vermelho - Dados de Reserva)
O Dashboard deve carregar e exibir a lista de áreas comuns disponíveis para reserva.
- Modelo de dados para as áreas comuns.
`app/src/main/java/com/rggn.appcondominio/data/CommonArea.kt`
- Adicionar o contrato no DataService
`app/src/main/java/com/rggn.appcondominio/data/DataService.kt`
- Escrever o teste
`app/src/test/java/com/rggn.appcondominio/datra/DataServiceTeste.kt`
- Implementar o DashboardViewModel.kt com mockito.
`app/src/main/java/com/rggn.appcondominio/home/DashboardViewModel.kt`
- Por enquanto está injetando o DataService no construtor para facilitar a troca por um mock no teste de unidade.
- Teste vermelho de unidade JUnit com Mockito para simular o comportamento do DataService
`app/src/test/java/com/rggn.appcondominio/home/DashboardViewModelTest.kt`
Executar teste: Falha, Unresolved Reference (loadResidents, residents)
### DATA-D2/CAL-T2 (Código Verde - Obter a Lista de Áreas)
Implementar a função getCommonAreas() no DataService.kt para retornar a lista mockada de CommonArea.
- Em DataService.kt adicionar a função getCommonAreas()
- Implementar o DashboardViewModel
### DATA-T3 (Teste Vermelho - Exibição da Lista de Áreas Comuns)
Exibição na Dashboard
- Preparar os Layouts.
	- Criar: `app/src/main/res/layout/item_area_comum.xml`
- Modificar: `.../layout/activity_dashboard.xml` para incluir o RecyclerView (precisa sincronizar o Gradle) e novo texto.
- Escrever o teste de UI (Espresso) para garantir que o nome da primeira área comum esteja visível.
`app/src/androidTest/java/com/rggn.appcondominio/home/DashboardUITeste.kt`
Executar o teste: Falha NoMatchingViewException
### DATA-D3 (Código Verde - Exibição da Lista)
Implementar lógica mínima para que a DashboardActivity observe o DashboardViewModel e configure o RecyclerView para exibir as áreas comuns.
- Criar o Adapter para vincular os dados do CommonArea ao item_area_comum.xml
`app/src/main/java/com/rggn.appcondominio/home/CommonAreaAdapter.kt`
- Injetar o ViewModel na DashboardActivity e observar o LiveData.
`../DashboardActivity.kt`
Executar o teste: Sucesso.
### DATA-T4 (Teste Vermelho - Navegação ao Clique)
Teste Espresso que verifica a navegação de clique em um item da lista para a tela de Reserva.
- Criar a Activity de destino e adicioná-la ao manifest.
`app/src/main/java/com/rggn.appcondominio/reservation/ReservationActivity.kt`
- Escrever o Teste com Espresso Intents
O teste falhará porque o `CommonAreaAdapter` ainda não possui um `OnClickListener` para iniciar a `ReservationActivity`. `app/src/androidTest/java/com/rggn.appcondominio/home/DashboardUITest.kt`
Executar o teste: Falha
### DATA-D4 (Código Verde - Navegação ao Clique)
Implementar a lógica de clique (onClickListener) no `CommonAreaAdapter` para iniciar (Intent) a `ReservationActivity` e passar o `id` da área clicada.
`../home/CommonAreaAdapter.kt`
- Implementar o Listener na DashboardActivity
`../home/DashboardActivity.kt`
Executar o DashboardUITest: Sucesso
### DATA-T5 (Teste Vermelho - Tela de Reserva)
Desenvolver a tela de destino `ReservationActivity`.
- Preparar Layout
`../layout/activity_reservation.xml`
- Atribuir Layout na Activity
`../reservation/ReservationActivity.kt`
- Escrever o teste Espresso
`../reservation/ReservationUITest.kt`
Executar o Teste: Falha `AssertionError`
### DATA-D5 (Código Verde - Exibir o ID na Reserva)
Implementar a lógica na `ReservationActivity` para extrair o `ID` da Intent e atualizar o `TextView` `area_id_text_view`.
`../reservation/ReservationActivity.kt`
Executar o teste: Sucesso
## DATA-T6/CAL-T2 (Teste Vermelho - Seleção de Data)
Teste de UI para garantir que, ao interagir com o calendário ou um botão de seleção, a aplicação consiga capturar a data selecionada e exibir esse estado.
- Adicionar elemento de Seleção de Data
`../layout/activity_reservation.xml`
- Escrever o teste para selecionar data
`../reservation/ReservationUITest.kt`
Executar o teste: Falhou
### DATA-D6/CAL-D2 (Código Verde - Lógica de Seleção de Data)
- Modificar `ReservationActivity.kt` com a lógica do DatePickerDialog e o mock da data.
Executar o ReservationUITest: Sucesso
### DATA-T7/CAL-T2 (Teste Vermelho - ViewModel de Reserva)
Verificar se, ao chamar `checkAvailability()` no ViewModel, ele interage com o `DataService` e atualiza um `LiveData<Boolean>` com o resultado.
- Alterar o DataService para simular a chamada ao backend (novo contrato).
`../data/Dataservice.kt`
- Escrever o teste
`../reservation/ReservationViewModelTest.kt`
Executar o teste: Falha Unresolved reference
### DATA-D7/CAL-T2 (Código Verde - Criar o ViewModel de Reserva)
Implementar a classe `ReservationViewModel` e a lógica necessária para que o teste passe.
Criar a classe `ReservationViewModel`, injetar o `DataService`, e implementar o método `checkAvailability` para atualizar um `LiveData<Boolean>`.
- Criar o arquivo
`../reservation/ReservationViewModel.kt`
Executar o teste: Sucesso
### CAL-T2.1 (Teste Vermelho - UI de Disponibilidade)
Conectar esse `ViewModel` à `Activity` e atualizar a interface do usuário (UI) com o resultado da disponibilidade (DISPONÍVEL ou INDISPONÍVEL).
- Atualização do Layout para exibit o status
`../layout/activity_reservation.xml`
- Criar a classe de dados para exibir dados da reserva
`../data/AvailabilityStatus.kt`
- Escrever o teste
`../reservation/ReservationUITest.kt`
Executar o teste: Falha
### CAL-D2.1 (Código Verde - Ligar ViewModel à UI)
Atualizar a `ReservationActivity.kt` para instanciar o `ReservationViewModel` e sua `Factory`, conectar os botões ao `ViewModel` e observar o `LiveData<Boolean>` do ViewModel e atualizar o novo `TextView` (`availability_status_text_view`) com a disponibilidade.
- Atualizar o método que retorna o status detalhado
`../reservation/ReservationViewModel.kt`
Executar o ReservationUITest: Sucesso
### CAL-T2.2 (Teste Vermelho - ViewModel de Reserva)
Refatorar o teste de unidade (JUnit) ReservationViewModelTest.kt para receber o AvailabilityStatus.
Executar o teste: Falha inesperada Unresolved reference para os métodos (checkAvailability, availabilityStatus, isAvailable.
- Correção no DataService.kt: adicionar o método `checkAvailability`
- Correção no ReservationViewModel.kt: atualizar para utilizar a classe AvailabilityStatus e o método checkAvailability.
Executar o teste: Sucesso
### CAL-D2.2 (Refatorar Seleção de Datas)
Atualizar o arquivo `ReservationActivity.kt` para que o seletor de datas use a data **real** escolhida pelo usuário, mantendo o aplicativo funcional para todas as datas e áreas.
Executar o aplicativo e navegar até a verificação de disponibilidade: Sucesso
### CAL-T2.3 (Teste de UI Vermelho - Exibição do Calendário)
Escrever o teste de UI (Espresso) com funções mock para garantir que as datas reservadas sejam exibidas corretamente.
- Teste de UI CalendarActivityTest.kt
### CAL D2.3 (Código Verde - Implementação da Lógica do Calendário)
- Preparações:
	1. Atualização do DataService.kt para adicionar uma função mock que retornará uma lista de datas reservadas para uma determinada área, simulando a API.
	2. Atualização do ReservationViewModel.kt para adicionar a lógica para buscar essa lista de datas reservadas e expô-las à Activity via LiveData.
- Novos componentes:
	1. Criar o layout activity_calendar.xml para a nova CalendarActivity, para listar as datas de maneira simples (TextView) para testes.
	2. Criar, e adicionar ao manifest, a CalendarActivity.kt no package reservation, que buscará a lista de datas reservadas no ViewModel e exibe os resultados no TextView (depois será o calendário).
Executar o teste: Sucesso.
### Refatoração (Navegação da Dashboard para CalendarActivity)
- DashboardActivity.kt
- Remover o ID fixo do CalendarActivity.kt
Executar Testes: Falha, não encontra a Intent.
- Correção: Modificar a CalendarActivity.kt para receber a RecervationActivity.EXTRA_AREA_ID
Executar Testes: Falha  CalendarActivityTest#fetchReservedDates_deveExibirListaDeDatasReservadas
- **O Teste Espera:** `"01/12/2025\n02/12/2025\n03/12/2025"`
- **O `DataService` Fornece:** `listOf("05/12/2025", "10/12/2025", "25/12/2025")`
Corrigir a lista mockada no DataService.
Executar Testes: Sucesso
### CAL-D2.3 (Implementar a Visualização do Calendário)
- Novo layout com o CalendarView padrão Android em activity_calendar.xml
- Atualizar o CalendarActivity.kt para incluir o componente CalendarView.
### CAL-T3 (Teste Vermelho - Teste de Interação da Navegação para a Reserva com Data)
Teste de UI (Espresso): Para que o Espresso consiga "clicar" em uma data do `CalendarView` e verificar a Intent, precisamos de uma biblioteca auxiliar (o `CalendarView` é notoriamente difícil de testar).
- Alterar o CalendarActivityTeste.kt para incluir o novo requisito.
Executar o teste: Falha esperada Unresolved reference (EXTRA_DATE).
### CAL-D3 (Código Verde - Implementação da Navegação)
- Atualizar o layout activity_reservation.xml.
- Remover a lógica temporária de mock e definir a constante EXTRA_DATE em `ReservationActivity.kt`
- Atualizar `CalendarActivity.kt` implementando a Intent.
Executar o teste: Falha inesperada em ReservationUITest, múltiplas Unresolved reference. As IDs do layout mudaram.
- Atualizar `ReservationUITest` com as novas IDs
Executar o teste: Falha inesperada AmbiguousViewMatcherException
- Usar a classe exata da View na simulação de interação (click)
Executar o teste: Sucesso.
### Refatoração (E limpeza do código)
- Unificação de Telas: A tela de Calendário e a de Status da Reserva foram unificadas na CalendarActivity.
- Exibição do Nome da Área: O título agora exibe o nome da área em vez do ID.
- Lógica da CalendarActivity: A CalendarActivity agora controla a exibição do status da reserva na mesma tela.
- Remoção de Código: A ReservationActivity, seu layout e suas referências no AndroidManifest e DashboardActivity foram removidos.
- Testes de Interface: Os testes foram atualizados para validar a nova interface unificada e a exibição do status de reserva.
- DataService: Uma nova função para buscar o nome da área pelo ID foi adicionada.
- Simplificação do ViewModel: O ReservationViewModel e seu teste de unidade foram simplificados, removendo propriedades e testes que se tornaram desnecessários.
- Limpeza do DataService: Os métodos getResidents e getReservedDatesForArea, que não estavam em uso, foram removidos, assim como o teste que validava getResidents.
- Remoção de Testes de UI: O arquivo de teste de interface ReservationUITest foi excluido, pois era dedicado a uma tela que foi removida.
- Remoção de Layout: o arquivo de layout activity_reservation.xml foi removido, pois não será mais usado após a unificação das telas do calendário e reserva.
- Correção de Referências: Foram corrigidas referências e importações desnecessárias nos arquivos DashboardActivity, DashboardUITest e CalendarActivity.
- Executados todos os testes: Sucesso.
