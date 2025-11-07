package com.rggn.appcondominio.reservation

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rggn.appcondominio.R
import com.rggn.appcondominio.data.DataService
import com.rggn.appcondominio.reservation.ReservationActivity.Companion.EXTRA_AREA_ID
import com.rggn.appcondominio.reservation.ReservationActivity.Companion.EXTRA_DATE // Importação Necessária
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarActivity : AppCompatActivity() {

    private val viewModel: ReservationViewModel by viewModels {
        ReservationViewModelFactory(DataService())
    }

    private lateinit var reservedDatesTextView: TextView
    private lateinit var areaTitleTextView: TextView
    private lateinit var reservationCalendarView: CalendarView

    // Propriedade de classe para armazenar o ID lido da Intent
    private var currentAreaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        areaTitleTextView = findViewById(R.id.area_title_text)
        reservationCalendarView = findViewById(R.id.reservation_calendar_view)

        // 1. LÊ O EXTRA DA INTENT E SALVA NA PROPRIEDADE
        currentAreaId = intent.getIntExtra(EXTRA_AREA_ID, -1)

        if (currentAreaId != -1) {
            viewModel.setAreaId(currentAreaId)
            areaTitleTextView.text = "Calendário de Reservas (Área ID: $currentAreaId)"
        } else {
            areaTitleTextView.text = "Erro: ID da Área não encontrado."
        }

        // Dentro da função onCreate() ou setupViews() da CalendarActivity
        val calendarView = findViewById<CalendarView>(R.id.reservation_calendar_view)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Lógica para formatar a data e chamar a próxima Activity
            // ...
            // Exemplo de como formatar a data para o Intent
            val date = Calendar.getInstance().apply { set(year, month, dayOfMonth) }
            val dateString = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date.time)

            // Lógica de navegação:
            val intent = Intent(this, ReservationActivity::class.java).apply {
                putExtra(ReservationActivity.EXTRA_AREA_ID, currentAreaId) // areaId é a variável que você passou no Intent anterior
                putExtra(ReservationActivity.EXTRA_DATE, dateString)
            }
            startActivity(intent)
        }


        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        // CAL-T3: Listener de clique no CalendarView
        reservationCalendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // 1. Converte os parâmetros do CalendarView para um objeto Calendar
            val selectedCalendar = Calendar.getInstance().apply {
                // NOTA: O CalendarView usa month com base 0 (0=Janeiro, 11=Dezembro)
                set(year, month, dayOfMonth)
            }

            // 2. Chama a função de navegação, usando a propriedade de classe
            // CORREÇÃO APLICADA AQUI: O compilador agora sabe que currentAreaId é uma propriedade.
            navigateToReservationActivity(currentAreaId, selectedCalendar)
        }
    }

    // Função auxiliar para formatar a data, usada na Intent
    private fun formatDate(calendar: Calendar): String {
        // Formato dd/MM/yyyy, crucial para a Intent e o DataService
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    // CAL-T3 IMPLEMENTAÇÃO: Envia a Intent com a data e o ID
    private fun navigateToReservationActivity(areaId: Int, date: Calendar) {
        // 1. Formata a data para ser enviada como String (formato do DataService)
        val dateString = formatDate(date)

        // 2. Cria e dispara a Intent
        val intent = Intent(this, ReservationActivity::class.java).apply {
            putExtra(EXTRA_AREA_ID, areaId)
            putExtra(EXTRA_DATE, dateString) // Adiciona a data (alvo do teste)
        }
        startActivity(intent)
    }


    private fun observeViewModel() {
        viewModel.reservedDates.observe(this) { dates ->
            val datesListString = if (dates.isNotEmpty()) {
                dates.joinToString(separator = "\n")
            } else {
                "Nenhuma data reservada encontrada."
            }
            reservedDatesTextView.text = datesListString
        }

        // FUTURO: Aqui entrará a lógica para customizar a visualização das datas reservadas
    }
}
