package com.vikvita.javaprojext;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Class ChatAdapter for List View
 *
 * this  class adaptates  one common pattern for each element
 * In current situation we have class Phrases which has one field(phrase)
 * this adapter take already existing pattern(message.xml and message_2.xml )
 * and change text of TextView to the Message.getMessage() and Message.getSide
 * and do this action for each element which is in the Array List
 *
 * */
public class ChatAdapter extends BaseAdapter {

/**       Declaring of variables */
    private ArrayList<Message> array;
    private Context ctx;
    LayoutInflater inflater;
    private TextToSpeech textToSpeech;
/****************************************************************/
   /**Constructor where all variables are initialized*/
    public ChatAdapter(Context ctx,ArrayList<Message> array){
        this.array=array;
        this.ctx=ctx;
        inflater=(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        textToSpeech=new TextToSpeech(ctx,(i)->{

            if(i!=TextToSpeech.ERROR){
                textToSpeech.setLanguage(Locale.US);

            }
        });
    }
/**************************************************************************************/

/**Return size of array*/
    @Override
    public int getCount() {
        return array.size();
    }
    /*********************************/

    /**Return element which is on the particular position */
    @Override
    public Object getItem(int position) {
        return array.get(position);
    }
    /*****************************************************/

    /** return index of current element */
    @Override
    public long getItemId(int position) {
        return position;
    }
/*********************************************************************/


    /**  Drawing view for each element */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewL=inflater.inflate(R.layout.message_2,parent,false);
        View viewR=inflater.inflate(R.layout.message,parent,false);


        if(((Message)getItem(position)).getSide()==LR.LEFT) {
            ((TextView)viewL.findViewById(R.id.messageL)).setText(((Message)getItem(position)).getMessage());

            return viewL;
        }
        else{
            ((TextView)viewR.findViewById(R.id.messageR)).setText(((Message)getItem(position)).getMessage());

            viewR.findViewById(R.id.speech).setOnClickListener((v)->{

                textToSpeech.speak(((Message)getItem(position)).getMessage(),TextToSpeech.QUEUE_FLUSH,null);

            });
            return viewR;
        }
    }

    /*********************************************************************************************/

public void addTo(Message message){
    array.add(message);
    notifyDataSetChanged();
}
}
