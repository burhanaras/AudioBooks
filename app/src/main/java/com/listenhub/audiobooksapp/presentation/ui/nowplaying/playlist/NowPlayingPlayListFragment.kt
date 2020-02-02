package com.listenhub.audiobooksapp.presentation.ui.nowplaying.playlist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.listenhub.audiobooksapp.R
import com.listenhub.audiobooksapp.presentation.ui.player.NowPlayingActivityViewModel
import kotlinx.android.synthetic.main.fragment_now_playing_play_list.*

class NowPlayingPlayListFragment : Fragment() {

    private lateinit var viewModel: NowPlayingActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_now_playing_play_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NowPlayingActivityViewModel::class.java)

        val adapter =
            NowPlayingListAdapter { selectedAudioBookPosition ->
                viewModel.audioBookSelectedInPlayList(selectedAudioBookPosition)
            }

        rvNowPlayingList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            setAdapter(adapter)
        }

        viewModel.nowPlayingPlayListSDO.observe(this, Observer {
            it?.let { playList ->
                adapter.setData(playList)
                rvNowPlayingList.smoothScrollToPosition(playList.currentInd)
            }
        })
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            NowPlayingPlayListFragment()
    }
}
