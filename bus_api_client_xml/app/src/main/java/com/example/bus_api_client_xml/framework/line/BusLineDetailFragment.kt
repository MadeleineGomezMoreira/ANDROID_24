package com.example.bus_api_client_xml.framework.line

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bus_api_client_xml.databinding.FragmentBusLineDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusLineDetailFragment : Fragment() {

    private var _binding: FragmentBusLineDetailBinding? = null
    private val binding get() = _binding!!


    //    private lateinit var adapter: StopAdapter
    private val viewModel: BusLineDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        //changed View? to make it non-nullable
    ): View {
        _binding = FragmentBusLineDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}