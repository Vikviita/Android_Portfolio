package com.vikvita.test.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.arch.core.executor.DefaultTaskExecutor
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.vikvita.test.R
import com.vikvita.test.databinding.FragmentDetailBinding
import com.vikvita.test.models.UserDetail

class DetailFragment:Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel:UserDetailsViewModel by viewModels{factory()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadUser(requireArguments().getInt(ARG_USER_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDetailBinding.inflate(inflater,container,false)

        viewModel.usersDetails.observe(viewLifecycleOwner){
            binding.userNameTextView.text=it.user.name
            if(it.user.photo.isNotBlank()){
                Glide.with(this)
                    .load(it.user.photo)
                    .circleCrop()
                    .into(binding.photoImageView)
            }
            else{
                Glide.with(this)
                    .load(R.drawable.baseline_account_circle_24)
                    .into(binding.photoImageView)
            }
            binding.userDetailTextView.text=it.detail
        }
            binding.deleteButton.setOnClickListener{
                viewModel.deleteUser()
                navigator().toast(R.string.has_been_deleted)
               navigator().goBack()
            }
return binding.root
    }

    companion object{
private const val ARG_USER_ID="ARG_USER_ID"
        fun newInstance(userId: Int): DetailFragment {

            val fragment = DetailFragment()
            fragment.arguments = bundleOf(ARG_USER_ID to userId)
            return fragment
        }
        
    }
}