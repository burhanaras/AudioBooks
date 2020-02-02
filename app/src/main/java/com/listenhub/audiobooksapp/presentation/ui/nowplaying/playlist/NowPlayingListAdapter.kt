package com.listenhub.audiobooksapp.presentation.ui.nowplaying.playlist

/**
 * Developed by tcbaras on 2019-12-01.
 */

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.listenhub.audiobooksapp.R
import com.listenhub.audiobooksapp.domain.model.AudioBook
import com.listenhub.audiobooksapp.presentation.core.extension.loadFromUrl
import com.listenhub.audiobooksapp.presentation.core.extension.setSingleClickListener
import com.listenhub.audiobooksapp.presentation.ui.player.service.PlayList
import kotlinx.android.synthetic.main.item_play_list_layout.view.*

class NowPlayingListAdapter(private var onClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<NowPlayingListAdapter.ViewHolder>() {

    internal var data = PlayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_play_list_layout,
                parent,
                false
            )
        )

    override fun getItemCount() = data.audioBooks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(data.audioBooks[position], position == data.currentInd, onClickListener)

    fun setData(playList: PlayList) {
        data = playList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.ivPlayListAudioBookImage
        private val name: TextView = itemView.tvPlayListaudioBookName
        private val author: TextView = itemView.tvPlayListAuthor

        fun bind(audioBook: AudioBook, isHighLighted: Boolean = false, callback: (Int) -> Unit) {
            name.text = audioBook.name
            author.text = audioBook.author
            icon.loadFromUrl(audioBook.imageUrl)
            name.setTextColor(if (isHighLighted) Color.RED else Color.BLACK)
            itemView.setSingleClickListener { callback(adapterPosition) }
        }
    }

}
