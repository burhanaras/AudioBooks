package com.burhan.audiobooksapp.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.presentation.ui.audiobookdetail.AudioBookDetailActivity
import com.burhan.audiobooksapp.presentation.ui.dashboard.adapter.DashBoardAdapter
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchFragmentViewModel
    private val adapter = DashBoardAdapter(object : (AudioBook) -> Unit {
        override fun invoke(audioBook: AudioBook) {
            startActivity(activity?.let { AudioBookDetailActivity.newIntent(it, audioBook) })
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchFragmentViewModel::class.java)

        initUI()
        setObservers()
    }

    private fun initUI() {
        rvSearchResults.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvSearchResults.adapter = adapter

        etSearch.addTextChangedListener {
            it?.toString()?.let { query -> viewModel.search(query = query) }
        }
    }

    private fun setObservers() {
        viewModel.searchResults.observe(this, Observer { result ->
            result?.let {
                adapter.setData(it)
            } ?: kotlin.run { adapter.setData(listOf()) }

        })
    }

    companion object {
        val TAG: String = SearchFragment::class.java.simpleName
        fun newInstance() = SearchFragment()
    }

}
