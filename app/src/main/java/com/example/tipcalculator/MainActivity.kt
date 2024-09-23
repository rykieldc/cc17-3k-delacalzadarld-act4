package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonCalculateTip.setOnClickListener {
            calculateTip()
        }
    }

    private fun calculateTip() {
        val costString = binding.inputServiceCost.text.toString()

        val cost = costString.toDoubleOrNull()
        if (cost == null) {
            binding.outputFinalTip.text = getString(R.string.output_final_tip, "0.00")
            return
        }

        val selectedTip = binding.optionsServiceQuality.checkedRadioButtonId

        val tipPercentage = when (selectedTip) {
            R.id.option_amazing -> 0.20
            R.id.option_good -> 0.15
            else -> 0.10
        }

        var tip = cost * tipPercentage
        val roundUp = binding.switchRoundTip.isChecked
        if (roundUp) {
            tip = ceil(tip)
        }

        val pesoFormat = NumberFormat.getCurrencyInstance()
        pesoFormat.currency = java.util.Currency.getInstance("PHP")
        val currencyTip = pesoFormat.format(tip)

        binding.outputFinalTip.text = getString(R.string.output_final_tip, currencyTip)
    }
}