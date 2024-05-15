package com.example.bus_api_client_xml.framework.driver

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bus_api_client_xml.databinding.FragmentDriverDetailBinding
import com.example.bus_api_client_xml.domain.model.BusDriver
import com.example.bus_api_client_xml.utils.Constants
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DriverDetailFragment : Fragment() {

    private var _binding: FragmentDriverDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var driver: BusDriver
    private lateinit var sharedPreferences: SharedPreferences

    private val viewModel: DriverDetailViewModel by viewModels()
    val args: DriverDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriverDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //this will initialize the shared preferences
        sharedPreferences =
            requireActivity().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        val loginDriverId = args.Id
        if(loginDriverId == Constants.HELLO_STRING){
            val bottomNavDriverId = sharedPreferences.getString(Constants.USER_ID, Constants.EMPTY_STRING)
            viewModel.handleEvent(DriverDetailContract.DriverDetailEvent.GetDriver(bottomNavDriverId!!.toInt()))
        } else{
            viewModel.handleEvent(DriverDetailContract.DriverDetailEvent.GetDriver(loginDriverId.toInt()))
        }

        addLineButtonOnClickListener()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    if (it.loading) {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    if (!it.loading) {
                        binding.progressBar.visibility = View.GONE
                        driver = it.busDriver
                        setScreenDriverData()
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
                        viewModel.handleEvent(DriverDetailContract.DriverDetailEvent.ErrorDisplayed)
                    }
                }
            }
        }
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun addLineButtonOnClickListener() {
        binding.lineIdButton.setOnClickListener {
            val action =
                DriverDetailFragmentDirections.actionDriverDetailFragmentToBusLineDetailFragment(Id = driver.line.id.toString())
            findNavController().navigate(action)
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