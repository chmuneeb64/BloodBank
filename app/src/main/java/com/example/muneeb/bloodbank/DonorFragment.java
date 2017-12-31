package com.example.muneeb.bloodbank;



import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonorFragment extends Fragment {

    ArrayList<Integer> img = new ArrayList<>();
    ArrayList<ModelClass> data =new ArrayList<>();
    ModelClass modelClass;

    ListAdapter listAdapter;
    DataBase dataBase;
    Cursor cursor;

    Dialog dialog;



    public DonorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_donor, container, false);
        ListView listView = (ListView) v.findViewById(R.id.myList);

        dataBase = new DataBase(getActivity());
        dataBase.open();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                dialog = new Dialog(getContext());
                dialog.setTitle("Donor Details");
                dialog.setContentView(R.layout.dialog_xml);

                TextView nameTv = (TextView) dialog.findViewById(R.id.nameTextView);
                TextView bloodTv = (TextView) dialog.findViewById(R.id.bloodTextView);
                TextView ageTv = (TextView) dialog.findViewById(R.id.ageTextView);
                TextView emailTv = (TextView) dialog.findViewById(R.id.emailTextView);
                TextView cityTv = (TextView) dialog.findViewById(R.id.cityTextView);
                TextView phoneTv = (TextView) dialog.findViewById(R.id.phoneTextView);
                ImageView phoneImg = (ImageView) dialog.findViewById(R.id.phone_imageView);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView);
                Button msgBtn = (Button) dialog.findViewById(R.id.massageButton);
                Button closeBtn = (Button) dialog.findViewById(R.id.closeButton);

                modelClass = data.get(position);

                nameTv.setText(modelClass.getName());
                bloodTv.append("Blood Group : "+modelClass.getBloodGroup());
                ageTv.append("Age : "+modelClass.getAge()+"\nGender: "+modelClass.getGender());
                cityTv.setText(modelClass.getCity());
                phoneTv.setText(modelClass.getPhone());
                emailTv.append("Email : "+modelClass.getEmail());
                imageView.setImageResource(img.get(position));

                /*nameTv.setText(name.get(position));
                bloodTv.append("Blood Group : "+blood.get(position));
                ageTv.append("Age : "+age.get(position)+"\nGender: "+gender.get(position));
                cityTv.setText(city.get(position));
                phoneTv.setText(phone.get(position));
                emailTv.append("Email : "+email.get(position));
                imageView.setImageResource(img.get(position));*/


                dialog.show();
                dialog.setCancelable(false);

                phoneImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+modelClass.getPhone()));
                        startActivity(intent);
                    }
                });

                msgBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("smsto:"+modelClass.getPhone()));
                        startActivity(intent);
                    }
                });

                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            //    Toast.makeText(getContext(),position+" "+name.get(position)+"  "+blood.get(position)+" "+gender.get(position), Toast.LENGTH_SHORT).show();
            }
        });
/*

        // data arrays
        name.add("Hamza");
        name.add("Muneeb");
        name.add("Adeel");
        name.add("Ali");

        blood.add("O-");
        blood.add("B+");
        blood.add("B+");
        blood.add("AB-");

        gender.add("male");
        gender.add("male");
        gender.add("male");
        gender.add("male");

        img.add(R.drawable.avatar1);
        img.add(R.drawable.avatar2);
        img.add(R.drawable.avatar3);
        img.add(R.drawable.avatar4);
*/
        cursor = dataBase.fetch();
        int iName = cursor.getColumnIndex(DataBaseHelper.KEY_NAME);
        int iEmail = cursor.getColumnIndex(DataBaseHelper.KEY_EMAIL);
        int iPhone = cursor.getColumnIndex(DataBaseHelper.KEY_PHONE);
        int iCity = cursor.getColumnIndex(DataBaseHelper.KEY_CITY);
        int iBloodGroup = cursor.getColumnIndex(DataBaseHelper.KEY_BLOODGROUP);
        int iGender = cursor.getColumnIndex(DataBaseHelper.KEY_GENDER);
        int iAge = cursor.getColumnIndex(DataBaseHelper.KEY_AGE);

        cursor.moveToFirst();
        int i = 0;
        do {
            if (cursor != null){
                i++;

                /*name.add(cursor.getString(iName));
                email.add(cursor.getString(iEmail));
                phone.add(cursor.getString(iPhone));
                city.add(cursor.getString(iCity));
                blood.add(cursor.getString(iBloodGroup));
                gender.add(cursor.getString(iGender));
                age.add(cursor.getString(iAge));*/
                img.add(R.drawable.avatar3);

                modelClass = new ModelClass();

                modelClass.setName(cursor.getString(iName));
                modelClass.setEmail(cursor.getString(iEmail));
                modelClass.setPhone(cursor.getString(iPhone));
                modelClass.setCity(cursor.getString(iCity));
                modelClass.setAge(cursor.getString(iAge));
                modelClass.setBloodGroup(cursor.getString(iBloodGroup));
                modelClass.setGender(cursor.getString(iGender));
                modelClass.setImg(R.drawable.avatar3);
                data.add(modelClass);
            }

        }while (cursor.moveToNext());

        dataBase.close();

        // set adapter
       // listAdapter = new ListAdapter(getContext(), R.layout.row_xml, name, blood, gender, img);
        listAdapter = new ListAdapter(getContext(), R.layout.row_xml, data);
        listView.setAdapter(listAdapter);

        return v;
    }

}
