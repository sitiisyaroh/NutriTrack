package com.capstone.nutritrack.ui.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.nutritrack.R
import com.capstone.nutritrack.dummy.news.News

class NewsAdapter(
    private val context: Context,
    private val newsList: List<News>,
    private val onItemClick: (News) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        private val imgItemPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(newsList[position])
                }
            }
        }

        fun bind(news: News) {
            tvTitle.text = news.title
            tvAuthor.text = news.author

            // Load gambar dari drawable menggunakan Glide
            val imageResId = context.resources.getIdentifier(news.image, "drawable", context.packageName)
            Glide.with(context)
                .load(imageResId)
                .centerCrop()
//                .placeholder(R.drawable.placeholder_image) // Placeholder saat gambar sedang dimuat
//                .error(R.drawable.error_image) // Gambar yang ditampilkan jika ada kesalahan
                .into(imgItemPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}