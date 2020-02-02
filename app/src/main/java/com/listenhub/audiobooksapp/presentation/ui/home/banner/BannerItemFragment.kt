package com.listenhub.audiobooksapp.presentation.ui.home.banner


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.listenhub.audiobooksapp.R
import com.listenhub.audiobooksapp.domain.model.AudioBook
import com.listenhub.audiobooksapp.presentation.core.extension.createPalette
import com.listenhub.audiobooksapp.presentation.core.extension.loadBitmapFromUrl
import com.listenhub.audiobooksapp.presentation.core.extension.loadFromUrl
import com.listenhub.audiobooksapp.presentation.core.extension.setSingleClickListener
import com.listenhub.audiobooksapp.presentation.ui.audiobookdetail.AudioBookDetailActivity
import kotlinx.android.synthetic.main.fragment_banner_item.*

/**
 * A simple [Fragment] subclass.
 */
class BannerItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_banner_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.getParcelable<AudioBook>(ARG_AUDIO_BOOK)?.let { audiobook ->
            tvBannerAudioBookName.text = audiobook.name
            tvBannerAudioBookDescription.text = audiobook.description
            ivBannerAudioBook.loadFromUrl(audiobook.imageUrl)

            ivBannerAudioBookBlurredBackground.loadBitmapFromUrl(audiobook.imageUrl) { bitmap ->
                bitmap.createPalette { palette ->
                    ivBannerAudioBookBlurredBackground.setBackgroundColor(
                        palette.lightMutedSwatch?.rgb ?: Color.WHITE
                    )
                    tvBannerAudioBookName.setTextColor(
                        palette.vibrantSwatch?.titleTextColor ?: Color.BLACK
                    )
                }
            }

            view?.setSingleClickListener {
                startActivity(context?.let { it1 ->
                    AudioBookDetailActivity.newIntent(
                        it1,
                        audiobook
                    )
                })
            }
        }
    }


    companion object {
        private const val ARG_AUDIO_BOOK: String = "ARG_AUDIO_BOOK"
        fun newInstance(audioBook: AudioBook) = BannerItemFragment().apply {
            arguments = Bundle().apply { putParcelable(ARG_AUDIO_BOOK, audioBook) }
        }
    }
}
