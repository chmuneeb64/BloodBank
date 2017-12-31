package com.example.muneeb.bloodbank;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFrag extends Fragment {

    TextView keyId, nameView;
    TextView emailView;
    TextView passwordView;
    TextView phoneView;
    TextView cityView;
    TextView ageView;
    TextView genderView, bloodGroup;
    Button edit;
    String email;
    DataBase dataBase;
    ModelClass modelClass;
    SharedPreferences sharedPreferences;

    public ProfileFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        keyId = (TextView) v.findViewById(R.id.keyView);
        nameView = (TextView) v.findViewById(R.id.nameView);
        emailView = (TextView) v.findViewById(R.id.emailView);
        passwordView = (TextView) v.findViewById(R.id.passwordView);
        phoneView = (TextView) v.findViewById(R.id.phoneView);
        cityView = (TextView) v.findViewById(R.id.cityView);
        ageView = (TextView) v.findViewById(R.id.ageView);
        genderView = (TextView) v.findViewById(R.id.genderView);
        bloodGroup = (TextView) v.findViewById(R.id.bloodGroupView);
        edit = (Button) v.findViewById(R.id.editButton);

        sharedPreferences = getActivity().getSharedPreferences("session", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email_key", "not found");

        dataBase = new DataBase(getActivity());
        dataBase.open();

        ArrayList<ModelClass> data = dataBase.searchData(email);
        modelClass = new ModelClass();
        modelClass = data.get(0);

        keyId.append(modelClass.getId());
        nameView.append(" : " + modelClass.getName());
        emailView.append(" : " + modelClass.getEmail());
        passwordView.append(" : " + modelClass.getPassword());
        phoneView.append(" : " + modelClass.getPhone());
        cityView.append(" : " + modelClass.getCity());
        ageView.append(" : " + modelClass.getAge());
        bloodGroup.append(" : " + modelClass.bloodGroup);
        genderView.append(" : " + modelClass.getGender());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setFragment(new UpdateFrag());
                Toast.makeText(getContext(), "Please wait for this", Toast.LENGTH_SHORT).show();


            }
        });


        return v;
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
