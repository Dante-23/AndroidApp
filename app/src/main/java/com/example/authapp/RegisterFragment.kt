package com.example.authapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.authapp.databinding.FragmentRegisterBinding
import com.example.authapp.db.models.User
import com.example.authapp.models.UserRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val TAG: String = "RegisterFragment"

    private var _binding : FragmentRegisterBinding? = null;
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.btnSignUp.setOnClickListener {
            authViewModel.registerUser(UserRequest("test@gmail.com", "123456", "test"))
        }
        binding.btnLogin.setOnClickListener {
//            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

            CoroutineScope(Dispatchers.IO).launch {
                val users: List<User> = authViewModel.getAllUsers()
                Log.d(TAG, users.toString())
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}