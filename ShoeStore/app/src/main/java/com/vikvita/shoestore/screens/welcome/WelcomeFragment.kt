package com.vikvita.shoestore.screens.welcome

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.vikvita.shoestore.R
import com.vikvita.shoestore.databinding.FragmentWelcomeScreenBinding

class WelcomeFragment:Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentWelcomeScreenBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_welcome_screen,container,false)
        binding.next.setOnClickListener {
            it.findNavController().navigate(R.id.instruction_screen)
        }

        return binding.root
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
          super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.logout).setVisible(false)
      }



}