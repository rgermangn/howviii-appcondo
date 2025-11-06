package com.rggn.appcondominio.reservation

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rggn.appcondominio.R
import com.rggn.appcondominio.data.DataService
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationActivity : AppCompatActivity() {

    // Views declaradas
    private lateinit var selectedDateTextView: TextView
    private lateinit var selectDateButton: Button
    private lateinit var checkAvailabilityButton: Button
    private lateinit var availabilityStatusTextView: TextView

    // Variável para armazenar a data selecionada e o ID da Área
    private var selectedDate: Calendar = Calendar.getInstance()
    private var currentAreaId: Int = -1

    // Inicialização do ViewModel com Factory (Injeção Manual de DataService)
    private val viewModel: ReservationViewModel by viewModels {
        ReservationViewModelFactory(DataService())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        // Inicializar Views
        selectedDateTextView = findViewById(R.id.selected_date_text_view)
        selectDateButton = findViewById(R.id.select_date_button)
        val idTextView = findViewById<TextView>(R.id.area_id_text_view)
        checkAvailabilityButton = findViewById(R.id.check_availability_button)
        availabilityStatusTextView = findViewById(R.id.availability_status_text_view)

        // O botão de checagem deve estar desabilitado até a data ser escolhida
        checkAvailabilityButton.isEnabled = false

        // Lógica para receber e exibir o ID da Área
        currentAreaId = intent.getIntExtra(EXTRA_AREA_ID, -1)
        if (currentAreaId != -1) {
            idTextView.text = "ID da Área: $currentAreaId"
            viewModel.setAreaId(currentAreaId)
        } else {
            idTextView.text = "Erro: ID da Área não encontrado."
        }

        setupListeners()

        // Observar mudanças no ViewModel
        observeViewModel()
    }

    private fun setupListeners() {
        // Lógica do Seletor de Data
        selectDateButton.setOnClickListener {
            showDatePickerDialog()
        }

        // Lógica do botão de Checar Disponibilidade: Chama a lógica do ViewModel
        checkAvailabilityButton.setOnClickListener {
            viewModel.checkAvailability(currentAreaId, selectedDate)
        }
    }

    private fun observeViewModel() {
        // Observa o resultado detalhado da checagem de disponibilidade
        viewModel.availabilityStatus.observe(this) { status ->
            // Se o status for nulo (primeira execução), não faz nada.
            if (status == null) return@observe

            val statusText: String
            val colorResId: Int

            if (status.isAvailable) {
                statusText = "Status: DISPONÍVEL"
                colorResId = android.R.color.holo_green_dark
            } else {
                // Monta a string detalhada (usando os dados mockados)
                statusText = "Status: INDISPONÍVEL\n" +
                        "Reservado por: ${status.residentName ?: "Desconhecido"}\n" +
                        "Unidade: ${status.residentUnit ?: "N/A"}"
                colorResId = android.R.color.holo_red_dark
            }

            // Atualiza o TextView
            availabilityStatusTextView.text = statusText

            // Altera a cor do texto para melhor feedback visual
            availabilityStatusTextView.setTextColor(resources.getColor(colorResId, theme))
        }
    }

    private fun showDatePickerDialog() {
        // Usa a data atual como default no picker
        val initialYear = Calendar.getInstance().get(Calendar.YEAR)
        val initialMonth = Calendar.getInstance().get(Calendar.MONTH)
        val initialDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            // CORREÇÃO: Removemos o mock e usamos os valores reais (year, month, dayOfMonth)
            { _, year, month, dayOfMonth ->

                // Cria uma nova instância de Calendar com a data REAL selecionada
                val newSelectedDate = Calendar.getInstance()
                newSelectedDate.set(year, month, dayOfMonth)

                selectedDate = newSelectedDate // Atualiza a variável local
                updateSelectedDateText(newSelectedDate)
                checkAvailabilityButton.isEnabled = true // Habilita o botão
            },
            initialYear,
            initialMonth,
            initialDay
        )
        datePickerDialog.show()
    }

    private fun updateSelectedDateText(calendar: Calendar) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        selectedDateTextView.text = dateFormat.format(calendar.time)
    }

    companion object {
        const val EXTRA_AREA_ID = "com.rggn.appcondominio.AREA_ID"
    }
}
