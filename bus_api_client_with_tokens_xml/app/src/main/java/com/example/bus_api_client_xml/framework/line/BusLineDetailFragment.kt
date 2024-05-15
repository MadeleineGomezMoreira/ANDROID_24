package com.example.bus_api_client_xml.framework.line

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bus_api_client_xml.databinding.FragmentBusLineDetailBinding
import com.example.bus_api_client_xml.domain.model.BusLine
import com.example.bus_api_client_xml.utils.Constants
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BusLineDetailFragment : Fragment() {

    private var _binding: FragmentBusLineDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var busLineId: String
    private lateinit var adapter: StopAdapter
    private val viewModel: BusLineDetailViewModel by viewModels()
    private val args: BusLineDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusLineDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            //retrieve the bus line id from the arguments
            busLineId = args.Id
            val id: Int = busLineId.toInt()

            //retrieve the selected bus line and its stops
            viewModel.handleEvent(BusLineDetailContract.BusLineDetailEvent.GetBusLine(id))
            viewModel.handleEvent(BusLineDetailContract.BusLineDetailEvent.GetBusLineStops(id))

            //set the adapter for the stops recycler view
            val recyclerView = recyclerStops
            val context = requireContext()
            val layoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = layoutManager

            adapter = StopAdapter(
                context
            ) { stop ->
                val stopId = stop.id.toString()
                val action =
                    BusLineDetailFragmentDirections.actionBusLineDetailFragmentToStopDetailFragment(
                        Id = stopId,
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
                            setBusLineData(it.busLine)
                            if (it.stops.isNotEmpty()) {
                                //set the stops data
                                adapter.submitList(it.stops)
                                viewModel.handleEvent(BusLineDetailContract.BusLineDetailEvent.StopsDisplayed)
                            }
                        }
                        if (it.error != null) {
                            if (it.error == Constants.FORBIDDEN_STRING) {
                                showErrorMessage(Constants.PERMISSION_DENIED_ERROR)
                            } else {
                                showErrorMessage(it.error)
                            }
                            viewModel.handleEvent(BusLineDetailContract.BusLineDetailEvent.ErrorDisplayed)
                        }
                    }
                }
            }
        }
    }

    private fun setBusLineData(busLine: BusLine) {
        binding.idContentContentTextView.text = busLine.id.toString()
        binding.originContentTextView.text = busLine.lineStart
        binding.destinationContentTextView.text = busLine.lineEnd
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}