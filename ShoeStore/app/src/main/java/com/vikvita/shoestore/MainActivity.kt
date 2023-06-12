package com.vikvita.shoestore

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.vikvita.shoestore.databinding.ActivityMainBinding
import com.vikvita.shoestore.screens.login.LoginFragment


class MainActivity : AppCompatActivity() {
private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
     supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.menu,menu)
        return true
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
fu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
                 android.R.id.home->{
                     this.findNavController(R.id.myNavHostFragment).popBackStack()
                 }
            R.id.logout->{
              Navigation.findNavController(this,R.id.myNavHostFragment).popBackStack(R.id.loginFragment,false)
            }
        }

        return true
    }
}