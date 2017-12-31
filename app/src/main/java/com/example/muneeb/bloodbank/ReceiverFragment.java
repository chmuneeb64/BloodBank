package com.example.muneeb.bloodbank;


import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReceiverFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    String[] genderArray = {"Male", "Female"};     // why we not use this
    String[] bloodGroupArray = {"A+","A-","B+","B-","AB+","AB-","O+","O-"};
    ArrayList<ModelClass> data =new ArrayList<>();
    Spinner gender, bloodGroup;
    String genderValue, bloodGroupValue;
    Button search;
    ListView listView;
    ListAdapter listAdapter;
    ModelClass modelClass;
    DataBase dataBase;
    View view;

    Dialog dialog;


    public ReceiverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_receiver, container, false);

        gender = (Spinner) view.findViewById(R.id.gender_spinner);
        bloodGroup = (Spinner) view.findViewById(R.id.bloodGroup_spinner);
        search = (Button) view.findViewById(R.id.searchButton);
        listView = (ListView) view.findViewById(R.id.receiverList);


        dataBase = new DataBase(getActivity());
        dataBase.open();

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1, genderArray);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);

        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_activated_1,bloodGroupArray);
        bloodGroup.setAdapter(bloodAdapter);
        gender.setOnItemSelectedListener(this);
        bloodGroup.setOnItemSelectedListener(this);





        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderValue =gender.getSelectedItem().toString();
                bloodGroupValue =bloodGroup.getSelectedItem().toString();

                data = dataBase.receiverSearch(genderValue,bloodGroupValue);


                listAdapter = new ListAdapter(getContext(), R.layout.row_xml, data);
                listView.setAdapter(listAdapter);

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
                        imageView.setImageResource(modelClass.getImg());

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


                Toast.makeText(getContext(), "you selected " + genderValue+"  "+bloodGroupValue , Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         String item = parent.getItemAtPosition(position).toString();


                Toast.makeText(getContext(), "you selected " + item , Toast.LENGTH_SHORT).show();
            }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
