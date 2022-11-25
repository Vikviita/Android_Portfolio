package com.vikvita.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.TextView
import android.widget.Toast
import com.vikvita.compass.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),SensorEventListener {
    private lateinit var binding:ActivityMainBinding
    private var sensor:SensorManager?=null
    private var current_degree=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.navigationBarColor=resources.getColor(R.color.back)
        sensor=getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
sensor?.registerListener(this,sensor?.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME)

    }

    override fun onPause() {
        super.onPause()
        sensor?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
      val degree:Int=event?.values?.get(0)?.toInt()!!


        when(degree){
            in sides.N.period()->{
                binding.degree.setText(Html.fromHtml("<font color=${resources.getColor(R.color.letter)}>"+sides.N.title+"<font/>"+"$degree°"),TextView.BufferType.SPANNABLE)
            }
            in 0..22  ->{
                binding.degree.setText(Html.fromHtml("<font color=${resources.getColor(R.color.letter)}>"+sides.N.title+"<font/>"+"$degree°"),TextView.BufferType.SPANNABLE)

            }

            in sides.W.period()->{
                binding.degree.setText(Html.fromHtml("<font color=${resources.getColor(R.color.letter)}>"+sides.W.title+"<font/>"+"$degree°"),TextView.BufferType.SPANNABLE)
            }
            in sides.E.period()->{
                binding.degree.setText(Html.fromHtml("<font color=${resources.getColor(R.color.letter)}>"+sides.E.title+"<font/>"+"$degree°"),TextView.BufferType.SPANNABLE)
            }
            in sides.S.period()->{
                binding.degree.setText(Html.fromHtml("<font color=${resources.getColor(R.color.letter)}>"+sides.S.title+"<font/>"+"$degree°"),TextView.BufferType.SPANNABLE)
            }
            in sides.NW.period()->{
                binding.degree.setText(Html.fromHtml("<font color=${resources.getColor(R.color.letter)}>"+sides.NW.title+"<font/>"+"$degree°"),TextView.BufferType.SPANNABLE)
            }
            in sides.NE.period()->{
                binding.degree.setText(Html.fromHtml("<font color=${resources.getColor(R.color.letter)}>"+sides.NE.title+"<font/>"+"$degree°"),TextView.BufferType.SPANNABLE)
            }
            in sides.SW.period()->{
                binding.degree.setText(Html.fromHtml("<font color=${resources.getColor(R.color.letter)}>"+sides.SW.title+"<font/>"+"$degree°"),TextView.BufferType.SPANNABLE)
            }
            in sides.SE.period()->{
                binding.degree.setText(Html.fromHtml("<font color=${resources.getColor(R.color.letter)}>"+sides.SE.title+"<font/>"+"$degree°"),TextView.BufferType.SPANNABLE)


            }
        }

        val rotate=RotateAnimation(current_degree.toFloat(),(-degree).toFloat(),Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)

        rotate.fillAfter=true
        current_degree=-degree
binding.compass.startAnimation(rotate)


    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}