package com.burhan.audiobooksapp.presentation.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.burhan.audiobooksapp.R

class SettingsFragment : Fragment() {

    companion object {
        val TAG: String = SettingsFragment::class.java.simpleName
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsFragmentViewModel::class.java)
        // TODO: Use the ViewModel
        Log.d(TAG, "onActivityCreated()")
    }

}
