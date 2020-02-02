package com.listenhub.audiobooksapp.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.listenhub.audiobooksapp.R
import com.listenhub.audiobooksapp.domain.model.AudioBook
import com.listenhub.audiobooksapp.presentation.ui.audiobookdetail.AudioBookDetailActivity
import com.listenhub.audiobooksapp.presentation.ui.home.adapter.HomeCategoriesAdapter
import com.listenhub.audiobooksapp.presentation.ui.samples.SamplesFragment
import com.listenhub.audiobooksapp.presentation.ui.samples.SamplesFragmentViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: SamplesFragmentViewModel
    private var onFragmentInterActionListener: HomeFragmentInteractionListener? = null
    private val adapter = HomeCategoriesAdapter(object : (AudioBook) -> Unit {
        override fun invoke(audioBook: AudioBook) {
            startActivity(activity?.let { AudioBookDetailActivity.newIntent(it, audioBook) })
        }
    },
        object : (String) -> Unit {
            override fun invoke(categoryId: String) {
                onFragmentInterActionListener?.onCategorySelected(categoryId)
            }
        })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeFragmentInteractionListener) {
            onFragmentInterActionListener = context
        }
    }

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
        ViewModelProviders.of(activity!!).get(SamplesFragmentViewModel::class.java)

    private fun setRecyclerView() {
        rvHomeCategories.setHasFixedSize(true)
        rvHomeCategories.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager?
        rvHomeCategories.adapter = adapter
    }

    private fun setObservers() {
        viewModel.loadingProgressVisibility.observe(this, Observer {
            it?.let { isVisible ->
                progressHome.visibility = if (isVisible) View.VISIBLE else View.GONE
            }
        })
        viewModel.data.observe(this, Observer { data ->
            adapter.setData(data)
        })
    }

    interface HomeFragmentInteractionListener {
        fun onCategorySelected(categoryId: String)
    }

    companion object {
        val TAG: String = SamplesFragment::class.java.simpleName
        fun newInstance() = SamplesFragment()
    }

}
