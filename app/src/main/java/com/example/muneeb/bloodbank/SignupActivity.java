package com.example.muneeb.bloodbank;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    EditText nameText, bloodGroupText;
    EditText emailText;
    EditText passwordText;
    EditText phoneText;
    EditText cityText;
    EditText ageText;
    String name, email, password, phone, city, age, gender, bloodGroup;
    RadioGroup genderGroup;
    RadioButton radioButton;
    Intent intent;
    Button registerButton;
    SharedPreferences session;
    SharedPreferences.Editor editor;
    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameText = (EditText) findViewById(R.id.nameText);
        emailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        phoneText = (EditText) findViewById(R.id.phoneText);
        cityText = (EditText) findViewById(R.id.cityText);
        ageText = (EditText) findViewById(R.id.ageText);
        bloodGroupText = (EditText) findViewById(R.id.bloodGroupText);
        genderGroup = (RadioGroup) findViewById(R.id.radioGroup);
        registerButton = (Button) findViewById(R.id.registerButton);

        session = getSharedPreferences("session", Context.MODE_PRIVATE);
        editor = session.edit();

        dataBase = new DataBase(SignupActivity.this);
        dataBase.open();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SignupActivity.this, DetailsActivity.class);

                if (nameText.getText().toString().isEmpty()) {
                    nameText.setError("Name Required");
                    nameText.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches()) {
                    emailText.setError("Invalid Email Format");
                    emailText.requestFocus();
                } else if (passwordText.getText().toString().isEmpty() || passwordText.getText().toString().length() < 6) {
                    passwordText.setError("Enter password 6 digit long");
                    passwordText.requestFocus();
                } else if (phoneText.getText().toString().isEmpty()) {
                    phoneText.setError("Enter Phone Number");
                    phoneText.requestFocus();
                } else if (cityText.getText().toString().isEmpty()) {
                    cityText.setError("Please Enter City");
                    cityText.requestFocus();
                } else if (ageText.getText().toString().isEmpty() || ageText.getText().toString().length() > 2) {
                    ageText.setError("Enter Valid Age");
                    ageText.requestFocus();
                } else if (bloodGroupText.getText().toString().isEmpty()) {
                    bloodGroupText.setError("blood Group Required");
                    bloodGroupText.requestFocus();
                } else {

                    name = nameText.getText().toString();
                    email = emailText.getText().toString();
                    password = passwordText.getText().toString();
                    phone = phoneText.getText().toString();
                    city = cityText.getText().toString();
                    age = ageText.getText().toString();
                    bloodGroup = bloodGroupText.getText().toString();

                    int selectedId = genderGroup.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selectedId);

                    gender = radioButton.getText().toString();

                    // intent.putExtra("email", email);
                    editor.putString("email_key", email);
                    editor.apply();

                    try {
                        ModelClass modelClass = new ModelClass();

                        modelClass.setName(name);
                        modelClass.setEmail(email);
                        modelClass.setPassword(password);
                        modelClass.setPhone(phone);
                        modelClass.setCity(city);
                        modelClass.setAge(age);
                        modelClass.setBloodGroup(bloodGroup);
                        modelClass.setGender(gender);

                        dataBase.insert(modelClass);

                       // dataBase.insert(name, email, password, phone, city, age, bloodGroup, gender);
                        Toast.makeText(SignupActivity.this, "success full", Toast.LENGTH_SHORT).show();
                        dataBase.close();
                    }catch (Exception e){
                        Toast.makeText(SignupActivity.this, "fail \n" + e.toString() , Toast.LENGTH_SHORT).show();
                    }
                    new AlertDialog.Builder(SignupActivity.this)
                            .setIcon(android.R.drawable.ic_menu_save)
                            .setTitle("Registration successful")
                            .setMessage("Do You Want to continue")
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(intent);
                                    finish();

                                }
                            })
                            .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    intent = new Intent(SignupActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            }
        });
    }
}
