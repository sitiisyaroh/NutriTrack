package com.capstone.nutritrack.ui.search

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.nutritrack.databinding.ActivitySearchBinding
import com.capstone.nutritrack.ui.detailfood.DetailFoodActivity

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        // Get user ID from intent or other source
        val userId = intent.getStringExtra("USER_ID")

        // Set up RecyclerView
        binding.rvFood.layoutManager = LinearLayoutManager(this)
        adapter = SearchAdapter(emptyList()) { food ->
            val intent = Intent(this, DetailFoodActivity::class.java).apply {
                putExtra("FOOD_NAME", food.name)
                putExtra("FOOD_CALORIES", food.calories)
                putExtra("FOOD_FAT", food.fat)
                putExtra("FOOD_CARBS", food.carbs)
                putExtra("FOOD_PROTEIN", food.protein)
                putExtra("USER_ID", userId) // Pass user ID to DetailFoodActivity
            }
            startActivity(intent)
        }
        binding.rvFood.adapter = adapter

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearch(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })

        // Observe search results
        viewModel.searchResults.observe(this) { results ->
            adapter.foodList = results // Update adapter data
            adapter.notifyDataSetChanged() // Notify adapter about data change
        }
    }

    private fun performSearch(query: String) {
        viewModel.searchFood(query)
    }
}
