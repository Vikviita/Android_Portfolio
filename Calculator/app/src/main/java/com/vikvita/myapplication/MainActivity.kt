package com.vikvita.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.lang.StringBuilder
import com.fathzer.soft.javaluator.DoubleEvaluator
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.navigationBarColor=resources.getColor(R.color.purple_700)
        var exps=StringBuilder("")
        but1.setOnClickListener{
            exp.append("1")
            exps.append("1")
            res.text=""
        }
        but2.setOnClickListener{
            exp.append("2")
            exps.append("2")
            res.text=""
        }
        but3.setOnClickListener{
            exp.append("3")
            exps.append("3")
            res.text=""
        }
        but4.setOnClickListener{
            exp.append("4")
            exps.append("4")
            res.text=""
        }
        but5.setOnClickListener{
            exp.append("5")
            exps.append("5")
            res.text=""
        }
        but6.setOnClickListener{
            exp.append("6")
            exps.append("6")
            res.text=""
        }
        but7.setOnClickListener{
            exp.append("7")
            exps.append("7")
            res.text=""
        }
        but8.setOnClickListener{
            exp.append("8")
            exps.append("8")
            res.text=""
        }
        but9.setOnClickListener{
            exp.append("9")
            exps.append("9")
            res.text=""
        }
        but0.setOnClickListener{
            exp.append("0")
            exps.append("0")
            res.text=""
        }

        butDiv.setOnClickListener{
            if(!exps.isBlank()) {
                if ((exps.get(exps.length - 1) in '0'..'9') or (exps.get(exps.length - 1)==')') ) {
                    exp.append(resources.getString(R.string.div))
                    exps.append("/")
                }
                res.text = ""
            }
        }
        butMul.setOnClickListener{
            if(!exps.isBlank()){
            if((exps.get(exps.length-1) in '0'..'9') or (exps.get(exps.length - 1)==')')){
                exp.append(resources.getString(R.string.mul))
                exps.append("*")}
            res.text=""}
        }


        butMin.setOnClickListener{
            if(!exps.isBlank()) {
                if ((exps.get(exps.length - 1) in '0'..'9') or (exps.get(exps.length - 1)==')') or (exps.get(exps.length - 1)=='(')) {
                    exp.append(resources.getString(R.string.minus))
                    exps.append(resources.getString(R.string.minus))
                }
                res.text = ""
            }
        }
        butPlus.setOnClickListener{
           if(!exps.isBlank()) {
               if ((exps.get(exps.length - 1) in '0'..'9') or (exps.get(exps.length - 1)==')')) {
                   exp.append(resources.getString(R.string.plus))
                   exps.append(resources.getString(R.string.plus))
               }
               res.text = ""
           }
        }
        butBr.setOnClickListener {
            try {


                if ((exps.get(exps.length - 1) in '0'..'9') or (exps.get(exps.length - 1)==')')) {
                    exp.append(")")
                    exps.append(")")
                }
                else if(exps.get(exps.length-1)=='.')
                else {
                    exp.append("(")
                    exps.append("(")
                }
            }catch(e:StringIndexOutOfBoundsException){

                exp.append("(")
                exps.append("(")

            }


        }
        butDot.setOnClickListener{
            if(!exps.isBlank()) {
                if (exps.get(exps.length - 1) in '0'..'9') {
                    exp.append(resources.getString(R.string.dot))
                    exps.append(resources.getString(R.string.dot))
                }
                res.text = ""
            }
        }
        butBack.setOnClickListener{
            if(!exp.text.toString().isBlank()) {
                exp.text = exp.text.toString().substring(0, exp.text.length - 1)
                exps.replace(0,exps.length,exps.substring(0,exps.length-1))
                res.text=""
            }
            else{
                Toast.makeText(this, "Already empty", Toast.LENGTH_SHORT).show()
            }
        }
        butC.setOnClickListener{
            exp.text =""
            res.text=""
            exps.clear()
        }
        butEq.setOnClickListener{
            if(!exps.isBlank()) {
                val result = DoubleEvaluator().evaluate(exps.toString())
                val longRes = result.toLong()
                if(result==longRes.toDouble()){
                    res.text=longRes.toString()
                }
                else{
                    res.text=result.toString()
                }
                exp.text=""
                exps.clear()
            }
            else{
                Toast.makeText(this,"isEmpty",Toast.LENGTH_SHORT).show()
            }

        }





    }




}

