package com.burhan.audiobooksapp.presentation.ui.nowplaying.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.presentation.ui.nowplaying.info.NowPlayingInfoFragment
import com.burhan.audiobooksapp.presentation.ui.nowplaying.playlist.NowPlayingPlayListFragment
import com.burhan.audiobooksapp.presentation.ui.player.NowPlayingActivityViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_now_playing_bottomsheet.*

class NowPlayingBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: NowPlayingActivityViewModel
    private lateinit var sharedViewModel: NowPlayingbottomSheetSharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_now_playing_bottomsheet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NowPlayingActivityViewModel::class.java)
        sharedViewModel =
            ViewModelProviders.of(activity!!).get(NowPlayingbottomSheetSharedViewModel::class.java)
        initUI()
    }

    private fun initUI() {

        arguments?.let {
            it.getParcelable<AudioBook>(ARG_AUDIO_BOOK)?.let { audioBook ->
                NowPlayingBottomSheetSectionsPagerAdapter(
                    childFragmentManager
                ).apply {
                    addFragment(NowPlayingInfoFragment.newInstance(audioBook))
                    addFragment(NowPlayingPlayListFragment.newInstance())
                    viewPagerNowPlayingBottomSheet.adapter = this
                }
            }
        }

        viewPagerNowPlayingBottomSheet.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                dotIndicator.setSelected(position)
            }
        })

        val behavior = BottomSheetBehavior.from(view?.parent as View)
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("BURHANx", "slideOffset:$slideOffset")
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                Log.d("BURHANx", "newState:$newState") //3: Expanded 4: Collapsed
                sharedViewModel.bottomSheetStateChanged(newState)
            }
        })
    }

    companion object {
        val TAG: String = NowPlayingBottomSheetFragment::class.java.simpleName
        private const val ARG_AUDIO_BOOK = "ARG_AUDIO_BOOK"
        fun newInstance(audioBook: AudioBook): NowPlayingBottomSheetFragment =
            NowPlayingBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_AUDIO_BOOK, audioBook)
                }
            }
    }
}
