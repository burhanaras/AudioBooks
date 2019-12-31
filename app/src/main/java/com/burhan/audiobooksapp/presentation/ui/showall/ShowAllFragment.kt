package com.burhan.audiobooksapp.presentation.ui.showall

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
import com.burhan.audiobooksapp.domain.model.Category
import com.burhan.audiobooksapp.presentation.ui.audiobookdetail.AudioBookDetailActivity
import com.burhan.audiobooksapp.presentation.ui.bestsellers.adapter.BestSellersAdapter
import kotlinx.android.synthetic.main.fragment_show_all.*

class ShowAllFragment : Fragment() {

    private lateinit var viewModel: ShowAllViewModel
    private val adapter = BestSellersAdapter(object : (AudioBook) -> Unit {
        override fun invoke(audioBook: AudioBook) {
            startActivity(activity?.let { AudioBookDetailActivity.newIntent(it, audioBook) })
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_all, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ShowAllViewModel::class.java)

        initUI()
        setObservers()

        arguments?.let {
            it.getString(ARG_CATEGORY_ID)?.let { categoryId ->
                viewModel.loadData(Category(categoryId, "", "", listOf()))
            }
        }
    }

    private fun initUI() {
        rvShowAll.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvShowAll.setHasFixedSize(true)
        rvShowAll.adapter = adapter
    }

    private fun setObservers() {
        viewModel.audioBooks.observe(this, Observer {
            it?.let { audioBooks ->
                adapter.setData(audioBooks)
            }
        })
    }

    companion object {
        const val TAG: String = "ShowAllFragment"

        private const val ARG_CATEGORY_ID: String = "ARG_CATEGORY_ID"
        fun newInstance(categoryId: String?) = ShowAllFragment().apply {
            val bundle = Bundle().apply {
                categoryId?.let { putString(ARG_CATEGORY_ID, it) }
            }
            arguments = bundle
        }
    }

}
