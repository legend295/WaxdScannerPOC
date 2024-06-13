package com.waxdscannerpoc.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.scanner.activity.FingerprintScanner
import com.scanner.utils.constants.ScannerConstants
import com.scanner.utils.enums.ScanningType
import java.io.File
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private var tvStatus: AppCompatTextView? = null
    private var sheet: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvRegistration: Button = findViewById(R.id.btnRegistration)
        val tvVerification: Button = findViewById(R.id.btnVerification)
        tvStatus = findViewById(R.id.tvStatus)
        tvRegistration.setOnClickListener {
            sheet = showFieldsDialog(ScanningType.REGISTRATION) { bvnNumber, phoneNumber, name, _ ->
                FingerprintScanner.Builder(this).setBvnNumber(bvnNumber).setPhoneNumber(phoneNumber)
                    .setScanningType(ScanningType.REGISTRATION).start(this, scanningLauncher)
            }
        }

        tvVerification.setOnClickListener {
            sheet = showFieldsDialog(ScanningType.VERIFICATION) { bvnNumber, _, _, amount ->
                FingerprintScanner.Builder(this).setBvnNumber(bvnNumber)
                    .setAmount(amount.toInt())
                    .setScanningType(ScanningType.VERIFICATION).start(this, scanningLauncher)
            }
        }
    }

    private val scanningLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val list: ArrayList<File>? = it.data?.serializable(ScannerConstants.DATA)
                Log.d(MainActivity::class.simpleName, list?.size.toString())
                handleResponse(list)
            }
        }

    private fun handleResponse(list: ArrayList<File>?) {
        var message = ""
        list?.forEach {
            message += "\n${it.path}"
        }
        if (message.isNotEmpty()) {
            tvStatus?.text = StringBuilder().append("File saved to paths :- ").append(message)
        }
    }
}