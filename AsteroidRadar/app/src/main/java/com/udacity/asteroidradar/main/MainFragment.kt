package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.RecyclerAdapter
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this,Factory(activity.application)).get(MainViewModel::class.java)
    }
   private lateinit var binding:FragmentMainBinding

   var adapter:RecyclerAdapter?=null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.asteroidList.observe(viewLifecycleOwner){
            it?.let{
                adapter?.submitList(it)
                if(it.size!=0)binding.statusLoadingWheel.visibility=View.GONE
            }
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        adapter=RecyclerAdapter{
            viewModel.asteroidClick(it)
        }

        binding.asteroidRecycler.adapter=adapter
        binding.asteroidRecycler.layoutManager=LinearLayoutManager(context)




        viewModel.showDetail.observe(viewLifecycleOwner) {
            it?.let {
                this.findNavController().navigate(
                    MainFragmentDirections
                        .actionShowDetail(it)
                )
                viewModel.asteroidClicked()
            }
        }

        viewModel.pictureOfDay.observe(viewLifecycleOwner){
            it?.let{
            Picasso.Builder(context).build().load(it.url).into(binding.activityMainImageOfTheDay)
            binding.activityMainImageOfTheDay.contentDescription=getString(R.string.nasa_picture_of_day_content_description_format)}
        }

        viewModel.showToast.observe(viewLifecycleOwner){
            if(it){
                Toast.makeText(context, "Connection Error", Toast.LENGTH_SHORT).show()
                binding.activityMainImageOfTheDay.contentDescription=getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
                viewModel.toastShowed()
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val weekList= getNextSevenDaysFormattedDates()
        val weekListAsteroid= ArrayList<Asteroid>()
        for(i in weekList){
            for(j in  viewModel.asteroidList.value!!){
                if(j.closeApproachDate.compareTo(i)==0){
                    weekListAsteroid.add(j)
                }
            }
        }
        when(item.itemId){

            R.id.show_today_menu->{
                adapter?.submitList(weekListAsteroid.filter { it.closeApproachDate.compareTo(weekList[0])==0 })


            }
            R.id.show_saved_menu->{
                adapter?.submitList(viewModel.asteroidList.value)

            }
            R.id.show_week_menu->{
               adapter?.submitList(weekListAsteroid)

            }
        }

        return true
    }
}
