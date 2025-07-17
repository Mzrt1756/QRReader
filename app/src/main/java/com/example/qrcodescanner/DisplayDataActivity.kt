package com.example.qrcodescanner

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DisplayDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_data)

        val data = intent.getStringExtra("scanned_data")
        val dataTextView = findViewById<TextView>(R.id.dataTextView)
        dataTextView.text = data ?: "No se detectaron datos"
    }
}