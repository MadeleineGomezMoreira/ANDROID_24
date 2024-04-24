package com.example.bus_api_client_xml.framework.stop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bus_api_client_xml.databinding.FragmentStopDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StopDetailFragment : Fragment() {

    private var _binding: FragmentStopDetailBinding? = null
    private val binding get() = _binding!!


    //    private lateinit var adapter: LineAdapter
    private val viewModel: StopDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        //changed View? to make it non-nullable
    ): View {
        _binding = FragmentStopDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}