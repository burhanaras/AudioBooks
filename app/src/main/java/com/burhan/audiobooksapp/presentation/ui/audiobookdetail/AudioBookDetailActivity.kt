package com.burhan.audiobooksapp.presentation.ui.audiobookdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.presentation.core.extension.loadFromUrl
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_audio_book_detail.*
import kotlin.math.abs


class AudioBookDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.burhan.audiobooksapp.R.layout.activity_audio_book_detail)
        setSupportActionBar(toolbar)
        setUpToolbar()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        intent.getParcelableExtra<AudioBook>(ARG_AUDIO_BOOK)?.let { audioBook ->
            tvAudioBookDetailTitle.text = audioBook.name
            ivAudioBookDetailImage.loadFromUrl(audioBook.imageUrl)
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
