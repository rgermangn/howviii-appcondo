package com.rggn.appcondominio.info

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rggn.appcondominio.R

/**
 * Activity responsável por exibir a tela de Informações.
 * Esta tela deve conter Regras, Custos, Itens e Equipamentos.
 * Tarefa: INFO-D1 (Criar a interface e adicionar Regras/Custos)
 */
class InformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        // Configura a barra de ação/título
        supportActionBar?.title = "Informações e Regras"

        // FUTURO: INFO-D2 - Adicionar lógica para exibir conteúdo detalhado de Itens e Equipamentos,
        // possivelmente usando Fragments ou RecyclerViews se o conteúdo for complexo.
    }
}