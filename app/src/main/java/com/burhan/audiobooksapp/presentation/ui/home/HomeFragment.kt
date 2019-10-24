package com.burhan.audiobooksapp.presentation.ui.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.presentation.ui.home.adapter.HomeCategoriesAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    companion object {
        val TAG: String = HomeFragment::class.java.simpleName
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeFragmentViewModel
    private val adapter = HomeCategoriesAdapter()

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

    private fun setObservers() {
        viewModel.data.observe(this, Observer { data ->
            adapter.setData(data)
        })
    }

    private fun initViewModels() = ViewModelProviders.of(this).get(HomeFragmentViewModel::class.java)

    private fun setRecyclerView() {
        rvHomeCategories.setHasFixedSize(true)
        rvHomeCategories.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvHomeCategories.adapter = adapter
    }

}
