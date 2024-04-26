package com.example.bus_api_client_xml.framework.login

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
import com.example.bus_api_client_xml.common.Constants
import com.example.bus_api_client_xml.data.model.RegisterDTO
import com.example.bus_api_client_xml.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()
    private var username = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        //changed View? to make it non-nullable
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //button listeners
        setButtonOnClickListeners()

        //state listeners
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    if (it.loading) {
                        //show loading indicator on screen
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    if(!it.loading){
                        //hide loading indicator on screen
                        binding.progressBar.visibility = View.GONE
                        if (it.userId != null) {
                            val id = it.userId
                            val action =
                                LoginFragmentDirections.actionLoginFragmentToDriverDetailFragment(
                                    Id = id.toString()
                                )
                            findNavController().navigate(action)
                        }
                    }
                    if (it.correctAction) {
                        viewModel.handleEvent(LoginContract.LoginEvent.RetrieveUserId(username))
                    }
                }
            }
        }
    }

    private fun setButtonOnClickListeners() {
        with(binding) {
            loginButton.setOnClickListener {
                username = textFieldContentUserNameLogin.text.toString()
                val user = textFieldContentUserNameLogin.text.toString()
                val pass = textFieldContentPasswordLogin.text.toString()
                if (user.isNotEmpty() && pass.isNotEmpty()) {
                    viewModel.handleEvent(
                        LoginContract.LoginEvent.LoginUser(
                            user,
                            pass,
                        )
                    )
                } else {
                    Snackbar.make(
                        requireView(),
                        Constants.LOGIN_FIELDS_EMPTY_ERROR,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            registerButton.setOnClickListener {
                username = textFieldContentUserNameRegister.text.toString()
                val user = textFieldContentUserNameRegister.text.toString()
                val pass = textFieldContentPasswordRegister.text.toString()
                val fullName = textFieldContentFullName.text.toString()
                val firstName = fullName.split(Constants.BLANK_SPACE)[0]
                val lastName = fullName.split(Constants.BLANK_SPACE)[1]
                val email = textFieldContentEmail.text.toString()
                val phone = textFieldContentPhone.text.toString()
                if (user.isNotEmpty() && pass.isNotEmpty() && fullName.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && fullName.contains(
                        Constants.BLANK_SPACE
                    )
                ) {
                    viewModel.handleEvent(
                        LoginContract.LoginEvent.RegisterUser(
                            RegisterDTO(
                                firstName,
                                lastName,
                                phone,
                                user,
                                pass,
                                email,
                            )
                        )
                    )
                } else {
                    Snackbar.make(
                        requireView(),
                        Constants.REGISTER_FIELDS_EMPTY_ERROR,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}