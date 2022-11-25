package com.vikvita.javaprojext;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
/**                                  Main Screen                                     */
public class MainActivity extends AppCompatActivity {
/**                            Declaring Variables                          */
DrawerLayout sideMenu;
ListView phraseList;
ArrayList<Phrases> phrasesArrayList;
Button newDialogue;
NavigationView navigView;
/***********************************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**                  Initializing variables */
        sideMenu=findViewById(R.id.sideMenu);
        phraseList =findViewById(R.id.fraseList);
        newDialogue=findViewById(R.id.newDialogue);
        navigView=findViewById(R.id.navigView);
        loadData();
        /***************************************************************************/





/**set some settings for screen*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger_md);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
/******************************************************************************************8*/

/**                            Set Adapter for list of ready Phrase */
        phraseList.setAdapter(new Adapter(this, phrasesArrayList));

      /**Set Listeners for the Button*/
        newDialogue.setOnClickListener((v)->{
            Intent intent=new Intent(this,ChatActivity.class);
            intent.putParcelableArrayListExtra("phraseArray",phrasesArrayList);
            startActivity(intent);

        });

              navigView.setNavigationItemSelectedListener((item -> {
                  switch (item.getItemId()){
                      case R.id.nD:
                          Intent intent=new Intent(this,ChatActivity.class);
                          intent.putParcelableArrayListExtra("phraseArray",phrasesArrayList);
                          startActivity(intent);

                          break;

                  }

                  return true;
              }));
    }

    /**Listener for menu in action bar */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                if(!sideMenu.isOpen()){
                sideMenu.openDrawer(GravityCompat.START);}
                else{
                    sideMenu.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.add:
                showDialog("Add New Phrase");
                break;
        }

        return true;
    }
    /******************************************************************/

    /** Connect menu from package menu with action bar */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu,menu);

        return true;
    }
/************************************************************/


    @Override
    protected void onStop() {

        super.onStop();

        saveData();
    }

    /**          Create Alert Dialogue for Adding new Phrase                  */
public void showDialog(String title){
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.alert_dialogue,null);

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder
                .setTitle(title)
                .setView(view)
                .setPositiveButton("Add",(d,i)->{

                    String newFrase=((EditText) view.findViewById(R.id.edit_text)).getText().toString();
                    if(!newFrase.isEmpty()) {
                        phrasesArrayList.add(new Phrases(newFrase));
                    }
                    else{
                        Toast.makeText(this,"Empty",Toast.LENGTH_SHORT).show();
                    }



                })
                .show();
    }
    /******************************************************************************/



/**Methods for saving list of ready Phrases*/
    private void loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("phrases", null);

        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<Phrases>>() {}.getType();

        // in below line we are getting data from gson
        // and saving it to our array list
       phrasesArrayList = gson.fromJson(json, type);

        // checking below if the array list is empty or not
        if (phrasesArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            phrasesArrayList = new ArrayList<>();
            phrasesArrayList.add(new Phrases("I can’t talk, but this app talks for me."));
            phrasesArrayList.add(new Phrases("Can you help me, please?"));
            phrasesArrayList.add(new Phrases("Good morning, How are you?"));
            phrasesArrayList.add(new Phrases("Sorry, could you please hepl me with this problem?"));
            phrasesArrayList.add(new Phrases("Let me help you!"));
            phrasesArrayList.add(new Phrases("Let’s go there, I love that place."));
        }
    }

    private void saveData() {
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(phrasesArrayList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("phrases", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();

        // after saving data we are displaying a toast message.

    }
    /*************************************************************************************/

}
