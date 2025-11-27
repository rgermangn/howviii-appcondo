package com.rggn.appcondominio.reservation

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.rggn.appcondominio.R
import com.rggn.appcondominio.data.DataService
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_AREA_ID = "extra_area_id"
    }

    private val viewModel: ReservationViewModel by viewModels {
        ReservationViewModelFactory(DataService())
    }

    private lateinit var areaTitleTextView: TextView
    private lateinit var reservationCalendarView: CalendarView
    private lateinit var selectedDateTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var reservedByLabel: TextView
    private lateinit var residentNameTextView: TextView
    private lateinit var residentUnitLabel: TextView
    private lateinit var residentUnitTextView: TextView
    private lateinit var availabilityStatusLabel: TextView
    private lateinit var emptySelectionMessage: TextView

    private var currentAreaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        // Initialize Views
        areaTitleTextView = findViewById(R.id.area_title_text)
        reservationCalendarView = findViewById(R.id.reservation_calendar_view)
        selectedDateTextView = findViewById(R.id.selected_date_text)
        statusTextView = findViewById(R.id.status_text)
        reservedByLabel = findViewById(R.id.reserved_by_label)
        residentNameTextView = findViewById(R.id.resident_name_text)
        residentUnitLabel = findViewById(R.id.resident_unit_label)
        residentUnitTextView = findViewById(R.id.resident_unit_text)
        availabilityStatusLabel = findViewById(R.id.availability_status_label)
        emptySelectionMessage = findViewById(R.id.empty_selection_message)

        // Set initial visibility
        emptySelectionMessage.visibility = View.VISIBLE
        selectedDateTextView.visibility = View.GONE
        availabilityStatusLabel.visibility = View.GONE
        statusTextView.visibility = View.GONE
        reservedByLabel.visibility = View.GONE
        residentNameTextView.visibility = View.GONE
        residentUnitLabel.visibility = View.GONE
        residentUnitTextView.visibility = View.GONE

        currentAreaId = intent.getIntExtra(EXTRA_AREA_ID, -1)

        if (currentAreaId != -1) {
            val areaName = DataService().getAreaNameById(currentAreaId)
            areaTitleTextView.text = areaName
        } else {
            areaTitleTextView.text = "Erro: ID da Área não encontrado."
        }

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        reservationCalendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            onDateSelected(year, month, dayOfMonth)
        }
    }

    fun onDateSelected(year: Int, month: Int, dayOfMonth: Int) {
        emptySelectionMessage.visibility = View.GONE
        val selectedCalendar = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
        }
        val dateString = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedCalendar.time)
        selectedDateTextView.text = "Data Selecionada: $dateString"
        viewModel.checkAvailability(currentAreaId, selectedCalendar)
    }

    private fun observeViewModel() {
        viewModel.availabilityStatus.observe(this, Observer { status ->
            updateAvailabilityStatus(status.isAvailable, status.residentName, status.residentUnit)
        })
    }

    private fun updateAvailabilityStatus(isAvailable: Boolean, residentName: String?, residentUnit: String?) {
        selectedDateTextView.visibility = View.VISIBLE
        availabilityStatusLabel.visibility = View.VISIBLE
        statusTextView.visibility = View.VISIBLE

        if (isAvailable) {
            statusTextView.text = "DISPONÍVEL"
            statusTextView.setBackgroundColor(Color.parseColor("#4CAF50")) // Verde
            statusTextView.setTextColor(Color.WHITE)

            reservedByLabel.visibility = View.GONE
            residentNameTextView.visibility = View.GONE
            residentUnitLabel.visibility = View.GONE
            residentUnitTextView.visibility = View.GONE
        } else {
            statusTextView.text = "RESERVADO"
            statusTextView.setBackgroundColor(Color.parseColor("#FF9800")) // Laranja/Amarelo
            statusTextView.setTextColor(Color.BLACK)

            reservedByLabel.visibility = View.VISIBLE
            residentNameTextView.visibility = View.VISIBLE
            residentUnitLabel.visibility = View.VISIBLE
            residentUnitTextView.visibility = View.VISIBLE

            residentNameTextView.text = residentName ?: "Não Informado"
            residentUnitTextView.text = "Unidade: ${residentUnit ?: "-"}"
        }
    }
}
