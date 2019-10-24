package com.burhan.audiobooksapp.presentation.ui.audiobookdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_audio_book_detail.*

class AudioBookDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_book_detail)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    companion object {
        private const val ARG_AUDIO_BOOK: String = "ARG_AUDIO_BOOK"
        fun newIntent(callerActivity: Context, audioBook: AudioBook) =
            Intent(callerActivity, AudioBookDetailActivity::class.java).apply {
                putExtra(ARG_AUDIO_BOOK, audioBook)
            }

    }
}
