package com.waxdscannerpoc.android

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.github.legend295.fingerprintscanner.BuildConfig
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.scanner.activity.FingerprintScanner
import com.scanner.utils.builder.ThemeOptions
import com.scanner.utils.constants.ScannerConstants
import com.scanner.utils.enums.ScanningType
import org.json.JSONObject
import java.io.File

class MainActivity : AppCompatActivity() {
    private var tvStatus: AppCompatTextView? = null
    private var sheet: BottomSheetDialog? = null

    private val themeOptions = ThemeOptions().apply {
        buttonColor = R.color.black
        buttonTextColor = R.color.white
        messageColor = R.color.black
        titleTextColor = R.color.black
        contentTextColor = R.color.black
        buttonBackground = R.drawable.bg_round_white
        popUpBackground = R.drawable.bg_round_white
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvRegistration: Button = findViewById(R.id.btnRegistration)
        val tvVerification: Button = findViewById(R.id.btnVerification)
        tvStatus = findViewById(R.id.tvStatus)
        tvRegistration.setOnClickListener {
            sheet =
                showFieldsDialog(ScanningType.REGISTRATION) { bvnNumber, phoneNumber, name, _, key ->
                    FingerprintScanner.Builder(this)
                        .setUniqueId(bvnNumber)
                        .setPhoneNumber(phoneNumber)
                        .setScanningType(ScanningType.REGISTRATION)
                        .setKey(key)
                        .setThemeOptions(themeOptions)
                        .setCustomData(JSONObject().apply {
                            put("pin", 1234)
                        })
                        .newRelicToken(BuildConfig.NEW_RELIC_TOKEN)
                        .skipLocation(skipLocation = false)
                        .start(this, scanningLauncher)
                }
        }


        tvVerification.setOnClickListener {
            sheet = showFieldsDialog(ScanningType.VERIFICATION) { bvnNumber, _, _, amount, key ->
                FingerprintScanner.Builder(this)
                    .setUniqueId(bvnNumber)
                    .setAmount(amount.toInt())
                    .setScanningType(ScanningType.VERIFICATION)
                    .newRelicToken(BuildConfig.NEW_RELIC_TOKEN)
                    .skipLocation(skipLocation = false)
                    .setThemeOptions(themeOptions)
                    .setKey(key) // For Encryption/Decryption
                    .start(this, scanningLauncher)
            }
        }

    }

    private val scanningLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val list: ArrayList<File>? = it.data?.serializable(ScannerConstants.DATA)
                val isVerified: Boolean? =
                    it.data?.getBooleanExtra(ScannerConstants.VERIFICATION_RESULT, false)
                Log.d(MainActivity::class.simpleName, list?.size.toString())
                handleResponse(list, isVerified)
            }
        }

    private fun handleResponse(list: ArrayList<File>?, isVerified: Boolean?) {
        if (list.isNullOrEmpty()) {
            tvStatus?.text =
                StringBuilder().append("Fingerprint verification :- ").append(isVerified)
            return
        }
        var message = ""
        list.forEach {
            message += "\n${it.path}"
        }
        if (message.isNotEmpty()) {
            tvStatus?.text = StringBuilder().append("File saved to paths :- ").append(message)
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            sheet?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}