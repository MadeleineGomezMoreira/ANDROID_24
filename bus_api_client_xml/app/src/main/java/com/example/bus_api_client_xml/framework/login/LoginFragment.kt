package com.example.bus_api_client_xml.framework.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bus_api_client_xml.common.Constants
import com.example.bus_api_client_xml.data.model.RegisterDTO
import com.example.bus_api_client_xml.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()


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
        with(binding) {
//            val context = requireContext()
            loginButton.setOnClickListener {
                val user = textFieldUserNameLogin.toString()
                val pass = textFieldPasswordLogin.toString()
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
                val user = textFieldUserNameRegister.toString()
                val pass = textFieldPasswordRegister.toString()
                val fullName = textFieldFullName.toString()
                val firstName = fullName.split(Constants.BLANK_SPACE)[0]
                val lastName = fullName.split(Constants.BLANK_SPACE)[1]
                val email = textFieldEmail.toString()
                val phone = textFieldPhone.toString()
                if (user.isNotEmpty() && pass.isNotEmpty() && fullName.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && fullName.contains(Constants.BLANK_SPACE)) {
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
//                    NAVIGATE TO DETAIL DRIVER FRAGMENT (id = 1) this does not work
                    val id = 1
                    val action = LoginFragmentDirections.actionLoginFragmentToDriverDetailFragment(Id = id.toString())
                    findNavController().navigate(action)
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