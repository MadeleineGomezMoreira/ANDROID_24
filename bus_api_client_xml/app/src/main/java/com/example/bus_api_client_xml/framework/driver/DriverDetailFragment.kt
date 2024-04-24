package com.example.bus_api_client_xml.framework.driver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.bus_api_client_xml.databinding.FragmentDriverDetailBinding
import com.example.bus_api_client_xml.domain.model.BusDriver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DriverDetailFragment : Fragment() {

    private var _binding: FragmentDriverDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var driver: BusDriver

    private val viewModel: DriverDetailViewModel by viewModels()
    val args: DriverDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        //changed View? to make it non-nullable
    ): View {
        _binding = FragmentDriverDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val driverId = args.Id.toInt()
            viewModel.handleEvent(DriverDetailContract.DriverDetailEvent.GetDriver(driverId))

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.state.collect { value ->
                        driver = value.busDriver
                        setScreenDriverData()
                    }
                }
            }
        }
    }

    private fun setScreenDriverData() {
        with(binding) {
            firstNameContentTextView.text = driver.firstName
            lastNameContentTextView.text = driver.lastName
            phoneContentTextView.text = driver.phone
            lineIdButton.text = driver.line.id.toString()
        }
    }
}