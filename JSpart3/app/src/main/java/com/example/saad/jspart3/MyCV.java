package com.example.saad.jspart3;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCV extends Fragment {

    public static final int check = 1;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    Firebase Database;
    Firebase SignUpDatabase;
    Firebase key;
    Firebase FireCV;


    Button upload;
    Button open;
    Button showcv;

    String intent_key;

    public MyCV() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cv, container, false);

        Firebase.setAndroidContext(getActivity());
        Database = new Firebase("https://js-part-3.firebaseio.com/");

        Intent myintent = getActivity().getIntent();
        intent_key = myintent.getStringExtra("email").replace(".","/");

        SignUpDatabase = Database.child("SignUp_Database");
        //final String a = SignUpDatabase.push().getKey();
        String abc = intent_key;
        String ab = abc.replace(".","/");
        key = SignUpDatabase.child(ab);





        Button upload = (Button) view.findViewById(R.id._fragment_my_cv_upload);
        Button open = (Button) view.findViewById(R.id._fragment_my_cv_open);
        Button show = (Button) view.findViewById(R.id._fragment_my_cv_show);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageReference = FirebaseStorage.getInstance().getReference();

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setType("*/*");
                startActivityForResult(intent, 4);


            }
        });



        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                key.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                          Map<String, String> map = dataSnapshot.getValue(Map.class);
                          String value = map.get("CV_URL");
                        Uri webpage = Uri.parse(value);
                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == 4 && resultCode == Activity.RESULT_OK){
            Uri uuri = data.getData();
            //sCvEdittext = (EditText)findViewById(R.id.sCVTextview);
            final StorageReference Ref = storageReference.child("CVs").child(uuri.getLastPathSegment());
            Ref.putFile(uuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //sCvEdittext.setText(String.valueOf(taskSnapshot.getDownloadUrl()));
                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("CV_URL", String.valueOf(taskSnapshot.getDownloadUrl()));
                    editor.commit();
                    firebaseAuth = FirebaseAuth.getInstance();
                    SignUpDatabase = Database.child("SignUp_Database");
                    //final String a = SignUpDatabase.push().getKey();
                    String abc = intent_key;
                    String ab = abc.replace(".","/");
                    key = SignUpDatabase.child(ab);
                    FireCV = key.child("CV_URL");
                    String CVURL = sharedPref.getString("CV_URL",null);
                    FireCV.setValue(CVURL);
                    //String CVURL = sharedPref.getString("CV_URL",null);
                    Toast.makeText(getActivity(),"File Uploaded", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}
