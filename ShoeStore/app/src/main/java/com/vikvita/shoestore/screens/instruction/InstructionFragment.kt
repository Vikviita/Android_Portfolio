package com.vikvita.shoestore.screens.instruction

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.vikvita.shoestore.R
import com.vikvita.shoestore.databinding.FragmentInstructionScreenBinding


class InstructionFragment:Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentInstructionScreenBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_instruction_screen, container, false)

        binding.next.setOnClickListener {
            it.findNavController().navigate(R.id.list_screen)
        }

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.logout).setVisible(false)
    }


}