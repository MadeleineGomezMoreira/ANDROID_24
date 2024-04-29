package com.example.bus_api_client_xml.framework.drivers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bus_api_client_xml.common.Constants
import com.example.bus_api_client_xml.databinding.FragmentDriversListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DriversListFragment : Fragment() {

    private var _binding: FragmentDriversListBinding? = null
    private val binding get() = _binding!!


    private lateinit var adapter: DriverAdapter
    private val viewModel: DriversListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        //changed View? to make it non-nullable
    ): View {
        _binding = FragmentDriversListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewModel.handleEvent(DriversListContract.DriversListEvent.GetDrivers)

            val recyclerView = recyclerDrivers
            val context = requireContext()
            val layoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = layoutManager

            adapter = DriverAdapter(context) { driver ->
                val driverId = driver.id.toString()
                val action =
                    DriversListFragmentDirections.actionDriversListFragmentToDriverDetailFragment(
                        Id = driverId
                    )
                root.findNavController().navigate(action)
            }
            recyclerView.adapter = adapter

            //set the state listeners
            setStateListeners()
        }
    }

    private fun setStateListeners() {
        with(binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.state.collect {
                        if (it.loading) {
                            //show loading indicator on screen
                            progressBar.visibility = View.VISIBLE
                        }
                        if (!it.loading) {
                            //hide loading indicator on screen
                            progressBar.visibility = View.GONE
                            if (it.drivers.isNotEmpty()) {
                                //set the stops data
                                adapter.submitList(it.drivers)
                                viewModel.handleEvent(DriversListContract.DriversListEvent.DriversDisplayed)
                            }
                        }
                        if (it.error != null) {
                            if(it.error == Constants.FORBIDDEN_STRING) {
                                showErrorMessage(Constants.PERMISSION_DENIED_ERROR)
                            } else{
                                showErrorMessage(it.error)
                            }
                            viewModel.handleEvent(DriversListContract.DriversListEvent.ErrorDisplayed)
                        }
                    }
                }
            }
        }
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}