package com.example.bus_api_client_xml.framework.stop

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
import com.example.bus_api_client_xml.databinding.FragmentStopDetailBinding
import com.example.bus_api_client_xml.domain.model.BusStop
import com.example.bus_api_client_xml.utils.Constants
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StopDetailFragment : Fragment() {

    private var _binding: FragmentStopDetailBinding? = null
    private val binding get() = _binding!!


    private lateinit var adapter: LineAdapter
    private lateinit var stopId: String
    private val viewModel: StopDetailViewModel by viewModels()
    private val args: StopDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStopDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            //retrieve the bus stop id from the arguments
            stopId = args.Id
            val id: Int = stopId.toInt()

            //retrieve the selected bus stop and its lines
            viewModel.handleEvent(StopDetailContract.StopDetailEvent.GetStop(id))
            viewModel.handleEvent(StopDetailContract.StopDetailEvent.GetStopLines(id))

            //set the adapter for the lines recycler view
            val recyclerView = recyclerLines
            val context = requireContext()
            val layoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = layoutManager

            adapter = LineAdapter(
                context
            ) { line ->
                val lineId = line.id.toString()
                val action =
                    StopDetailFragmentDirections.actionStopDetailFragmentToLineDetailFragment(
                        Id = lineId,
                    )
                binding.root.findNavController().navigate(action)
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
                            setBusStopData(it.busStop)
                            if (it.lines.isNotEmpty()) {
                                //set the stops data
                                adapter.submitList(it.lines)
                                viewModel.handleEvent(StopDetailContract.StopDetailEvent.LinesDisplayed)
                            }
                        }
                        if (it.error != null) {
                            if (it.error == Constants.FORBIDDEN_STRING) {
                                Snackbar.make(
                                    requireView(),
                                    Constants.PERMISSION_DENIED_ERROR,
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            } else {
                                showErrorMessage(it.error)
                            }
                            viewModel.handleEvent(StopDetailContract.StopDetailEvent.ErrorDisplayed)
                        }
                    }
                }
            }
        }
    }

    private fun setBusStopData(busStop: BusStop) {
        binding.stopIdContentTextView.text = busStop.id.toString()
        binding.stopNameContentTextView.text = busStop.name
        binding.coordinatesContentTextView.text = busStop.location.toString()
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}