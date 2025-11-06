package com.rggn.appcondominio.reservation

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rggn.appcondominio.R
import com.rggn.appcondominio.data.DataService

class CalendarActivity : AppCompatActivity() {

    private val viewModel: ReservationViewModel by viewModels {
        ReservationViewModelFactory(DataService())
    }

    private lateinit var reservedDatesTextView: TextView
    private lateinit var areaTitleTextView: TextView

    // ID da Área que estamos visualizando
    // private val AREA_ID_TO_VIEW = 10
    private var currentAreaId: Int = -1 // CAL-D2.3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Usamos o novo layout
        setContentView(R.layout.activity_calendar)

        reservedDatesTextView = findViewById(R.id.reserved_dates_list_text)
        areaTitleTextView = findViewById(R.id.area_title_text)

        // Simulação: Define o ID da área, que dispara a busca por datas
        // viewModel.setAreaId(AREA_ID_TO_VIEW)
        // areaTitleTextView.text = "Calendário de Reservas (Área ID: $AREA_ID_TO_VIEW)"

        // CAL-D2.3
        currentAreaId = intent.getIntExtra(ReservationActivity.EXTRA_AREA_ID, -1)

        if (currentAreaId != -1) {
            // Define o ID no ViewModel, que dispara a busca por datas
            viewModel.setAreaId(currentAreaId)
            areaTitleTextView.text = "Calendário de Reservas (Área ID: $currentAreaId)"
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        // Observa a lista de datas reservadas
        viewModel.reservedDates.observe(this) { dates ->
            // Converte a lista de strings em uma string com quebras de linha
            val datesListString = if (dates.isNotEmpty()) {
                dates.joinToString(separator = "\n")
            } else {
                "Nenhuma data reservada encontrada."
            }

            // Atualiza o TextView - Este é o ponto a ser testado pelo Espresso
            reservedDatesTextView.text = datesListString
        }
    }
}
