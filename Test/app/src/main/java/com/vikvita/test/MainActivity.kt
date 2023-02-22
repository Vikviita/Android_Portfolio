package com.vikvita.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.vikvita.test.databinding.ActivityMainBinding
import com.vikvita.test.models.User
import com.vikvita.test.models.UserListener
import com.vikvita.test.models.UserService
import com.vikvita.test.screens.DetailFragment
import com.vikvita.test.screens.MainFragment

class MainActivity : AppCompatActivity() ,Navigator{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer,MainFragment())
                .commit()
        }

    }

    override fun showDetail(user: User) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer,DetailFragment.newInstance(user.id))
            .commit()
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun toast(messageRes: Int) {
        Toast.makeText(this,messageRes,Toast.LENGTH_SHORT)
    }
}