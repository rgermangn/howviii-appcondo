package com.rggn.appcondominio.reservation

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.rggn.appcondominio.R
import com.rggn.appcondominio.data.DataService
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ReservationActivity : AppCompatActivity() {

    // Constantes para Intent Extras
    companion object {
        const val EXTRA_AREA_ID = "extra_area_id"
        const val EXTRA_DATE = "extra_date"
    }

    private val viewModel: ReservationViewModel by viewModels {
        ReservationViewModelFactory(DataService())
    }

    // Views
    private lateinit var areaIdTextView: TextView
    private lateinit var selectedDateTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var reservedByLabel: TextView
    private lateinit var residentNameTextView: TextView
    private lateinit var residentUnitTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        // Inicializa as Views
        areaIdTextView = findViewById(R.id.area_title_text_reservation)
        selectedDateTextView = findViewById(R.id.selected_date_text)
        statusTextView = findViewById(R.id.availability_status_text)
        reservedByLabel = findViewById(R.id.resident_unit_label)
        residentNameTextView = findViewById(R.id.resident_name_text)
        residentUnitTextView = findViewById(R.id.resident_unit_text)

        // 1. Processar Intent (ID da Área e Data)
        val areaId = intent.getIntExtra(EXTRA_AREA_ID, -1)
        val dateString = intent.getStringExtra(EXTRA_DATE)

        if (areaId != -1 && dateString != null) {
            // Exibir dados brutos na UI
            areaIdTextView.text = getString(R.string.area_id_display, areaId)
            selectedDateTextView.text = dateString

            // Converter String de Data para Calendar para o ViewModel
            val calendar = dateStringToCalendar(dateString)

            // 2. Iniciar verificação no ViewModel
            viewModel.checkAvailability(areaId, calendar)
        } else {
            // Tratar erro (falta de dados)
            areaIdTextView.text = "Erro: Dados incompletos."
        }

        // 3. Observar LiveData
        observeViewModel()
    }

    private fun observeViewModel() {
        // Observa o AvailabilityStatus COMPLETO
        viewModel.availabilityStatus.observe(this, Observer { status ->
            if (status.isAvailable) {
                // Estado: DISPONÍVEL
                statusTextView.text = "DISPONÍVEL"
                statusTextView.setBackgroundColor(Color.parseColor("#4CAF50")) // Verde
                statusTextView.setTextColor(Color.WHITE)

                // Ocultar detalhes do reservante
                reservedByLabel.visibility = View.GONE
                residentNameTextView.visibility = View.GONE
                residentUnitTextView.visibility = View.GONE

            } else {
                // Estado: RESERVADO
                statusTextView.text = "RESERVADO"
                statusTextView.setBackgroundColor(Color.parseColor("#FF9800")) // Laranja/Amarelo
                statusTextView.setTextColor(Color.BLACK)

                // Exibir detalhes do reservante
                reservedByLabel.visibility = View.VISIBLE
                residentNameTextView.visibility = View.VISIBLE
                residentUnitTextView.visibility = View.VISIBLE

                // Preencher detalhes
                residentNameTextView.text = status.residentName ?: "Não Informado"
                residentUnitTextView.text = getString(R.string.unit_display, status.residentUnit ?: "-")
            }
        })
    }

    // Função auxiliar para converter a data string de volta para Calendar
    private fun dateStringToCalendar(dateString: String): Calendar {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = format.parse(dateString)
        return Calendar.getInstance().apply {
            if (date != null) {
                time = date
            }
        }
    }
}
