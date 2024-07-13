package com.example.authapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.authapp.databinding.FragmentLoginBinding
import com.example.authapp.databinding.FragmentRegisterBinding
import com.example.authapp.db.models.UserRequest
import com.example.authapp.utils.NetworkResult

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null;
    private val binding get() = _binding!!
    private val TAG = "LoginFragment"
    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignUp.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnLogin.setOnClickListener {
            val usernme = binding.txtEmail.text.toString()
            val pssword = binding.txtPassword.text.toString()
            authViewModel.loginUser(UserRequest("", pssword, usernme))
        }
        bindObservers()
        if (authViewModel.isUserLoggedIn()) {
            Log.d(TAG, "User is logged in")
        } else {
            Log.d(TAG, "User is not logged in")
            authViewModel.initRegisterState()
        }
    }

    private fun bindObservers() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Starting -> {
                    Log.d(TAG, "Starting")
                }
                is NetworkResult.Success -> {
                    // Token needed
                    Log.d(TAG, "Login success for user: " + it.data?.user?.username)
                    it.data?.user?.username?.let { it1 -> authViewModel.loginUser(it1) }
                    findNavController().navigate(R.id.action_loginFragment_to_todoFragment)
                    authViewModel.userResponseLiveData.removeObservers(this)
                }

                is NetworkResult.Loading -> {
                    Log.d(TAG, "Loading")
                }

                is NetworkResult.Error -> {
                    Log.d(TAG, "Error")
                    binding.txtError.text = it.message
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}