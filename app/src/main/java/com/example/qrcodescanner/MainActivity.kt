package com.example.qrcodescanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class MainActivity : AppCompatActivity() {

    private lateinit var barcodeScannerView: DecoratedBarcodeView

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                barcodeScannerView.resume()
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        barcodeScannerView = findViewById(R.id.scannerView)

        barcodeScannerView.decodeContinuous { result ->
            barcodeScannerView.pause()
            showScannedData(result.text)
        }

        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                barcodeScannerView.resume()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                Toast.makeText(this, "Se requiere permiso de cámara para escanear QR", Toast.LENGTH_LONG).show()
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun showScannedData(data: String) {
        val intent = Intent(this, DisplayDataActivity::class.java)
        intent.putExtra("scanned_data", data)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        barcodeScannerView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeScannerView.pause()
    }
}