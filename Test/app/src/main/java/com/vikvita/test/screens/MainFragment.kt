package com.vikvita.test.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikvita.test.UserActionListener
import com.vikvita.test.UsersAdapter
import com.vikvita.test.databinding.FragmentUsersBinding
import com.vikvita.test.models.User

class MainFragment:Fragment() {


    private lateinit var binding: FragmentUsersBinding
    private lateinit var adapter: UsersAdapter
private val viewModel:UserListViewModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUsersBinding.inflate(inflater,container,false)
        adapter= UsersAdapter(object:UserActionListener{
            override fun onUserMove(user: User, moveBy: Int) {
                viewModel.moveUser(user,moveBy)
            }

            override fun onUserDelete(user: User) {
                viewModel.deleteUser(user)
            }

            override fun onUserDetail(user: User) {
                navigator().showDetail(user)
            }


        })
        val layoutManager=LinearLayoutManager(requireContext())
        binding.list.layoutManager=layoutManager
        binding.list.adapter=adapter

        viewModel.users.observe(viewLifecycleOwner){
            adapter.users=it   }



        return binding.root
    }
}