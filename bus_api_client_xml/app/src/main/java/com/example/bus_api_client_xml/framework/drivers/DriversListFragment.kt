package com.example.bus_api_client_xml.framework.drivers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bus_api_client_xml.databinding.FragmentDriversListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DriversListFragment : Fragment(){

    private var _binding: FragmentDriversListBinding? = null
    private val binding get() = _binding!!


//    private lateinit var adapter: DriverAdapter
    private val viewModel: DriversListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        //changed View? to make it non-nullable
    ): View {
        _binding = FragmentDriversListBinding.inflate(inflater, container, false)
        return binding.root
    }
}