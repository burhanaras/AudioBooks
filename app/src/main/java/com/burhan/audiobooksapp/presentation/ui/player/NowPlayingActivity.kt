package com.burhan.audiobooksapp.presentation.ui.player

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.presentation.core.extension.loadFromUrl
import kotlinx.android.synthetic.main.activity_now_playing.*

class NowPlayingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)

        intent.getParcelableExtra<AudioBook>(ARG_AUDIO_BOOK)?.let { audioBook ->
            tvPlayerAudioBookName.text = audioBook.name
            ivPlayerAudioBookImage.loadFromUrl(audioBook.imageUrl)
        }
    }

    companion object {
        private const val ARG_AUDIO_BOOK: String = "ARG_AUDIO_BOOK"
        fun newIntent(callerActivity: Activity, audioBook: AudioBook) =
            Intent(callerActivity, NowPlayingActivity::class.java).apply {
                putExtra(ARG_AUDIO_BOOK, audioBook)
            }
    }
}
