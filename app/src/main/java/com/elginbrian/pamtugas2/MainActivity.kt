package com.elginbrian.pamtugas2

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var display: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.tvDisplay)

        val buttonIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
            R.id.btn8, R.id.btn9, R.id.btnDot,
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnEqual, R.id.btnClear
        )

        buttonIds.forEach { id ->
            findViewById<MaterialButton>(id).setOnClickListener(this)
        }
    }

    override fun onClick(view: View?) {
        if (view is MaterialButton) {
            animateButtonClick(view)
        }

        when (view?.id) {
            R.id.btnClear -> {
                display.text = "0"
            }
            R.id.btnEqual -> {
                val expr = display.text.toString()
                val result = evaluateExpression(expr)
                display.text = result?.toString() ?: "Error"
            }
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide -> {
                val opText = (view as MaterialButton).text.toString()
                var currentText = display.text.toString()
                if (currentText == "0") {
                    currentText = ""
                    display.text = currentText
                }
                if (currentText.isNotEmpty() && currentText.last() in listOf('+', '-', '*', '/')) {
                    display.text = currentText.dropLast(1) + opText
                } else {
                    display.append(opText)
                }
            }
            else -> {
                val btnText = (view as MaterialButton).text.toString()
                if (display.text.toString() == "0") {
                    display.text = btnText
                } else {
                    display.append(btnText)
                }
            }
        }
    }

    private fun animateButtonClick(button: MaterialButton) {
        // Ubah warna tombol menjadi kuning dan teks menjadi hitam
        button.backgroundTintList = ColorStateList.valueOf(Color.YELLOW)
        button.setTextColor(Color.BLACK)

        // Setelah 150ms, kembalikan warna tombol ke abu-abu (#757575) dan teks ke putih
        button.postDelayed({
            button.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#757575"))
            button.setTextColor(Color.WHITE)
        }, 150)
    }

    private fun evaluateExpression(expr: String): Double? {
        val tokens = mutableListOf<String>()
        var current = ""

        for (char in expr) {
            if (char.isDigit() || char == '.') {
                current += char
            } else if (char in listOf('+', '-', '*', '/')) {
                if (current.isNotEmpty()) {
                    tokens.add(current)
                    current = ""
                }
                tokens.add(char.toString())
            }
        }
        if (current.isNotEmpty()) {
            tokens.add(current)
        }
        if (tokens.isEmpty()) return null

        var result = tokens[0].toDoubleOrNull() ?: return null
        var i = 1
        while (i < tokens.size) {
            val op = tokens[i]
            val nextNumber = tokens.getOrNull(i + 1)?.toDoubleOrNull() ?: return null
            when (op) {
                "+" -> result += nextNumber
                "-" -> result -= nextNumber
                "*" -> result *= nextNumber
                "/" -> {
                    if (nextNumber == 0.0) return null
                    result /= nextNumber
                }
                else -> return null
            }
            i += 2
        }
        return result
    }
}
