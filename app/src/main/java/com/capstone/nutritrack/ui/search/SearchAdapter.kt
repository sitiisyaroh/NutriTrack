package com.capstone.nutritrack.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.nutritrack.databinding.ItemSearchfoodBinding
import com.capstone.nutritrack.response.SearchFoodResponse

class SearchAdapter(
    var foodList: List<SearchFoodResponse>,
    private val onItemClicked: (SearchFoodResponse) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchfoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    override fun getItemCount() = foodList.size

    class SearchViewHolder(
        private val binding: ItemSearchfoodBinding,
        private val onItemClicked: (SearchFoodResponse) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: SearchFoodResponse) {
            binding.tvFoodItem.text = food.name
            val formattedCalories = String.format("%.2f Kcal", food.calories)
            binding.tvKaloriItem.text = formattedCalories
            binding.root.setOnClickListener {
                onItemClicked(food)
            }
        }
    }

    fun updateData(newFoodList: List<SearchFoodResponse>) {
        foodList = newFoodList
        notifyDataSetChanged()
    }
}

