package com.vikvita.testexercise.screens.categoryScreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.vikvita.testexercise.R
import com.vikvita.testexercise.data.dto.DishesItem
import com.vikvita.testexercise.databinding.CustomAlertDialogueBinding
import com.vikvita.testexercise.databinding.FragmentCategoryBinding
import com.vikvita.testexercise.databinding.ToolBarSimpleBinding
import com.vikvita.testexercise.databinding.ToolBarWithInfoBinding
import com.vikvita.testexercise.utils.TagsOfDish
import com.vikvita.testexercise.utils.converToBasket
import org.koin.android.ext.android.inject

class CategoryFragment:Fragment() {
    private lateinit var binding:FragmentCategoryBinding
    private lateinit var toolBar: Toolbar
    private lateinit var bindingToolBar: ToolBarSimpleBinding
    private val _viewModel:CategoryFragmentViewModel by inject()
    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentCategoryBinding.inflate(layoutInflater,container,false)

        setChipListener()
        setAdapter()
        setToolBar()

        _viewModel.showToast.observe(viewLifecycleOwner){
            val msg=it.getValue()
            if(msg==null){
                return@observe
            }
            else{
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

            }

        }

        _viewModel.listOfDished.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        return binding.root
    }







private fun setAdapter(){
    adapter=CategoryAdapter {
        showAlertDialogue(it)

    }
    binding.categoryList.adapter=adapter
    binding.categoryList.layoutManager=GridLayoutManager(context,3)

}



    fun setToolBar(){
        toolBar=binding.toolBar
        bindingToolBar=ToolBarSimpleBinding.bind(layoutInflater.inflate(R.layout.tool_bar_simple,null))
        bindingToolBar.root.layoutParams=android.widget.Toolbar.LayoutParams(android.widget.Toolbar.LayoutParams.MATCH_PARENT, android.widget.Toolbar.LayoutParams.WRAP_CONTENT)

        toolBar.addView(bindingToolBar.root)
          bindingToolBar.nameOfCategory.text= arguments?.getString("title")
        bindingToolBar.toolBackButton.setOnClickListener { findNavController().popBackStack() }
    }


    private fun setChipListener(){
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            when(checkedIds[0]){
                R.id.chip_all->{
                    _viewModel.filterChanged(TagsOfDish.DEF)
                }
                R.id.chip_rise->{
                    _viewModel.filterChanged(TagsOfDish.WITH_RICE)
                }
                R.id.chip_fish->{
                    _viewModel.filterChanged(TagsOfDish.WITH_FISH)
                }
                R.id.chip_salad->{
                    _viewModel.filterChanged(TagsOfDish.SALAD)
                }
            }
        }

    }




fun showAlertDialogue(item:DishesItem){
    val alertBinding=CustomAlertDialogueBinding.inflate(LayoutInflater.from(context))

    Glide.with(alertBinding.alertPicture.context)
        .load(item.pictureUrl)
        .fitCenter()
        .placeholder(R.drawable.baseline_cloud_download_24)
        .error(R.drawable.baseline_cloud_download_24)
        .into(alertBinding.alertPicture)
    var msg="${item.price} ${getString(R.string.ruble)}"
    alertBinding.priceField.text=msg
    msg="${item.weight} г"
    alertBinding.weightField.text=msg
    alertBinding.descriptionTextView.text=item.description


    val alert= AlertDialog.Builder(requireContext())
        .setView(alertBinding.root)
        .create()

    alert.show()

    alertBinding.addButton.setOnClickListener {
         _viewModel.saveToBasket(item.converToBasket())
        _viewModel.showToast("Продукт добавлен")
        alert.cancel()
    }
 alertBinding.closeButton.setOnClickListener {
    alert.cancel()
}



}



}