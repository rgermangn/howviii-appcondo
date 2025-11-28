package com.rggn.appcondominio.info

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rggn.appcondominio.R

class InformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        supportActionBar?.title = "Informações e Regras"

    }
}