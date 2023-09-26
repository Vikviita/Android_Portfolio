package com.vikvita.testexercise.screens.BasketScreen

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.recyclerview.widget.LinearLayoutManager
import com.vikvita.testexercise.R
import com.vikvita.testexercise.data.dto.BasketItem
import com.vikvita.testexercise.databinding.FragmentBagBinding
import com.vikvita.testexercise.databinding.FragmentMainBinding
import com.vikvita.testexercise.databinding.ToolBarWithInfoBinding
import org.koin.android.ext.android.inject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BasketFragment: Fragment() {
    private lateinit var binding: FragmentBagBinding
    private lateinit var toolBar: Toolbar
    private lateinit var bindingToolBar: ToolBarWithInfoBinding
    private val _viewModel:BasketFragmentViewModel by inject()
    private lateinit var adapter:BasketAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentBagBinding.inflate(inflater,container,false)
        setToolBar()
        setAdapter()

        _viewModel.listOfBasketItem.observe(viewLifecycleOwner){
            adapter.submitList(it)

        }
        _viewModel.price.observe(viewLifecycleOwner){
            val string="Оплатить $it ${getString(R.string.ruble)}"
            binding.basketBuy.text=string
        }

        binding.basketBuy.setOnClickListener {
            Toast.makeText(context, "Оплачено", Toast.LENGTH_SHORT).show()
            _viewModel.clear()
        }

        return binding.root
    }




    private fun setAdapter(){
        val layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
         adapter=BasketAdapter(object :AddAndDelete{
            override fun add(newItem:BasketItem) {
                _viewModel.addItem(newItem)
            }

            override fun delete(id:Int) {
                _viewModel.deleteItem(id)
            }

        })

        binding.basketRecyclerView.adapter=adapter
        binding.basketRecyclerView.layoutManager=layoutManager

    }





    @RequiresApi(Build.VERSION_CODES.O)
    fun setToolBar(){
        toolBar=binding.toolBar
        bindingToolBar=
            ToolBarWithInfoBinding.bind(layoutInflater.inflate(R.layout.tool_bar_with_info,null))
        bindingToolBar.root.layoutParams=android.widget.Toolbar.LayoutParams(android.widget.Toolbar.LayoutParams.MATCH_PARENT, android.widget.Toolbar.LayoutParams.WRAP_CONTENT)
        toolBar.addView(bindingToolBar.root)
        val formatter = DateTimeFormatter.ofPattern("dd MMMM,yyyy")
        val current = LocalDateTime.now().format(formatter)
        bindingToolBar.timeToolbar.text=current
    }


    override fun onDestroy() {
        super.onDestroy()
        _viewModel.saveBusket()
    }
}