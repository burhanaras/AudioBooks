package com.burhan.audiobooksapp.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.presentation.ui.audiobookdetail.AudioBookDetailActivity
import com.burhan.audiobooksapp.presentation.ui.home.adapter.HomeCategoriesAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeFragmentViewModel
    private val adapter = HomeCategoriesAdapter(object : (AudioBook) -> Unit {
        override fun invoke(audioBook: AudioBook) {
            startActivity(activity?.let { AudioBookDetailActivity.newIntent(it, audioBook) })
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = initViewModels()
        setRecyclerView()
        setObservers()
        viewModel.loadData()
    }

    private fun initViewModels() =
        ViewModelProviders.of(activity!!).get(HomeFragmentViewModel::class.java)

    private fun setRecyclerView() {
        rvHomeCategories.setHasFixedSize(true)
        rvHomeCategories.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvHomeCategories.adapter = adapter
    }

    private fun setObservers() {
        viewModel.data.observe(this, Observer { data ->
            adapter.setData(data)
        })
    }

    companion object {
        val TAG: String = HomeFragment::class.java.simpleName
        fun newInstance() = HomeFragment()
    }

}
