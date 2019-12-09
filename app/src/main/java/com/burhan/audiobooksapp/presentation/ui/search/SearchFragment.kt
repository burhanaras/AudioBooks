package com.burhan.audiobooksapp.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.presentation.ui.audiobookdetail.AudioBookDetailActivity
import com.burhan.audiobooksapp.presentation.ui.home.adapter.AudioBookAdapter
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchFragmentViewModel
    private lateinit var adapter: AudioBookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchFragmentViewModel::class.java)

        adapter = AudioBookAdapter {
            startActivity(context?.let { it1 -> AudioBookDetailActivity.newIntent(it1, it) })
        }
        rvSearchResults.layoutManager = GridLayoutManager(context, 2)
        rvSearchResults.adapter = adapter

        viewModel.searchResults.observe(this, Observer { result ->
            result?.let {
                adapter.setData(it)
            } ?: kotlin.run { adapter.setData(listOf()) }

        })
        etSearch.addTextChangedListener {
            it?.toString()?.let { query -> viewModel.search(query = query) }
        }
        viewModel.search("")
    }

    companion object {
        val TAG: String = SearchFragment::class.java.simpleName
        fun newInstance() = SearchFragment()
    }

}
