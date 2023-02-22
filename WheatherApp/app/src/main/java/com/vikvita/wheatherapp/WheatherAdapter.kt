import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vikvita.wheatherapp.R
import com.vikvita.wheatherapp.models.Wheather
import com.vikvita.wheatherapp.databinding.WheatherBinding

class WheatherAdapter:RecyclerView.Adapter<WheatherAdapter.MyViewHolder>(){
   var data: List<Wheather> =emptyList()
       set(value) {
           field=value
           notifyDataSetChanged()
       }


    class MyViewHolder(val binding: WheatherBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val inflater=LayoutInflater.from(parent.context)
        val binding=WheatherBinding.inflate(inflater,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int=data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder.binding){
            time.text=data[position].time
            WhImage.setImageResource(data[position].image ?: R.drawable.icon_snow)
            rainRate.text=data[position].rainRate
            temp.text=data[position].temperature
        }
    }

}