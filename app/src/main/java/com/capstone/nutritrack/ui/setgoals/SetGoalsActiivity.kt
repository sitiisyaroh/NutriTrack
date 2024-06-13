package com.capstone.nutritrack.ui.setgoals

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.capstone.nutritrack.R
import com.capstone.nutritrack.ViewModelFactory
import com.capstone.nutritrack.data.ResultState
import com.capstone.nutritrack.databinding.ActivitySetGoalsBinding
import java.util.Calendar

class SetGoalsActiivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetGoalsBinding
    private val viewModel: SetGoalsViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

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
        val gender = binding.spinnerGender.selectedItem.toString()
        val dob = binding.editTextDob.text.toString()
        val height = binding.editTextHeight.text.toString().toInt()
        val weight = binding.editTextWeight.text.toString().toInt()
        val goalWeight = binding.editTextGoalWeight.text.toString().toInt()

        viewModel.setGoals(gender, dob, height, weight, goalWeight).observe(this, Observer { result ->
            when(result) {
                is ResultState.Loading -> {
                    // Show loading indicator
                }
                is ResultState.Success -> {
                    Toast.makeText(this, "Goals saved successfully!", Toast.LENGTH_SHORT).show()
                    // Navigate to AccountFragment with the BMI category
                    val intent = Intent()
                    intent.putExtra("bmi_category", result.data.bmiCategory)
                    setResult(RESULT_OK, intent)
                    finish()
                }
                is ResultState.Error -> {
                    Toast.makeText(this, "Please Try Again!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}