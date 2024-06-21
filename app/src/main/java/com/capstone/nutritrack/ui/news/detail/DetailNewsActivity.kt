package com.capstone.nutritrack.ui.news.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.nutritrack.R
import com.capstone.nutritrack.databinding.ActivityDetailNewsBinding

class DetailNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari Intent
        val title = intent.getStringExtra("NEWS_TITLE")
        val author = intent.getStringExtra("NEWS_AUTHOR")
        val description = intent.getStringExtra("NEWS_DESCRIPTION")
        val imageUrl = intent.getStringExtra("NEWS_IMAGE_URL") // Dapatkan URL gambar

        // Set data ke View
        binding.tvTitleDetail.text = title
        binding.tvAuthor.text = author
        binding.tvDescDetail.text = description

        // Load gambar menggunakan Glide
        val imageResId = resources.getIdentifier(imageUrl, "drawable", packageName)
        Glide.with(this)
            .load(imageResId)
            .centerCrop()
//            .placeholder(R.drawable.placeholder_image) // Placeholder saat gambar sedang dimuat
//            .error(R.drawable.error_image)  // Gambar yang ditampilkan jika ada kesalahan
            .into(binding.ivDetailStory)
    }

}