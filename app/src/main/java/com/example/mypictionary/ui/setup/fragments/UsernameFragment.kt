package com.example.mypictionary.ui.setup.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypictionary.R
import com.example.mypictionary.databinding.FragmentUsernameBinding
import com.example.mypictionary.util.showSnackbar
import com.example.mypictionary.viewmodels.SetupEvent
import com.example.mypictionary.viewmodels.SetupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsernameFragment : Fragment() {

    private var _binding : FragmentUsernameBinding? = null
    private val binding: FragmentUsernameBinding
    get() = _binding!!

    private val viewModel: SetupViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_username, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUsernameBinding.bind(view)
        listenToEvents()
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.btnNext.setOnClickListener {
            viewModel.validateUsernameAndNavigateToSelectRoom(
                binding.etUsername.text.toString()
            )
        }
    }

    private fun listenToEvents(){
        lifecycleScope.launchWhenStarted {
            viewModel.setupEvent.collect { event->
                when(event) {
                    is SetupEvent.NavigateToSelectRoomEvent -> {
                        findNavController().navigate(
                            UsernameFragmentDirections.actionUsernameFragmentToSelectRoomFragment(event.username)
                        )
                    }

                    is SetupEvent.InputEmptyError -> {
                      showSnackbar(R.string.error_field_empty)
                    }

                    is SetupEvent.InputTooShortError -> {
                        showSnackbar(R.string.error_username_too_short)
                    }

                    is SetupEvent.InputTooLongError -> {
                        showSnackbar(R.string.error_room_name_too_long)
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}