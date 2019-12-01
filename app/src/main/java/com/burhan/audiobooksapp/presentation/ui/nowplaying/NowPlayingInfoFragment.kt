package com.burhan.audiobooksapp.presentation.ui.nowplaying


import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.presentation.core.extension.loadFromUrl
import com.burhan.audiobooksapp.presentation.core.extension.setSingleClickListener
import com.burhan.audiobooksapp.presentation.ui.player.NowPlayingActivityViewModel
import com.burhan.audiobooksapp.presentation.ui.player.audiofocus.AudioFocusObserver
import kotlinx.android.synthetic.main.fragment_now_playing_info.*


class NowPlayingInfoFragment : Fragment() {
    private lateinit var viewModel: NowPlayingActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_now_playing_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NowPlayingActivityViewModel::class.java)
        requestAudioFocus()

        setObservers()
        initUI()
        getExtras()
    }

    private fun requestAudioFocus() {
        val audioManager: AudioManager =
            context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        lifecycle.addObserver(AudioFocusObserver(audioManager))
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
    }

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
    }

    private fun getExtras() {
        arguments?.let {
            it.getParcelable<AudioBook>(ARG_AUDIO_BOOK)?.let { audioBook ->
                tvPlayerAudioBookName.text = audioBook.name
                tvPlayerAuthor.text = audioBook.author
                ivPlayerAudioBookImage.loadFromUrl(audioBook.imageUrl)
            }
        }
    }

    companion object {
        private const val ARG_AUDIO_BOOK = "ARG_AUDIO_BOOK"

        @JvmStatic
        fun newInstance(audioBook: AudioBook) =
            NowPlayingInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_AUDIO_BOOK, audioBook)
                }
            }
    }
}
