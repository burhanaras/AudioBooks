package com.listenhub.audiobooksapp.presentation.ui.audiobookdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.listenhub.audiobooksapp.domain.model.AudioBook
import com.listenhub.audiobooksapp.presentation.core.extension.loadFromUrl
import com.listenhub.audiobooksapp.presentation.core.extension.setSingleClickListener
import com.listenhub.audiobooksapp.presentation.ui.player.NowPlayingActivity
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_audio_book_detail.*
import kotlinx.android.synthetic.main.content_audio_book_detail.*
import kotlin.math.abs


class AudioBookDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.listenhub.audiobooksapp.R.layout.activity_audio_book_detail)
        setSupportActionBar(toolbar)
        setUpToolbar()

        intent.getParcelableExtra<AudioBook>(ARG_AUDIO_BOOK)?.let { audioBook ->
            tvAudioBookDetailTitle.text = audioBook.name
            ivAudioBookDetailImage.loadFromUrl(audioBook.imageUrl)
            tvAudioBookDetailName.text = audioBook.name
            tvAudioBookDetailAuthor.text = audioBook.author
            tvAudioBookDetailDescription.text = audioBook.description

            fab.setSingleClickListener {
                startActivity(NowPlayingActivity.newIntent(this, audioBook))
            }
        }
    }

    private fun setUpToolbar() {
        supportActionBar?.apply { setDisplayHomeAsUpEnabled(true) }
        title = ""
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            tvAudioBookDetailTitle.alpha = abs(
                verticalOffset / appBarLayout.totalScrollRange.toFloat()
            )
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val ARG_AUDIO_BOOK: String = "ARG_AUDIO_BOOK"
        fun newIntent(callerActivity: Context, audioBook: AudioBook) =
            Intent(callerActivity, AudioBookDetailActivity::class.java).apply {
                putExtra(ARG_AUDIO_BOOK, audioBook)
            }

    }
}
