package com.burhan.audiobooksapp.presentation.ui.home.adapter

/**
 * Developed by tcbaras on 2019-10-24.
 */

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.domain.model.AudioBook
import kotlinx.android.synthetic.main.item_audiobook.view.*

class AudioBookAdapter : RecyclerView.Adapter<AudioBookAdapter.ViewHolder>() {

    private var data: List<AudioBook> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_audiobook, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])
    fun setData(audioBooks: List<AudioBook>) {
        this.data = audioBooks
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.tvAudioBookName
        private val image: ImageView = itemView.ivAudioBookImage

        fun bind(audioBook: AudioBook) {
            name.text = audioBook.name
            Glide.with(image.context).load(audioBook.imageUrl).into(image)
        }
    }

}
