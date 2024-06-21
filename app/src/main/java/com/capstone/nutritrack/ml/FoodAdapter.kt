package com.capstone.nutritrack.ml

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import com.bumptech.glide.Glide
import com.capstone.nutritrack.R
import com.capstone.nutritrack.ml.FoodRecommendation

class FoodAdapter(
    private val context: Context,
    private val foodList: List<FoodRecommendation>,
    private val onItemClicked: (FoodRecommendation) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFoodItem: TextView = itemView.findViewById(R.id.tvFoodItems)
        val tvCaloriItem: TextView = itemView.findViewById(R.id.tvCaloriItem)
        val foodImage: ImageView = itemView.findViewById(R.id.img_item_ml)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        holder.tvFoodItem.text = food.name
        holder.tvCaloriItem.text = "${food.calories} Kcal"
        Glide.with(context).load(food.image).into(holder.foodImage)
        holder.itemView.setOnClickListener { onItemClicked(food) }
    }

    override fun getItemCount() = foodList.size
}