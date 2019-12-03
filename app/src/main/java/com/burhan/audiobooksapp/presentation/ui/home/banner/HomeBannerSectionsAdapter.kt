package com.burhan.audiobooksapp.presentation.ui.home.banner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.burhan.audiobooksapp.domain.model.AudioBook

/**
 * Developed by tcbaras on 2019-12-03.
 */
class HomeBannerSectionsAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var audioBooks = listOf<AudioBook>()

    override fun getItem(position: Int): Fragment {
        return BannerItemFragment.newInstance(audioBooks[position % audioBooks.size])
    }

    override fun getCount(): Int {
        return if(audioBooks.isNotEmpty()) Int.MAX_VALUE else 0
    }

    fun setData(audioBooks: List<AudioBook>) {
        this.audioBooks = audioBooks
        notifyDataSetChanged()
    }
}