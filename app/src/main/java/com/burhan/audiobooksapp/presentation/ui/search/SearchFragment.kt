package com.burhan.audiobooksapp.presentation.ui.search

import android.os.Bundle
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.burhan.audiobooksapp.R
import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.presentation.ui.audiobookdetail.AudioBookDetailActivity
import com.burhan.audiobooksapp.presentation.ui.bestsellers.adapter.BestSellersAdapter
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchFragmentViewModel
    private val adapter = BestSellersAdapter(object : (AudioBook) -> Unit {
        override fun invoke(audioBook: AudioBook) {
            startActivity(activity?.let { AudioBookDetailActivity.newIntent(it, audioBook) })
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.removeItem(R.id.action_search)
    }

    companion object {
        val TAG: String = SearchFragment::class.java.simpleName
        fun newInstance() = SearchFragment()
    }

}
