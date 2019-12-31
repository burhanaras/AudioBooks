package com.burhan.audiobooksapp.presentation.ui.bestsellers

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
import com.burhan.audiobooksapp.presentation.ui.bestsellers.adapter.BestSellersAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.*

class BestSellersFragment : Fragment() {

    private lateinit var viewModel: BestSellersFragmentViewModel
    private val adapter = BestSellersAdapter(object : (AudioBook) -> Unit {
        override fun invoke(audioBook: AudioBook) {
            startActivity(activity?.let { AudioBookDetailActivity.newIntent(it, audioBook) })
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BestSellersFragmentViewModel::class.java)
        setRecyclerView()
        setObservers()
        viewModel.loadData()
    }

    private fun setRecyclerView() {
        rvDashboardCategories.setHasFixedSize(true)
        rvDashboardCategories.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvDashboardCategories.adapter = adapter
    }

    private fun setObservers() {
        viewModel.data.observe(this, Observer {
            it?.let { data -> adapter.setData(data) }
        })
    }


    companion object {
        val TAG: String = BestSellersFragment::class.java.simpleName
        fun newInstance() = BestSellersFragment()
    }

}
