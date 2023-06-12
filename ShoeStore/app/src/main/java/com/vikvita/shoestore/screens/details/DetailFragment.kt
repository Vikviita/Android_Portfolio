package com.vikvita.shoestore.screens.details

import android.os.Bundle

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.vikvita.shoestore.R
import com.vikvita.shoestore.databinding.FragmentDetailScreenBinding
import com.vikvita.shoestore.models.Shoe
import com.vikvita.shoestore.screens.list.ListViewModel

class DetailFragment:Fragment() {
    private lateinit var binding: FragmentDetailScreenBinding
private val viewModel:ListViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
      var check=true
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_detail_screen,container,false)

         val shoe=Shoe("","","","")

        binding.shoes= shoe


        binding.cancel.setOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.save.setOnClickListener {
            viewModel.addNewShoe(shoe.name,shoe.company,shoe.size,shoe.description)
            it.findNavController().popBackStack()
        }


        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.logout).setVisible(false)
    }
}