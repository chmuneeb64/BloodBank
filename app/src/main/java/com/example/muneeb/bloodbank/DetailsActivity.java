package com.example.muneeb.bloodbank;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    Button donor, receiver, profile;
    String email;
    Intent intent;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        donor = (Button) findViewById(R.id.donor_button);
        receiver = (Button) findViewById(R.id.reciver_button);
        profile = (Button) findViewById(R.id.profile_button);

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        email = sharedPreferences.getString("email_key","not found");


        setFragment(new DonorFragment());

       /* SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email_key","not found");
*/

        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new DonorFragment());


            }
        });

        receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ReceiverFragment());
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ProfileFrag());

                /*Intent profile = new Intent(DetailsActivity.this, ProfileActivity.class);
                startActivity(profile);*/
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            editor.clear();
            editor.apply();
            intent = new Intent(DetailsActivity.this,MainActivity.class);
            /*intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/

            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setFragment(Fragment f) {

        FragmentManager fm = getSupportFragmentManager();
        /*Bundle  bundle =new Bundle();
        bundle.putString("one", "Hello Muneeb,\n From FragOne");
        bundle.putString("two", "Hello Muneeb,\n From FragTwo");
        f.setArguments(bundle);*/

        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out);
        ft = ft.replace(R.id.frame, f);
        ft.commit();
    }
}
