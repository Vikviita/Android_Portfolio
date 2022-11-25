package com.vikvita.javaprojext;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

/** This class control chat window */
public class ChatActivity extends AppCompatActivity {

    /**  ***********************************************Declaring variables and views**************************** */
    private ImageButton backArrow;

    private ImageButton sendMessage;
    private EditText editMessage;
    private static ArrayList<Message> array;
    private String frase;

    private ImageButton microphone;
    private SpeechRecognizer speechRecognizer;
    private TextToSpeech textToSpeech;

    private ListView messageList;
    private ChatAdapter chatAdapter;
    LottieAnimationView animationView;

    /******************************************************************************************************8*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
/**        initializing variables and connecting views with this class                          */
        messageList=findViewById(R.id.messageList);
        backArrow=findViewById(R.id.backArrow);
        array=new ArrayList<>();
        chatAdapter=new ChatAdapter(this,array);
        editMessage=findViewById(R.id.editMessage);
        sendMessage=findViewById(R.id.sendMessage);
        microphone=findViewById(R.id.micro_button);
        animationView=findViewById(R.id.animationView);

       /***********************************************************************************************/

       /** set settings for converting text to Speech*/
       textToSpeech=new TextToSpeech(this,(i)->{

            if(i!=TextToSpeech.ERROR){
                textToSpeech.setLanguage(Locale.US);

            }
        });
/*********************************************************************************************/

/**         taking ready example from main screen if user press on it and add it to the chat          */
       frase =getIntent().getStringExtra("phrase");
         if(frase!=null){

    array.add(new Message(frase,LR.RIGHT));
         }
         /*********************************************************************************/

        /** set the adapter for the list view of a chat and remove divider  */
         messageList.setDivider(null);
        messageList.setAdapter(new ChatAdapter(this,array));
/***********************************************************************/




        /**    ask permission to use microphone                    */
         if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){

             ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},1);
         }
/**********************************************************************************************************************/

/**                                  create speechrecognizer which converts speech to text */
        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
       final Intent speechRecognizerIntent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
       speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
/***********************************************************************************************************************/

/** Set Listeners for button and speechRecognizer */
        microphone.setOnClickListener((v)->{

//
               Toast.makeText(this,"Speak",Toast.LENGTH_SHORT).show();
              speechRecognizer.startListening(speechRecognizerIntent);
              animationView.setVisibility(View.VISIBLE);






       });
        backArrow.setOnClickListener((v)->finish());
        sendMessage.setOnClickListener((v)->{
            String frase=editMessage.getText().toString();
            if(!frase.isEmpty()) {
                array.add(new Message(frase, LR.RIGHT));
                chatAdapter.notifyDataSetChanged();
                textToSpeech.speak(frase,TextToSpeech.QUEUE_FLUSH,null);
                messageList.setSelection(chatAdapter.getCount() - 1);
                editMessage.setText("");
            }
            else{
                Toast.makeText(this,"empty",Toast.LENGTH_SHORT).show();
            }

        });


        speechRecognizer.setRecognitionListener(new RecognitionListener() {
           @Override
           public void onReadyForSpeech(Bundle params) {

           }

           @Override
           public void onBeginningOfSpeech() {
                            animationView.playAnimation();
           }

           @Override
           public void onRmsChanged(float rmsdB) {

           }

           @Override
           public void onBufferReceived(byte[] buffer) {

           }

           @Override
           public void onEndOfSpeech() {
               animationView.setVisibility(View.GONE);
animationView.cancelAnimation();
           }

           @Override
           public void onError(int error) {

           }

           @Override
           public void onResults(Bundle results) {
              ArrayList<String> data=results.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);

               array.add(new Message(data.get(0), LR.LEFT));
               chatAdapter.notifyDataSetChanged();
               messageList.setSelection(chatAdapter.getCount() - 1);
           }

           @Override
           public void onPartialResults(Bundle partialResults) {

           }

           @Override
           public void onEvent(int eventType, Bundle params) {

           }
       });
/**********************************************************************************************************************/


/** Hide Navigation bar and Action bar*/
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getSupportActionBar().hide();
        /***************************************************************************************************/
    }


/**method which calls when user give permission or deny it */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /*******************************************************************************/
}