package com.capstone.nutritrack.ui.home

import android.content.Intent
import android.os.Bundle
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
import com.capstone.nutritrack.databinding.FragmentHomeBinding
import com.capstone.nutritrack.ml.FoodAdapter
import com.capstone.nutritrack.ml.FoodRecommendationModel
import com.capstone.nutritrack.response.GetFoodIntakeResponseItem
import com.capstone.nutritrack.ui.tracking.FoodIntakeAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private var userIdLogin: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val factory = ViewModelFactory.getInstance(requireContext())
        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        homeViewModel.getSession().observe(viewLifecycleOwner, Observer { user ->
            user.userId?.let { userId ->
                userIdLogin = userId
                homeViewModel.fetchGoals(userId)
                homeViewModel.fetchFoodIntake(userId)  // Fetch food intake based on userId
            }
        })

        homeViewModel.goals.observe(viewLifecycleOwner, Observer { goals ->
            if (goals.isNotEmpty()) {
                val firstGoal = goals[0]
                val dailyCalorieNeeds = firstGoal.dailyCalorieNeeds
                binding.dailyCalValue.text = String.format("%.2f Kcal", dailyCalorieNeeds)

                // Load recommendations based on dailyCalValue
                loadRecommendations(dailyCalorieNeeds)
            }
        })

        homeViewModel.foodIntake.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultState.Success -> {
                    val achievedCalories = calculateAchievedCalories(result.data)
                    val dailyCalorieNeeds = homeViewModel.goals.value?.firstOrNull()?.dailyCalorieNeeds ?: 0f
                    val remainingCalories = maxOf(0f, dailyCalorieNeeds - achievedCalories)

                    binding.dailyCalValue.text = String.format("%.2f Kcal", dailyCalorieNeeds)

                    if (remainingCalories >= 0) {
                        binding.remainingCalValue.text = String.format("%.2f Kcal", remainingCalories)
                    } else {
                        binding.remainingCalValue.text = "Exceeded the target"
                    }
                }
                is ResultState.Error -> {
                    // Handle error state
                    Toast.makeText(requireContext(), "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
                is ResultState.Loading -> {
                    // Handle loading state if necessary
                }
            }
        })

        homeViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            // Display error message, e.g., using a Toast or Snackbar
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
        })

        binding.rcListHome.layoutManager = LinearLayoutManager(context)

        return root
    }

    private fun calculateAchievedCalories(foodIntake: List<GetFoodIntakeResponseItem> = emptyList()): Float {
        var totalCalories = 0f
        foodIntake.forEach { foodItem: GetFoodIntakeResponseItem ->
            totalCalories += foodItem.calories
        }
        return totalCalories
    }

    private fun loadRecommendations(calories: Float) {
        val model = FoodRecommendationModel(requireContext())
        val recommendations = model.getRecommendations(requireContext(), calories)
        if (recommendations.isNotEmpty()) {
            val adapter = FoodAdapter(requireContext(), recommendations) { food ->
                val intent = Intent(requireContext(), DetailRecomendationActivity::class.java).apply {
                    putExtra("FOOD_NAME", food.name)
                    putExtra("FOOD_IMAGE", food.image)
                    putExtra("FOOD_RECIPE", food.recipe)
                    putExtra("FOOD_CALORIES", food.calories)
                    putExtra("FOOD_PROTEINS", food.proteins)
                    putExtra("FOOD_FAT", food.fat)
                    putExtra("FOOD_CARBOHYDRATE", food.carbohydrate)
                }
                startActivity(intent)
            }
            binding.rcListHome.adapter = adapter
        } else {
            // Handle case when there are no recommendations
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
