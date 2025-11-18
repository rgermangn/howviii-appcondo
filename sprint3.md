
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
#TODO:
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
#### Falta o polimento da interface UI-2