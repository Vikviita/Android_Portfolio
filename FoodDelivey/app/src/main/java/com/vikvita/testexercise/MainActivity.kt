package com.vikvita.testexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vikvita.testexercise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater).also{
            setContentView(it.root)
        }


        setBottomListener()
    }




    private fun setBottomListener(){
        binding.bottomNavView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_main->{
                    findNavController(R.id.nav_host_fragment).popBackStack(R.id.fragmentMain,false)
                }
                R.id.menu_bag->{
                    findNavController(R.id.nav_host_fragment).navigate(R.id.basketFragment)
                }

            }
            true
        }

    }
}