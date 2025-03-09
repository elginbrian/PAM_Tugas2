package com.elginbrian.pamtugas2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RadioCalcActivity : AppCompatActivity() {

    private lateinit var edtNumber1: EditText
    private lateinit var edtNumber2: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnHitung: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio_calc)

        edtNumber1 = findViewById(R.id.edtNumber1)
        edtNumber2 = findViewById(R.id.edtNumber2)
        radioGroup = findViewById(R.id.radioGroup)
        btnHitung = findViewById(R.id.btnHitung)

        btnHitung.setOnClickListener {
            val num1 = edtNumber1.text.toString().toDoubleOrNull()
            val num2 = edtNumber2.text.toString().toDoubleOrNull()

            if (num1 == null || num2 == null) {
                Toast.makeText(this, "Masukkan angka yang valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(this, "Pilih operasi perhitungan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result: Double? = when (selectedId) {
                R.id.rbAdd -> num1 + num2
                R.id.rbSubtract -> num1 - num2
                R.id.rbMultiply -> num1 * num2
                R.id.rbDivide -> {
                    if (num2 == 0.0) {
                        Toast.makeText(this, "Tidak bisa membagi dengan 0", Toast.LENGTH_SHORT).show()
                        null
                    } else {
                        num1 / num2
                    }
                }
                else -> null
            }

            result?.let {
                val intent = Intent().apply {
                    putExtra("result", it)
                    putExtra("nim", "235150701111031")
                    putExtra("nama", "Elgin Brian Wahyu Bramadhika")
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            } ?: run {
                Toast.makeText(this, "Terjadi kesalahan perhitungan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
