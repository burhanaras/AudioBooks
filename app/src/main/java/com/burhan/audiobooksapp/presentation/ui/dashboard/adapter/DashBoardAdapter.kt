package com.burhan.audiobooksapp.presentation.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.presentation.core.extension.loadFromUrl
import com.burhan.audiobooksapp.presentation.core.extension.setSingleClickListener
import kotlinx.android.synthetic.main.item_audiobook_horizontal.view.*

/**
 * Developed by tcbaras on 2019-12-18.
 */
class DashBoardAdapter(private val onClickListener: (AudioBook) -> Unit) :
    RecyclerView.Adapter<DashBoardAdapter.ViewHolder>() {

    private var data: List<AudioBook> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_audiobook_horizontal, parent, false
        )
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(data[position], onClickListener)

    fun setData(audioBooks: List<AudioBook>) {
        data = audioBooks
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var icon: ImageView = itemView.ivAudioBookHorizontalImage
        private var name: TextView = itemView.tvAudioBookHorizontalName
        private var author: TextView = itemView.tvAudioBookHorizontalAuthor
        private var description: TextView = itemView.tvAudioBookHorizontalDescription

        fun bind(audioBook: AudioBook, onClickListener: (AudioBook) -> Unit) {
            icon.loadFromUrl(audioBook.imageUrl)
            name.text = audioBook.name
            author.text = audioBook.author
            description.text = audioBook.description

            itemView.setSingleClickListener { onClickListener(audioBook) }
        }

    }
}