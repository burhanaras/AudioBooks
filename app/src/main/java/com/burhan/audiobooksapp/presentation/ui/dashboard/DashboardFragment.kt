package com.burhan.audiobooksapp.presentation.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.burhan.audiobooksapp.R

class DashboardFragment : Fragment() {

    companion object {
        val TAG: String = DashboardFragment::class.java.simpleName
        fun newInstance() = DashboardFragment()
    }

    private lateinit var viewModel: DashboardFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dashboard_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DashboardFragmentViewModel::class.java)
        // TODO: Use the ViewModel
        Log.d(TAG, "onActivityCreated()")
    }

}
