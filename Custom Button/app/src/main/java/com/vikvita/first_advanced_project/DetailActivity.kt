package com.vikvita.first_advanced_project

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vikvita.first_advanced_project.databinding.ActivityDetailBinding
import com.vikvita.first_advanced_project.databinding.ContentDetailBinding


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var bindingContent:ContentDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        bindingContent= ContentDetailBinding.bind(findViewById(R.id.detail_layout))


        bindingContent.okButton.setOnClickListener { finish()



        }
        val notficationmanager=getSystemService(NotificationManager::class.java) as NotificationManager
        notficationmanager.cancelAll()


        bindingContent.body.text=intent.getStringExtra(getString(R.string.bodyextra))
        bindingContent.status.text=intent.getStringExtra(getString(R.string.statusextra))
    }

}
