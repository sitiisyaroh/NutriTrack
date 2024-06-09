package com.capstone.nutritrack.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.capstone.nutritrack.R
import com.capstone.nutritrack.databinding.ActivitySetGoalsBinding
import java.util.Calendar

class SetGoalsActiivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetGoalsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the spinner with the gender options
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.gender_array, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGender.adapter = adapter

        binding.editTextDob.setOnClickListener { showDatePickerDialog() }
        binding.buttonSaveGoals.setOnClickListener { saveGoals() }
    }

    private fun showDatePickerDialog() {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year1, month1, dayOfMonth ->
                binding.editTextDob.setText(
                    "$dayOfMonth/${month1 + 1}/$year1"
                )
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun saveGoals() {
        // Implement your save logic here
    }
}
