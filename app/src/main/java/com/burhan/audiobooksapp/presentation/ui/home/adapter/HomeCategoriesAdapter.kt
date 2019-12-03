package com.burhan.audiobooksapp.presentation.ui.home.adapter

/**
 * Developed by tcbaras on 2019-10-24.
 */

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.domain.model.Category
import kotlinx.android.synthetic.main.item_audiobook_row_list.view.*

class HomeCategoriesAdapter(private val onClickListener: (AudioBook) -> Unit) :
    RecyclerView.Adapter<HomeCategoriesAdapter.ViewHolder>() {

    private var data: MutableList<Category> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_audiobook_row_list,
                parent,
                false
            )
        )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(data[position], onClickListener)

    fun setData(data: List<Category>?) {
        data?.let {
            this.data = it.toMutableList()
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryName: TextView = itemView.tvCategoryTitle
        private val recyclerView: RecyclerView = itemView.rvAudioBookRowList

        fun bind(
            category: Category,
            onClickListener: (AudioBook) -> Unit
        ) {
            categoryName.text = category.name
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager =
                LinearLayoutManager(recyclerView.context, RecyclerView.HORIZONTAL, false)
            recyclerView.clipToPadding = false
            val adapter = AudioBookAdapter(onClickListener)
            recyclerView.adapter = adapter
            adapter.setData(category.audioBooks)
        }
    }

}
