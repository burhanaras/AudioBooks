package com.listenhub.audiobooksapp.presentation.ui.home.banner


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.listenhub.audiobooksapp.R
import com.listenhub.audiobooksapp.presentation.ui.samples.SamplesFragmentViewModel
import kotlinx.android.synthetic.main.fragment_home_banner.*

/**
 * A simple [Fragment] subclass.
 */
class BannerFragment : Fragment() {

    private lateinit var viewModel: SamplesFragmentViewModel
    private lateinit var adapter: BannerSectionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_banner, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this.activity!!).get(SamplesFragmentViewModel::class.java)
        
        initUI()
        setObservers()
    }

    private fun initUI() {
        adapter = BannerSectionsAdapter(childFragmentManager)
        viewPagerHomeBanner.adapter = adapter
        dotIndicatorBanner.dots(0, true)

        viewPagerHomeBanner.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                dotIndicatorBanner.setSelected(position)
            }
        })
    }

    private fun setObservers() {
        viewModel.bannerData.observe(this, Observer {
            it?.let { audioBooks ->
                adapter.setData(audioBooks)
                dotIndicatorBanner.dots(audioBooks.size, smallDots = true)
            }
        })
    }

}
