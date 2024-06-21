package com.capstone.nutritrack.ui.tracking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.nutritrack.R
import com.capstone.nutritrack.response.GetFoodIntakeResponseItem

class FoodIntakeAdapter(
    private val context: Context,
    private var foodList: List<GetFoodIntakeResponseItem>
) : RecyclerView.Adapter<FoodIntakeAdapter.FoodIntakeViewHolder>() {

    inner class FoodIntakeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val foodNameTextView: TextView = itemView.findViewById(R.id.tvFoodItems)
        private val foodCaloriesTextView: TextView = itemView.findViewById(R.id.tvCaloriItem)

        fun bind(foodItem: GetFoodIntakeResponseItem) {
            foodNameTextView.text = foodItem.foodName
            val formattedCalories = String.format("%.2f Kcal", foodItem.calories)
            foodCaloriesTextView.text = formattedCalories
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodIntakeViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_food_intake, parent, false)
        return FoodIntakeViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodIntakeViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    override fun getItemCount(): Int = foodList.size

    fun updateData(newFoodList: List<GetFoodIntakeResponseItem>) {
        foodList = newFoodList
        notifyDataSetChanged()
    }
}
