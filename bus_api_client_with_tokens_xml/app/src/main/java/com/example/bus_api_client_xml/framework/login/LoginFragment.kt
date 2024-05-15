package com.example.bus_api_client_xml.framework.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bus_api_client_xml.data.model.RegisterDTO
import com.example.bus_api_client_xml.databinding.FragmentLoginBinding
import com.example.bus_api_client_xml.utils.Constants
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
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //button listeners
        setButtonOnClickListeners()

        //state listeners
        setStateListeners()
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
                            if (it.userId != null) {
                                val id = it.userId
                                handleUserIdState(id.toString())
                            }
                        }
                        if (it.registerCorrect) {
                            correctRegistration()
                        }
                        if (it.error != null) {
                            handleErrorState(it.error)
                        }
                    }
                }
            }
        }
    }

    private fun handleUserIdState(userId: String?) {
        userId?.let {
            //save the userId in the shared preferences
            val sharedPreferences =
                requireActivity().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(Constants.USER_ID, userId)
            editor.apply()
            //then navigate
            val action =
                LoginFragmentDirections.actionLoginFragmentToDriverDetailFragment(Id = userId)
            findNavController().navigate(action)
        }
    }

    private fun correctRegistration() {
        clearInputFields()
        showErrorMessage(Constants.ACTIVATION_REQUIRED_ERROR)
    }

    private fun handleErrorState(error: String?) {
        error?.let {
            val errorMessage = when (error) {
                Constants.UNAUTHORIZED_STRING -> Constants.WRONG_LOGIN_INFO_ERROR
                Constants.CUSTOM_STRING -> Constants.ACCOUNT_NOT_ACTIVATED_ERROR
                Constants.CONFLICT_STRING -> Constants.USERNAME_OR_EMAIL_ALREADY_EXISTS_ERROR
                else -> error
            }
            showErrorMessage(errorMessage)
            viewModel.handleEvent(LoginContract.LoginEvent.ErrorDisplayed)
        }
    }


    private fun clearInputFields() {
        with(binding) {
            textFieldContentUserNameRegister.text?.clear()
            textFieldContentPasswordRegister.text?.clear()
            textFieldContentFullName.text?.clear()
            textFieldContentEmail.text?.clear()
            textFieldContentPhone.text?.clear()
        }
    }

    private fun showErrorMessage(message: String) {
        //we will hide the keyboard before displaying the error message
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
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
                    showErrorMessage(Constants.LOGIN_FIELDS_EMPTY_ERROR)
                }
            }

            registerButton.setOnClickListener {
                username = textFieldContentUserNameRegister.text.toString()
                val user = textFieldContentUserNameRegister.text.toString()
                val pass = textFieldContentPasswordRegister.text.toString()
                val fullName = textFieldContentFullName.text.toString()
                val email = textFieldContentEmail.text.toString()
                val phone = textFieldContentPhone.text.toString()
                if (user.isNotEmpty() && pass.isNotEmpty() && fullName.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && fullName.isNotEmpty() && fullName.contains(
                        Constants.BLANK_SPACE
                    )
                ) {
                    val firstName = fullName.split(Constants.BLANK_SPACE)[0]
                    val lastName = fullName.split(Constants.BLANK_SPACE)[1]
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
                    showErrorMessage(Constants.REGISTER_FIELDS_EMPTY_ERROR)
                }
            }
        }
    }
}