package com.rggn.appcondominio.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rggn.appcondominio.R
import com.rggn.appcondominio.reservation.CalendarActivity // NOVO IMPORT: Para navegar para a tela do calendário
import com.rggn.appcondominio.reservation.ReservationActivity

class DashboardActivity : AppCompatActivity() {

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var adapter: CommonAreaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        setupRecyclerView()
        observeViewModel()
    }

    // Função de navegação para a tela de reserva
    private fun navigateToReservation(areaId: Int) {
        // MUDANÇA: Navega para a CalendarActivity
        val intent = Intent(this, CalendarActivity::class.java).apply {
            // CORRIGIDO: Usa a constante definida na CalendarActivity
            putExtra(ReservationActivity.EXTRA_AREA_ID, areaId)
        }
        startActivity(intent)
    }


    private fun setupRecyclerView() {
        // Inicializa o Adapter passando o lambda de clique (NOVO CÓDIGO)
        adapter = CommonAreaAdapter(onAreaClick = { areaId ->
            navigateToReservation(areaId)
        })

        // Configura o RecyclerView
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.areas_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.areas.observe(this) { areas ->
            areas?.let {
                adapter.updateAreas(it)
            }
        }
    }
}
