package com.vikvita.shoestore.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.vikvita.shoestore.R
import com.vikvita.shoestore.databinding.ActivityMainBinding
import com.vikvita.shoestore.databinding.FragmentLoginScreenBinding

class LoginFragment:Fragment() {
    private lateinit var binding: FragmentLoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_login_screen,container,false)

        binding.signIn.setOnClickListener {
            it.findNavController().navigate(R.id.welcome_screen)
        }
        binding.signUp.setOnClickListener {
            it.findNavController().navigate(R.id.welcome_screen)
        }

        return binding.root
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.logout).setVisible(false)
    }


}