package com.capstone.nusart.ui_page.ui.home.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.nusart.R

class CategoryAdapter (private val categoryList: List<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryList[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int = categoryList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryImageView: ImageView = itemView.findViewById(R.id.categoryImageView)
        private val categoryNameTextView: TextView = itemView.findViewById(R.id.categoryNameTextView)

        fun bind(category: CategoryModel) {
            categoryImageView.setImageResource(category.categoryImageResId)
            categoryNameTextView.text = category.categoryName
            // Add any additional binding logic if needed
        }
    }
}