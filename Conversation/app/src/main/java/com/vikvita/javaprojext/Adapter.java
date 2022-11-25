package com.vikvita.javaprojext;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Class Adapter for List View
 *
 * this  class adaptates  one common pattern for each element
 * In current situation we have class Phrases which has one field(phrase)
 * this adapter take already existing pattern(pattern. xml )
 * and change text of TextView to the Phrases.getPhrase()
 * and do this action for each element which is in the Array List
 *
 * */
public class Adapter extends BaseAdapter {

    /**     Declaration of variables              */
    private ArrayList<Phrases> array;
    private Context ctx;
    private LayoutInflater inflater;
    private TextToSpeech textToSpeech;

    /** Constructor*/
    public Adapter(Context ctx,ArrayList<Phrases> array){
        this.array=array;
        this.ctx=ctx;
        inflater=(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        textToSpeech=new TextToSpeech(ctx,(i)->{

            if(i!=TextToSpeech.ERROR){
                textToSpeech.setLanguage(Locale.US);

            }
        });
    }

    /** return number of elements*/
    @Override
    public int getCount() {
        return array.size();
    }
/**   return object which is on the particular position   */
    @Override
    public Object getItem(int position) {
        return array.get(position);
    }
    /** return particular position */
    @Override
    public long getItemId(int position) {
        return position;
    }
/**  Drawing an of each elements */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view=convertView;
       if(view==null){
           view=inflater.inflate(R.layout.pattern,parent,false);}

       Phrases phrase=(Phrases)getItem(position);

        ((TextView) view.findViewById(R.id.text)).setText(phrase.getPhrase());
        ((TextView) view.findViewById(R.id.text)).setOnClickListener((v)->{
Intent intent=new Intent(ctx,ChatActivity.class);
intent.putExtra("phrase",phrase.getPhrase());
            ctx.startActivity(intent);
            textToSpeech.speak(phrase.getPhrase(),TextToSpeech.QUEUE_FLUSH,null);

        });
        ((ImageView) view.findViewById(R.id.popup_menu)).setOnClickListener((v)->{

            PopupMenu pop=new PopupMenu(ctx,v);
            pop.inflate(R.menu.popup);

            pop.setOnMenuItemClickListener((item)->{
                switch(item.getItemId()){
                    case R.id.edit:

                        View dialogue=inflater.inflate(R.layout.alert_dialogue,null);
                        ((EditText) dialogue.findViewById(R.id.edit_text)).setText(phrase.getPhrase());
                        AlertDialog.Builder builder=new AlertDialog.Builder(ctx);
                        builder
                                .setTitle("Edit Phrase")
                                .setView(dialogue)
                                .setPositiveButton("Edit",(d,i)->{

                                    String newFrase=((EditText) dialogue.findViewById(R.id.edit_text)).getText().toString();
                                    if(!newFrase.isEmpty()) {
                                        phrase.setPhrase(newFrase);
                                    }
                                    else{
                                        Toast.makeText(ctx,"Empty",Toast.LENGTH_SHORT).show();

                                    }

                                })
                                .show();
                        break;
                    case R.id.delete:
                        array.remove(phrase);
                        notifyDataSetChanged();
                        break;

                }

                return true;
            });


            pop.show();




        });


       return view;
    }
}

