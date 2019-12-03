package com.burhan.audiobooksapp.presentation.ui.home.banner


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.presentation.ui.home.HomeFragmentViewModel
import kotlinx.android.synthetic.main.fragment_home_banner.*

/**
 * A simple [Fragment] subclass.
 */
class HomeBannerFragment : Fragment() {

    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var adapter: HomeBannerSectionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_banner, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this.activity!!).get(HomeFragmentViewModel::class.java)

        adapter = HomeBannerSectionsAdapter(childFragmentManager)
        viewPagerHomeBanner.adapter = adapter

        viewModel.bannerData.observe(this, Observer {
            it?.let { audioBooks ->
                adapter.setData(audioBooks)
            }
        })
    }

}
