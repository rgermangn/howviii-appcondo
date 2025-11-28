
#### INFO-D1 e Info-D2: Criar a tela de informações e adicionar conteúdo
- Criar o arquivo da Activity: `InformationActivity.kt` no novo package `com.rggn.appcondominio.info`
- Adicionar a Activity no `AndroidManifest.xml`
- Criar o arquivo do layout: `activity_information.xml` em `main/res/layout`
  ![[Pasted image 20251118173858.png]]
#### INFO-T1: Teste de UI (Espresso)
- Teste para garantir que o conteúdo principal está sendo renderizado.
- Criar o arquivo de teste: `InformationActivityTeste.kt` no diretório de testes de UI `androidTest`.
- Executar o teste: Sucesso.
#### INFO-NAV (NOVO): Adicionar a navegação para acitivity das informações
- Adicionar o botão para abrir as informações no layout `activity_dashboard.xml`.
- Adicionar a listener do botão na `DashboardActivity.kt` para abrir a `InformationActivity.kt`
#### E2E-1 (ALTERADO): Teste ponta-a-ponta
- Criar o plano de testes com os casos de teste para testes manuais de ponta-a-ponta.
    - Autenticação
        - Testar as diversas condições de preenchimento
            - Dados válidos:
                - User: morador@teste.com
                - Senha: 123
        - Botão de Entrar ativado/desativado (depende se está tudo preenchido)
    - Dashboard (Áreas Comuns)
        - Aparecem as áreas comuns (salão de festas e churrasqueira gourmet) e a capacidade?
            - Salão: 40
            - Churrasqueira: 20
        - Aparece o botão de informações, ele funciona?
    - Informações
        - Os textos estão visíveis e nas áreas corretas?
    - Calendário
        - Detalhes aparecem corretamente para as datas reservadas?
            - Salão de Festas
                - Datas disponíveis: Todas as outras
                - Datas reservadas: João Silva, A-101, 05/12/2025
            - Churrasqueira Gourmet
                - Datas disponíveis: Todas as outras
                - Datas reservadas: Maria Souza, B-202, 10/12/2025
        - Os detalhes mudam conforme a data selecionada?

#### Plano de Testes Manual: E2E Jornada Crítica de Reserva
**Objetivo:** Validar o fluxo completo do usuário, desde a autenticação até a visualização do status de disponibilidade de datas em Áreas Comuns, utilizando os dados mockados no `DataService.kt`.

Versão do Aplicativo: [Vazio - Preencher durante a execução]

Testador: [Vazio - Preencher durante a execução]

Data da Execução: [Vazio - Preencher durante a execução]

##### 1. Autenticação (LoginActivity)
| **ID do Caso** | **Objetivo do Teste**                          | **Pré-condição**                                    | **Passos de Execução**                                                          | **Resultado Esperado**                                                   | **Status** |
|----------------|------------------------------------------------|-----------------------------------------------------|---------------------------------------------------------------------------------|--------------------------------------------------------------------------|------------|
| **E2E-1.1**    | Verificar ativação do botão com dados válidos. | Tela de Login exibida.                              | 1. Inserir `morador@teste.com` no campo Email. 2. Inserir `123` no campo Senha. | O botão **"Entrar"** (`login_button`) deve ser ativado (enabled = true). |            |
| **E2E-1.2**    | Validar login e navegação para o Dashboard.    | Botão "Entrar" ativado (Dados válidos preenchidos). | 1. Clicar no botão **"Entrar"**.                                                | O aplicativo deve navegar com sucesso para a tela **DashboardActivity**. |            |
| **E2E-1.3**    | Verificar desativação do botão (Email vazio).  | Tela de Login exibida.                              | 1. Deixar o campo Email vazio. 2. Inserir `123` no campo Senha.                 | O botão **"Entrar"** deve permanecer desativado (enabled = false).       |            |
| **E2E-1.4**    | Verificar desativação do botão (Senha vazia).  | Tela de Login exibida.                              | 1. Inserir `morador@teste.com` no campo Email. 2. Deixar o campo Senha vazio.   | O botão **"Entrar"** deve permanecer desativado (enabled = false).       |            |

##### 2. Dashboard (DashboardActivity)
| **ID do Caso** | **Objetivo do Teste**                      | **Pré-condição**                       | **Passos de Execução**                       | **Resultado Esperado**                                                                                                                                                                            | **Status** |
|----------------|--------------------------------------------|----------------------------------------|----------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------|
| **E2E-2.1**    | Exibição correta da lista de Áreas Comuns. | Navegação bem-sucedida para Dashboard. | 1. Observar a lista (`areas_recycler_view`). | A lista deve exibir **duas** áreas com os seguintes detalhes: 1. **"Salão de Festas"** com a descrição **"Capacidade: 40"**. 2. **"Churrasqueira Gourmet"** com a descrição **"Capacidade: 20"**. |            |
| **E2E-2.2**    | Navegação ao clicar no "Salão de Festas".  | Dashboard exibido.                     | 1. Clicar no item **"Salão de Festas"**.     | O aplicativo deve navegar com sucesso para a tela **CalendarActivity**.                                                                                                                           |            |

##### 3. Calendário (CalendarActivity - Salão de Festas)
_Pré-condição: Entrou no Calendário via "Salão de Festas" (AreaId = 10)._

| **ID do Caso** | **Objetivo do Teste**               | **Pré-condição**                              | **Passos de Execução**                                                                   | **Resultado Esperado**                                                                                                                     | **Status** |
|----------------|-------------------------------------|-----------------------------------------------|------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|------------|
| **E2E-3.1**    | Título da Área correto.             | Tela do Calendário exibida (Salão de Festas). | 1. Observar o texto principal da tela.                                                   | O título (`area_title_text`) deve ser **"Salão de Festas"**.                                                                               |            |
| **E2E-3.2**    | Verificação de data **Reservada**.  | Tela do Calendário exibida.                   | 1. Clicar e selecionar o dia **05/12/2025** no calendário (`reservation_calendar_view`). | 1. O status deve exibir **"RESERVADO"** (cor Laranja/Amarelo). 2. Os detalhes devem ser visíveis: **"João Silva"** e **"Unidade: A-101"**. |            |
| **E2E-3.3**    | Verificação de data **Disponível**. | Tela do Calendário exibida.                   | 1. Clicar e selecionar o dia **06/12/2025** (ou qualquer outra data não reservada).      | 1. O status deve exibir **"DISPONÍVEL"** (cor Verde). 2. Os detalhes do residente devem estar **ocultos**.                                 |            |

##### 4. Calendário (CalendarActivity - Churrasqueira Gourmet)
_Pré-condição: Você precisará voltar ao Dashboard e entrar no Calendário via "Churrasqueira Gourmet" (AreaId = 20)._

| **ID do Caso** | **Objetivo do Teste**               | **Pré-condição**                                    | **Passos de Execução**                                                                   | **Resultado Esperado**                                                                                                                      | **Status** |
|----------------|-------------------------------------|-----------------------------------------------------|------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|------------|
| **E2E-4.1**    | Título da Área correto.             | Tela do Calendário exibida (Churrasqueira Gourmet). | 1. Observar o texto principal da tela.                                                   | O título (`area_title_text`) deve ser **"Churrasqueira Gourmet"**.                                                                          |            |
| **E2E-4.2**    | Verificação de data **Reservada**.  | Tela do Calendário exibida.                         | 1. Clicar e selecionar o dia **10/12/2025** no calendário (`reservation_calendar_view`). | 1. O status deve exibir **"RESERVADO"** (cor Laranja/Amarelo). 2. Os detalhes devem ser visíveis: **"Maria Souza"** e **"Unidade: B-202"**. |            |
| **E2E-4.3**    | Verificação de data **Disponível**. | Tela do Calendário exibida.                         | 1. Clicar e selecionar o dia **11/12/2025** (ou qualquer outra data não reservada).      | 1. O status deve exibir **"DISPONÍVEL"** (cor Verde). 2. Os detalhes do residente devem estar **ocultos**.                                  |            |

#### Polimento UI-2 OK

#### Build do .apk OK