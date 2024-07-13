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
import com.example.authapp.databinding.FragmentRegisterBinding
import com.example.authapp.db.models.User
import com.example.authapp.db.models.UserRequest
import com.example.authapp.utils.NetworkResult
import com.example.authapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val TAG: String = "RegisterFragment"

    private var _binding : FragmentRegisterBinding? = null;
    private val binding get() = _binding!!
    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignUp.setOnClickListener {
            Log.d(TAG, "onViewCreated")
            val username = binding.txtUsername.text.toString()
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()
            val validateResult = Utils.validateCredentials(username, email, password)
            if (!validateResult.first) {
                binding.txtError.text = validateResult.second
            } else {
                authViewModel.registerUser(UserRequest(email, password, username))
            }
        }
        binding.btnLogin.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val users: List<User> = authViewModel.getAllUsers()
//                Log.d(TAG, users.toString())
//            }
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
//            val users: List<User> = authViewModel.getAllUsers()
//            Log.d(TAG, users.toString())
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
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Starting -> {
                    Log.d(TAG, "Starting")
                }
                is NetworkResult.Success -> {
                    // Token needed
                    Log.d(TAG, "Register success for user: " + it.data?.user?.username)
                    it.data?.user?.username?.let { it1 -> authViewModel.loginUser(it1) }
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                    authViewModel.userResponseLiveData.removeObservers(this)
                }

                is NetworkResult.Loading -> {
                    Log.d(TAG, "Loading")
                    binding.progressBar.isVisible = true
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














