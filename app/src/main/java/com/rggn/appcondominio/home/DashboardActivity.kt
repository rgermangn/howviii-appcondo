package com.rggn.appcondominio.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rggn.appcondominio.R
import com.rggn.appcondominio.info.InformationActivity
import com.rggn.appcondominio.reservation.CalendarActivity

class DashboardActivity : AppCompatActivity() {

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var adapter: CommonAreaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        setupRecyclerView()
        observeViewModel()

        val infoButton = findViewById<Button>(R.id.btn_information)
        infoButton.setOnClickListener {
            val intent = Intent(this, InformationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToReservation(areaId: Int) {
        val intent = Intent(this, CalendarActivity::class.java).apply {
            putExtra(CalendarActivity.EXTRA_AREA_ID, areaId)
        }
        startActivity(intent)
    }


    private fun setupRecyclerView() {
        adapter = CommonAreaAdapter(onAreaClick = { areaId ->
            navigateToReservation(areaId)
        })

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
