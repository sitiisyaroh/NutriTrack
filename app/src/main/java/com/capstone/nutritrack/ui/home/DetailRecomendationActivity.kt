package com.capstone.nutritrack.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.nutritrack.R
import com.capstone.nutritrack.databinding.ActivityDetailRecomendationBinding

class DetailRecomendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRecomendationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan data dari Intent
        val foodName = intent.getStringExtra("FOOD_NAME")
        val foodImage = intent.getStringExtra("FOOD_IMAGE")
        val foodRecipe = intent.getStringExtra("FOOD_RECIPE")
        val foodCalories = intent.getFloatExtra("FOOD_CALORIES", 0f)
        val foodProteins = intent.getFloatExtra("FOOD_PROTEINS", 0f)
        val foodFat = intent.getFloatExtra("FOOD_FAT", 0f)
        val foodCarbohydrate = intent.getFloatExtra("FOOD_CARBOHYDRATE", 0f)

        // Menampilkan data ke dalam view
        binding.foodTitle.text = foodName
        binding.caloriesDetail.text = getString(R.string.calories_format, foodCalories)
        binding.fatDetail.text = getString(R.string.fat_format, foodFat)
        binding.carbsDetail.text = getString(R.string.carbs_format, foodCarbohydrate)
        binding.proteinDetail.text = getString(R.string.protein_format, foodProteins)

        // Menggunakan Glide atau Picasso untuk memuat gambar
        // Glide.with(this).load(foodImage).into(binding.foodImageDetail)

        // Menampilkan resep makanan
        // binding.recipeDetail.text = foodRecipe

        // Mengatur action ketika tombol kembali ditekan
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}
