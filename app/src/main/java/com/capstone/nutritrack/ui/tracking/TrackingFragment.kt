package com.capstone.nutritrack.ui.tracking

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.nutritrack.ViewModelFactory
import com.capstone.nutritrack.data.ResultState
import com.capstone.nutritrack.databinding.FragmentTrackingBinding
import com.capstone.nutritrack.ml.FoodAdapter
import com.capstone.nutritrack.ml.FoodRecommendationModel
import com.capstone.nutritrack.response.GetFoodIntakeResponseItem
import com.capstone.nutritrack.ui.search.SearchActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TrackingFragment : Fragment() {

    private var _binding: FragmentTrackingBinding? = null
    private val binding get() = _binding!!
    private lateinit var trackingViewModel: TrackingViewModel
    private var userIdLogin: String? = null
    private lateinit var foodIntakeAdapter: FoodIntakeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val factory = ViewModelFactory.getInstance(requireContext())
        trackingViewModel = ViewModelProvider(this, factory).get(TrackingViewModel::class.java)

        trackingViewModel.getSession().observe(viewLifecycleOwner, Observer { user ->
            user.userId?.let { id ->
                userIdLogin = id
                trackingViewModel.fetchGoals(id)
                trackingViewModel.fetchFoodIntake(id)  // Fetch food intake based on userId
            }
        })

        trackingViewModel.goals.observe(viewLifecycleOwner, Observer { goals ->
            if (goals.isNotEmpty()) {
                val firstGoal = goals[0]
                val dailyCalorieNeeds = firstGoal.dailyCalorieNeeds

                // Set target calorie with correct format
                binding.targetCal.text = String.format("%.2f", dailyCalorieNeeds)

                // Load recommendations based on dailyCalValue
                loadRecommendations(dailyCalorieNeeds)

                // Calculate and display achieved and remaining calories
//                val achievedCalories = calculateAchievedCalories()
//                Log.d("awww3", achievedCalories.toString())
//                val remainingCalories = dailyCalorieNeeds - achievedCalories
//                Log.d("awww", dailyCalorieNeeds.toString())
//                Log.d("awww4", achievedCalories.toString())
//                Log.d("awww", remainingCalories.toString())
//
//                // Set achieved calories with correct format
//                binding.achievedCal.text = String.format("%.2f", achievedCalories)
//
//                // Set remaining calories with correct format
//                binding.remainingCal.text = String.format("%.2f", remainingCalories)
            }
        })

        // Initialize dateTextView with current date
        val currentDate = getCurrentDate()
        binding.dateTextView.text = currentDate

        binding.calendarIcon.setOnClickListener {
            showDatePickerDialog()
        }

        binding.addItemButton.setOnClickListener {
            onBreakfastClicked()
        }

        foodIntakeAdapter = FoodIntakeAdapter(requireContext(), emptyList())

        // Set up RecyclerView
        binding.foodRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = foodIntakeAdapter
        }

        trackingViewModel.foodIntake.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultState.Success -> {
                    foodIntakeAdapter.updateData(result.data)
                    // Update achieved calories when food intake data is loaded
                    val achievedCalories = calculateAchievedCalories(result.data)
                    val dailyCalorieNeeds =
                        trackingViewModel.goals.value?.firstOrNull()?.dailyCalorieNeeds ?: 0f
                    val remainingCalories = maxOf(0f, dailyCalorieNeeds - achievedCalories)
                    binding.achievedCal.text = String.format("%.2f", achievedCalories)
                    if (remainingCalories >= 0) {
                        Log.d("RemainingCal", "Remaining calories: $remainingCalories")
                        binding.remainingCal.text = String.format("%.2f", remainingCalories)
                    } else {
                        Log.d("RemainingCal", "Exceeded the target")
                        binding.remainingCal.text = "Exceeded the target"
                    }
                }
                // Handle other states
                is ResultState.Error -> {
                    // Handle error state
                    Toast.makeText(requireContext(), "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
                is ResultState.Loading -> {
                    // Handle loading state if necessary
                }
            }
        })



        return root
    }

    private fun calculateAchievedCalories(foodIntake: List<GetFoodIntakeResponseItem> = emptyList()): Float {
        var totalCalories = 0f
        Log.d("calculateAchievedCalories", "foodIntake size: ${foodIntake.size}")
        foodIntake.forEach { foodItem: GetFoodIntakeResponseItem ->
            Log.d("awww1", foodItem.calories.toString())
            totalCalories += foodItem.calories
        }
        Log.d("awww2",  totalCalories.toString())
        return totalCalories
    }

    private fun loadRecommendations(calories: Float) {
        val model = FoodRecommendationModel(requireContext())
        val recommendations = model.getRecommendations(requireContext(), calories)
        if (recommendations.isNotEmpty()) {
            val adapter = FoodAdapter(requireContext(), recommendations) { food ->
                val intent = Intent(requireContext(), SearchActivity::class.java).apply {
                    putExtra("FOOD_NAME", food.name)
                    putExtra("FOOD_IMAGE", food.image)
                    putExtra("FOOD_RECIPE", food.recipe)
                    putExtra("FOOD_CALORIES", food.calories)
                    putExtra("FOOD_PROTEINS", food.proteins)
                    putExtra("FOOD_FAT", food.fat)
                    putExtra("FOOD_CARBOHYDRATE", food.carbohydrate)
                    putExtra("USER_ID", userIdLogin)
                }
                startActivity(intent)
            }
        }
    }

    private fun onBreakfastClicked() {
        val calories = trackingViewModel.goals.value?.firstOrNull()?.dailyCalorieNeeds ?: 0f
        loadRecommendationsAndStartSearchActivity(calories)
    }

    private fun loadRecommendationsAndStartSearchActivity(calories: Float) {
        val model = FoodRecommendationModel(requireContext())
        val recommendations = model.getRecommendations(requireContext(), calories)
        if (recommendations.isNotEmpty()) {
            // Select the first recommendation for demonstration
            val food = recommendations.first()
            val intent = Intent(requireContext(), SearchActivity::class.java).apply {
                putExtra("FOOD_NAME", food.name)
                putExtra("FOOD_IMAGE", food.image)
                putExtra("FOOD_RECIPE", food.recipe)
                putExtra("FOOD_CALORIES", food.calories)
                putExtra("FOOD_PROTEINS", food.proteins)
                putExtra("FOOD_FAT", food.fat)
                putExtra("FOOD_CARBOHYDRATE", food.carbohydrate)
                putExtra("USER_ID", userIdLogin)
            }
            startActivity(intent)
        } else {
            // Handle case when there are no recommendations
            Toast.makeText(requireContext(), "No recommendations available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("EEEE, dd/MM/yyyy", Locale.getDefault())
                val selectedDate = if (selectedCalendar.after(calendar)) {
                    // If selected date is after today, set it to today's date
                    calendar.timeInMillis = selectedCalendar.timeInMillis
                    dateFormat.format(calendar.time)
                } else {
                    dateFormat.format(selectedCalendar.time)
                }
                binding.dateTextView.text = selectedDate // Update the TextView with selected date
            }, year, month, day
        )
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis // Set max date to today
        datePickerDialog.show()
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEEE, dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}