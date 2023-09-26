package com.vikvita.testexercise.screens.mainScreen


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikvita.testexercise.R
import com.vikvita.testexercise.databinding.FragmentMainBinding
import com.vikvita.testexercise.databinding.ToolBarWithInfoBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainFragment:Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var toolBar: Toolbar
    private lateinit var categoryList:List<MainCategoryItem>
    private lateinit var bindingToolBar:ToolBarWithInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentMainBinding.inflate(layoutInflater,container,false)

       setToolBar()



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      categoryList = listOf(
            MainCategoryItem(
                pictureId = R.drawable.bread_pic,
                text = "Пекарни \nи кондитерские",
                color =  resources.getColor(R.color.bread_color)
            ),
            MainCategoryItem(
                pictureId = R.drawable.fast_food_pic,
                text = "Фастфуд",
                color =resources.getColor(R.color.fast_food_color)
            ),
            MainCategoryItem(
                pictureId = R.drawable.asia_cuisine,
                text = "Азиатская кухня",
                color = resources.getColor(R.color.asia_cuisine_color)
            ),
            MainCategoryItem(
                pictureId = R.drawable.soup,
                text = "Супы",
                color = resources.getColor(R.color.soup_color)
            )
      )

         setAdapter()





    }


@RequiresApi(Build.VERSION_CODES.O)
fun setToolBar(){
    toolBar=binding.toolBar
    bindingToolBar=ToolBarWithInfoBinding.bind(layoutInflater.inflate(R.layout.tool_bar_with_info,null))
    bindingToolBar.root.layoutParams=android.widget.Toolbar.LayoutParams(android.widget.Toolbar.LayoutParams.MATCH_PARENT, android.widget.Toolbar.LayoutParams.WRAP_CONTENT)
    toolBar.addView(bindingToolBar.root)
    val formatter = DateTimeFormatter.ofPattern("dd MMMM,yyyy")
    val current = LocalDateTime.now().format(formatter)
    bindingToolBar.timeToolbar.text=current

}



    private fun setAdapter(){
        val layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        val adapter=MainAdapter(categoryList){
            findNavController().navigate(MainFragmentDirections.actionFragmentMainToCategoryFragment(it))
        }
        binding.mainCategoryRecycler.layoutManager=layoutManager
        binding.mainCategoryRecycler.adapter=adapter
    }








}