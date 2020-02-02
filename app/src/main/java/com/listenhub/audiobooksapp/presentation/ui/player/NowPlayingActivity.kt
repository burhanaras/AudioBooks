package com.listenhub.audiobooksapp.presentation.ui.player

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.listenhub.audiobooksapp.R
import com.listenhub.audiobooksapp.domain.model.AudioBook
import com.listenhub.audiobooksapp.presentation.core.extension.loadFromUrl
import com.listenhub.audiobooksapp.presentation.core.extension.setSingleClickListener
import com.listenhub.audiobooksapp.presentation.ui.player.audiofocus.AudioFocusObserver
import kotlinx.android.synthetic.main.activity_now_playing.*

class NowPlayingActivity : AppCompatActivity() {

    private lateinit var viewModel: NowPlayingActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)
        requestAudioFocus()

        viewModel = initViewModels()
        setObservers()
        initUI()

        intent.getParcelableExtra<AudioBook>(ARG_AUDIO_BOOK)?.let { audioBook ->
            viewModel.play(audioBook)
        }
    }

    private fun requestAudioFocus() {
        val audioManager: AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        lifecycle.addObserver(AudioFocusObserver(audioManager))
    }

    private fun initViewModels() =
        ViewModelProviders.of(this).get(NowPlayingActivityViewModel::class.java)


    private fun initUI() {
        ivNowPlayingPlayPauseButton.setSingleClickListener {
            viewModel.togglePlayPause()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                viewModel.seekBarProgressChanged(seekBar.progress, true)
            }
        })
        ivNowPlayingShare.setSingleClickListener { viewModel.onClickShare() }
    }

    private fun setObservers() {
        viewModel.nowPlayingSDO.observe(this, Observer {
            it?.let { nowPlayingSDO ->
                tvPlayerAudioBookName.text = nowPlayingSDO.title
                tvPlayerAuthor.text = nowPlayingSDO.author
                ivPlayerAudioBookImage.loadFromUrl(nowPlayingSDO.imageUrl)
            }
        })
        viewModel.nowPlayingTimeInfoSDO.observe(this, Observer {
            it?.let { nowPlayingTimeInfoSDO ->
                tvNowPlayingProgress.text = nowPlayingTimeInfoSDO.progress
                tvNowPlayingDuration.text = nowPlayingTimeInfoSDO.duration
                seekBar.progress = nowPlayingTimeInfoSDO.seekBarProgress
                seekBar.max = nowPlayingTimeInfoSDO.seekBarMaxValue
                ivNowPlayingPlayPauseButton.setImageResource(nowPlayingTimeInfoSDO.playPauseButtonIcon)
            }
        })
        viewModel.shareIntent.observe(this, Observer { startActivity(it) })
    }

    companion object {
        private const val ARG_AUDIO_BOOK: String = "ARG_AUDIO_BOOK"
        fun newIntent(callerActivity: Activity, audioBook: AudioBook?) =
            Intent(callerActivity, NowPlayingActivity::class.java).apply {
                putExtra(ARG_AUDIO_BOOK, audioBook)
            }
    }
}
