package com.example.muneeb.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText emailText, passwordText;
    String email, password;
    CheckBox checkBox;
    Button login, signUp;
    ImageView imageView;
    Intent intent;
    DataBase dataBase;
    SharedPreferences sharedPreferences;
    SharedPreferences session;
    SharedPreferences.Editor editor, sEditor;
    Boolean checkBoxValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        login = (Button) findViewById(R.id.login);
        signUp = (Button) findViewById(R.id.signUp);
        imageView = (ImageView) findViewById(R.id.frontImage);

        sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        session = getSharedPreferences("session", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sEditor = session.edit();


        dataBase = new DataBase(MainActivity.this);
        dataBase.open();


        checkBoxValue = sharedPreferences.getBoolean("checkbox_key", false);
        if (checkBoxValue == true) {
            emailText.setText(sharedPreferences.getString("email_key", ""));
            passwordText.setText(sharedPreferences.getString("pass_key", ""));
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        String logedIn = session.getString("email_key",null);
        if (logedIn!=null){
            intent = new Intent(MainActivity.this,DetailsActivity.class);
            startActivity(intent);
            finish();
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    intent = new Intent(MainActivity.this, DetailsActivity.class);
                    startActivity(intent);
                   /*ArrayList<String> data = dataBase.fetch();
                    for (int i = 0; i < data.size(); i++) {
                        Toast.makeText(MainActivity.this, data.get(i), Toast.LENGTH_SHORT).show();
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    email = emailText.getText().toString();
                    password = passwordText.getText().toString();
                    if (checkBox.isChecked()) {
                        editor.putString("email_key", email);
                        editor.putString("pass_key", password);
                        editor.putBoolean("checkbox_key", true);
                        editor.apply();
                        sEditor.putString("email_key", email);
                        sEditor.apply();
                    } else {
                        editor.clear();
                        editor.apply();
                    }
                    String searchPass = dataBase.searchPass(email);
                    if (password.equals(searchPass)) {
                        intent = new Intent(MainActivity.this, DetailsActivity.class);

                        intent.putExtra("email", email);
                        dataBase.close();
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(MainActivity.this, "Email And Password don't match!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
