package com.example.muneeb.bloodbank;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFrag extends Fragment {

    EditText nameText, bloodGroupText;
    EditText emailText;
    EditText passwordText;
    EditText phoneText;
    EditText cityText;
    EditText ageText;
    TextView keyId;
    String name, email, password, phone, city, age, gender, bloodGroup;
    RadioGroup genderGroup;
    int selectedId;
    RadioButton radioButton;
    Button updateButton;
    DataBase dataBase;
    ModelClass modelClass;
    SharedPreferences sharedPreferences;

    View view;

    public UpdateFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update, container, false);

        keyId = (TextView) view.findViewById(R.id.keyUpdateView);
        nameText = (EditText) view.findViewById(R.id.nameText);
        emailText = (EditText) view.findViewById(R.id.emailText);
        passwordText = (EditText) view.findViewById(R.id.passwordText);
        phoneText = (EditText) view.findViewById(R.id.phoneText);
        cityText = (EditText) view.findViewById(R.id.cityText);
        ageText = (EditText) view.findViewById(R.id.ageText);
        bloodGroupText = (EditText) view.findViewById(R.id.bloodGroupText);
        genderGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        updateButton = (Button) view.findViewById(R.id.registerButton);


        sharedPreferences = getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email_key", "not found");

        dataBase = new DataBase(getActivity());
        dataBase.open();

        ArrayList<ModelClass> data = dataBase.searchData(email);
        modelClass = new ModelClass();
        modelClass = data.get(0);

        keyId.append(modelClass.getId());
        nameText.append(modelClass.getName());
        emailText.append(modelClass.getEmail());
        passwordText.append(modelClass.getPassword());
        phoneText.append(modelClass.getPhone());
        cityText.append(modelClass.getCity());
        ageText.append(modelClass.getAge());
        bloodGroupText.append(modelClass.bloodGroup);
       // genderGroup.append(" : "+modelClass.getGender());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                    selectedId = genderGroup.getCheckedRadioButtonId();
                    radioButton = (RadioButton) view.findViewById(selectedId);

                    gender = radioButton.getText().toString();

                    try {
                        String keyId = modelClass.getId();
                        ModelClass modelClass = new ModelClass();


                        modelClass.setName(name);
                        modelClass.setEmail(email);
                        modelClass.setPassword(password);
                        modelClass.setPhone(phone);
                        modelClass.setCity(city);
                        modelClass.setAge(age);
                        modelClass.setBloodGroup(bloodGroup);
                        modelClass.setGender(gender);

                        dataBase.update(keyId,modelClass);

                        setFragment(new ProfileFrag());
                        // dataBase.insert(name, email, password, phone, city, age, bloodGroup, gender);
                        Toast.makeText(getActivity(), "Update Successfully", Toast.LENGTH_SHORT).show();
                        dataBase.close();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "fail \n" + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        return view;
    }

    public void setFragment(Fragment f) {

        FragmentManager fm = getActivity().getSupportFragmentManager();
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
