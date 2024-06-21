package com.capstone.nutritrack.ui.detailfood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.capstone.nutritrack.R
import com.capstone.nutritrack.data.api.ApiConfig
import com.capstone.nutritrack.data.request.AddFoodIntakeRequest
import com.capstone.nutritrack.ui.FrameActivity
import com.capstone.nutritrack.ui.main.MainActivity
import com.capstone.nutritrack.ui.search.SearchActivity
import com.capstone.nutritrack.ui.tracking.TrackingFragment
import kotlinx.coroutines.launch

class DetailFoodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_food)

        // Initialize components from the layout
        val backButton: ImageButton = findViewById(R.id.backButton)
        val foodTitle: TextView = findViewById(R.id.foodTitle)
        val tvCalories: TextView = findViewById(R.id.calories_detail)
        val tvFat: TextView = findViewById(R.id.fat_detail)
        val tvCarbs: TextView = findViewById(R.id.carbs_detail)
        val tvProtein: TextView = findViewById(R.id.protein_detail)
        val saveButton: Button = findViewById(R.id.saveButton)

        // Get food data from the intent
        val foodName = intent.getStringExtra("FOOD_NAME")
        val calories = intent.getDoubleExtra("FOOD_CALORIES", 0.0)
        val fat = intent.getDoubleExtra("FOOD_FAT", 0.0)
        val carbs = intent.getDoubleExtra("FOOD_CARBS", 0.0)
        val protein = intent.getDoubleExtra("FOOD_PROTEIN", 0.0)
        val userId = intent.getStringExtra("USER_ID") // Retrieve the user ID

        // Log retrieved values for debugging
        Log.d("DetailFoodActivity", "foodName: $foodName, calories: $calories, fat: $fat, carbs: $carbs, protein: $protein, userId: $userId")

        // Display food data on TextViews
        foodTitle.text = foodName
        tvCalories.text = "$calories kcal"
        tvFat.text = "$fat g"
        tvCarbs.text = "$carbs g"
        tvProtein.text = "$protein g"

        // Event listener for back button
        backButton.setOnClickListener {
            finish() // Close the Detail Food activity
        }

        // Event listener for save button
        saveButton.setOnClickListener {
            // Make sure the food name and user ID are not null
            if (foodName.isNullOrBlank()) {
                Log.d("DetailFoodActivity", "Invalid food name")
                Toast.makeText(this, "Invalid food name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (userId.isNullOrBlank()) {
                Log.d("DetailFoodActivity", "Invalid user ID")
                Toast.makeText(this, "Invalid user ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create the request object
            val request = AddFoodIntakeRequest(
                userId = userId,
                foodName = foodName,
                calories = calories
            )

            // Call the API to save food intake
            lifecycleScope.launch {
                try {
                    val response = ApiConfig.getApiService().addFoodIntake(request)
                    if (response.isSuccessful) {
                        val foodIntakeResponse = response.body()
                        if (foodIntakeResponse != null) {
                            Log.d("DetailFoodActivity", "Food intake saved successfully: $foodIntakeResponse")
                            Toast.makeText(this@DetailFoodActivity, "Successfully added food", Toast.LENGTH_SHORT).show()

                            // After successfully saving, start TrackingFragment with the new data
                            val intent = Intent(this@DetailFoodActivity, FrameActivity::class.java).apply {
                                putExtra("NAVIGATE_TO_TRACKING", true)
                            }
                            startActivity(intent)
                            finish()
                        } else {
                            Log.d("DetailFoodActivity", "Empty response body")
                            Toast.makeText(this@DetailFoodActivity, "Empty response body", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.d("DetailFoodActivity", "Failed to save food intake: ${response.errorBody()?.string()}")
                        Toast.makeText(this@DetailFoodActivity, "Failed to save food intake", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.d("DetailFoodActivity", "Error: ${e.message}")
                    Toast.makeText(this@DetailFoodActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}