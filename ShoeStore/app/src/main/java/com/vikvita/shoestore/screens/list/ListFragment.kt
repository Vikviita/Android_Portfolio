package com.vikvita.shoestore.screens.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.vikvita.shoestore.R
import com.vikvita.shoestore.databinding.FragmentListScreenBinding
import com.vikvita.shoestore.databinding.ShoeViewBinding
import com.vikvita.shoestore.models.Shoe


class ListFragment:Fragment() {
  private lateinit var  binding: FragmentListScreenBinding
    private lateinit var shoeView:ShoeViewBinding
    val viewModel: ListViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{

        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_list_screen, container, false)





        binding.addNew.setOnClickListener {
            it.findNavController().navigate(R.id.detail_screen)

        }

           viewModel.shoeList.observe(viewLifecycleOwner){
              it.forEach {
                  shoeView = DataBindingUtil.inflate(inflater, R.layout.shoe_view, container, false)
                  shoeView.name.text = it.name
                  shoeView.companyName.text = it.company
                  shoeView.size.text = it.size
                  shoeView.description.text = it.description
                  binding.listShoes.addView(shoeView.root)
              }
           }
        return binding.root
    }


    }






