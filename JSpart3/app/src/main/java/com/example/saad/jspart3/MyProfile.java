package com.example.saad.jspart3;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfile extends Fragment {

    private StorageReference storageReference;

    Switch myswitch;
    ProgressDialog waitDialog;
    ImageView dpImage;
    EditText _MyProfile_name;
    EditText _MyProfile_email;
    EditText _MyProfile_accountType;
    EditText _MyProfile_gender;
    EditText _MyProfile_dOb;
    EditText _MyProfile_country;
    EditText _MyProfile_city;
    Button _MyProfile_btnSave;
    Button _MyProfile_btnCancel;

    String real_name;
    String real_account;
    String real_gender;
    String real_dob;
    String real_country;
    String real_city;

    Firebase ref;

    String email_key;

    Firebase user;

    public MyProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_profile, container, false);

        waitDialog = new ProgressDialog(getActivity());

        Firebase.setAndroidContext(getActivity());
        ref = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database/");

        Intent myintent = getActivity().getIntent();
        email_key = myintent.getStringExtra("email");

        user = ref.child(email_key.replace(".","/"));



        myswitch=(Switch)view.findViewById(R.id.switch1);
        dpImage = (ImageView)view.findViewById(R.id.imageView1);
        _MyProfile_name=(EditText)view.findViewById(R.id._my_profile_name);
        _MyProfile_email=(EditText)view.findViewById(R.id._my_profile_email);
        _MyProfile_accountType=(EditText)view.findViewById(R.id._my_profile_signup_as);
        _MyProfile_gender=(EditText)view.findViewById(R.id._my_profile_gender);
        _MyProfile_dOb=(EditText)view.findViewById(R.id._my_profile_dOb);
        _MyProfile_country=(EditText)view.findViewById(R.id._my_profile_country);
        _MyProfile_city=(EditText)view.findViewById(R.id._my_profile_city);
        _MyProfile_btnSave=(Button)view.findViewById(R.id._my_profile_save);
        _MyProfile_btnCancel=(Button)view.findViewById(R.id._my_profile_cancel);

        SharedPreferences sharedpref=MyProfile.this.getActivity().getPreferences(Context.MODE_PRIVATE);
        String result=sharedpref.getString("My key",null);
        /*if(result!=null)
        {
            text_view.setText(result);
        }*/
        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    _MyProfile_name.setText("");
                    _MyProfile_name.setEnabled(true);
                    _MyProfile_email.setEnabled(false);
                    _MyProfile_accountType.setText("");
                    _MyProfile_accountType.setEnabled(true);
                    _MyProfile_gender.setText("");
                    _MyProfile_gender.setEnabled(true);
                    _MyProfile_dOb.setText("");
                    _MyProfile_dOb.setEnabled(true);
                    _MyProfile_country.setText("");
                    _MyProfile_country.setEnabled(true);
                    _MyProfile_city.setText("");
                    _MyProfile_city.setEnabled(true);
                    _MyProfile_btnSave.setEnabled(true);
                    _MyProfile_btnCancel.setEnabled(true);
                    SharedPreferences sharedpref=MyProfile.this.getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedpref.edit();
                    editor.putString("My key","True");
                    editor.commit();

                }
                else {
                    _MyProfile_name.setEnabled(false);
                    _MyProfile_email.setEnabled(false);
                    _MyProfile_accountType.setEnabled(false);
                    _MyProfile_gender.setEnabled(false);
                    _MyProfile_dOb.setEnabled(false);
                    _MyProfile_country.setEnabled(false);
                    _MyProfile_city.setEnabled(false);
                    _MyProfile_btnSave.setEnabled(false);
                    _MyProfile_btnCancel.setEnabled(false);
                    SharedPreferences sharedpref=MyProfile.this.getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedpref.edit();
                    editor.putString("My key","False");
                    editor.commit();

                }
            }
        });
        /*ed_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ed_4.getText().toString()!=ed_3.getText().toString()){
                    ed_4.setError("password doesn't match");
                }
                else{
                    ed_4.setError("correct",getResources().getDrawable(R.drawable.green_tick));
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,String> map = dataSnapshot.getValue(Map.class);

                Picasso.with(getActivity()).load(map.get("Image_URL")).into(dpImage);
                dpImage.invalidate();
                dpImage.postInvalidate();

                String name = map.get("First_Name") + " " +map.get("Last_Name");
                real_name = name;
                _MyProfile_name.setHint(name);
                String email = map.get("Email_Address");
                _MyProfile_email.setHint(email);
                if(map.get("Is_Employer").equals("true")){
                String account_type = "Employer";
                    _MyProfile_accountType.setHint(account_type);
                    real_account = account_type;
                }
                else {
                    String account_type = "Employee";
                    real_account = account_type;
                    _MyProfile_accountType.setHint(account_type);
                }
                String dOb = map.get("Date_of_Birth");
                real_dob = dOb;
                _MyProfile_dOb.setHint(dOb);
                if(map.get("Is_Male").equals("true")){
                    String gender = "Male";
                    real_gender = gender;
                    _MyProfile_gender.setHint(gender);
                }
                else {
                    String gender = "Female";
                    real_gender = gender;
                    _MyProfile_gender.setHint(gender);
                }
                String country = map.get("Country");
                real_country = country;
                _MyProfile_country.setHint(country);
                String city = map.get("City");
                real_city = city;
                _MyProfile_city.setHint(city);



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        _MyProfile_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] names = _MyProfile_name.getText().toString().split(" ");
                if(_MyProfile_accountType.getText().toString().equals("Employer")){
                    String account_type = "Employer";
                    Firebase Is_employer = user.child("Is_Employer");
                    Is_employer.setValue("true");
                    Firebase Is_employee = user.child("Is_Employee");
                    Is_employer.setValue("false");
                }
                else if(_MyProfile_accountType.getText().toString().equals("Employee")){
                    String account_type = "Employee";
                    Firebase Is_employer = user.child("Is_Employer");
                    Is_employer.setValue("false");
                    Firebase Is_employee = user.child("Is_Employee");
                    Is_employer.setValue("true");
                }
                if(_MyProfile_gender.getText().toString().equals("Male")){
                    String gender = "Male";
                    Firebase Is_male = user.child("Is_Male");
                    Is_male.setValue("true");
                    Firebase Is_female = user.child("Is_Female");
                    Is_female.setValue("false");
                }
                else if(_MyProfile_gender.getText().toString().equals("Female")){
                    String gender = "Female";
                    Firebase Is_male = user.child("Is_Male");
                    Is_male.setValue("false");
                    Firebase Is_female = user.child("Is_Female");
                    Is_female.setValue("true");
                }
                String country = _MyProfile_country.getText().toString();
                String city = _MyProfile_city.getText().toString();
                String dob = _MyProfile_dOb.getText().toString();
                try {
                    Firebase first_Name = user.child("First_Name");
                    first_Name.setValue(names[0]);
                    Firebase last_Name = user.child("Last_Name");
                    last_Name.setValue(names[1]);
                    Firebase dateofbirth = user.child("Date_of_Birth");
                    dateofbirth.setValue(dob);
                    Firebase fire_country = user.child("Country");
                    fire_country.setValue(country);
                    Firebase fire_city = user.child("City");
                    fire_city.setValue(city);
                    Toast.makeText(getActivity().getApplicationContext(),"Changes Saved",Toast.LENGTH_SHORT).show();
                    _MyProfile_name.setEnabled(false);
                    _MyProfile_email.setEnabled(false);
                    _MyProfile_accountType.setEnabled(false);
                    _MyProfile_gender.setEnabled(false);
                    _MyProfile_dOb.setEnabled(false);
                    _MyProfile_country.setEnabled(false);
                    _MyProfile_city.setEnabled(false);
                    _MyProfile_btnSave.setEnabled(false);
                    _MyProfile_btnCancel.setEnabled(false);
                    myswitch.setChecked(false);
                }
                catch (Exception ex){}


            }
        });
        _MyProfile_btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _MyProfile_name.setText(real_name);
                _MyProfile_name.setEnabled(false);
                _MyProfile_email.setEnabled(false);
                _MyProfile_accountType.setText(real_account);
                _MyProfile_accountType.setEnabled(false);
                _MyProfile_gender.setText(real_gender);
                _MyProfile_gender.setEnabled(false);
                _MyProfile_dOb.setText(real_dob);
                _MyProfile_dOb.setEnabled(false);
                _MyProfile_country.setText(real_country);
                _MyProfile_country.setEnabled(false);
                _MyProfile_city.setText(real_city);
                _MyProfile_city.setEnabled(false);
                _MyProfile_btnSave.setEnabled(false);
                _MyProfile_btnCancel.setEnabled(false);
                myswitch.setChecked(false);
            }
        });
        dpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitDialog.show();
                storageReference = FirebaseStorage.getInstance().getReference();
                //sCvEdittext = (EditText)findViewById(R.id.sCVTextview);
                Intent newIntent = new Intent(Intent.ACTION_PICK);
                newIntent.setType("image/*");
                //newIntent.setData("asd");
                startActivityForResult(newIntent,3);
            }
        });

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3 && resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference filepath = storageReference.child("Images").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    try {
                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        waitDialog.hide();
                        Toast.makeText(getActivity().getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                        Firebase update1 = user.child("Image_URL");
                        update1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String value = dataSnapshot.getValue(String.class);
                                Log.d("value-> ", value);
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                        update1.setValue(downloadUri);
                        Picasso.with(getActivity()).load(downloadUri).into(dpImage);
                        dpImage.invalidate();
                        dpImage.postInvalidate();
                    }
                    catch(Exception ex){

                    }


                }
            });
        }
    }


}
