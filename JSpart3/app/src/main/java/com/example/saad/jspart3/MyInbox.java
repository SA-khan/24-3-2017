package com.example.saad.jspart3;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyInbox extends Fragment {

    Firebase ref;
    ArrayList<MessageWord> list = new ArrayList<MessageWord>();
    String sender_data;
    String key_data;
    String reciever_data;
    String user1;

   // Firebase ref;

    public MyInbox() {
        // Required empty public constructor

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_user_messages, container, false);

        ListView rootView = (ListView) view.findViewById(R.id.mymessagesall);
        // Inflate the layout for this fragment
        //Firebase.setAndroidContext(this.getActivity());
        //ref = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database/");
        Intent myintent = this.getActivity().getIntent();
        String email = myintent.getStringExtra("email");
        String remSpace = email.replace("%40","");
        String dataEmail = remSpace.replace(".","/");
        reciever_data = dataEmail;
        Log.d("reciever_data"," "+reciever_data);
        Firebase.setAndroidContext(this.getActivity());
        ref = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database/"+dataEmail+"/");
        Log.d("user", " "+ dataEmail);
        final Firebase message = ref.child("Messages");
        final MessageAdaptor adapter = new MessageAdaptor(this.getActivity(), list);
        message.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String value = dataSnapshot.getValue(String.class);
                //Log.d("data---???>>>", " "+value);
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                    String clubkey = childSnapshot.getKey();
                    key_data = clubkey;
                    Log.d("Message--->>>"," "+clubkey);
                    final Firebase Message_Ref = message.child(clubkey);
                    Message_Ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                Map<String, String> map = dataSnapshot.getValue(Map.class);
                                user1 = map.get("Destination");
                                String sender = map.get("Source");
                                sender_data = sender;
                                Log.d("_sender_dada_123", " " + sender_data);
                                Firebase SenderRef = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database/");
                                Log.d("_sender", " " + sender);
                                Firebase newRef = SenderRef.child(sender);
                                newRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Map<String, String> map = dataSnapshot.getValue(Map.class);
                                        final String fname = map.get("First_Name");
                                        final String lname = map.get("Last_Name");


                                        Firebase date = Message_Ref.child("Posted_Date");
                                        date.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                try {
                                                    Map<String, String> map = dataSnapshot.getValue(Map.class);
                                                    String date = String.valueOf(map.get("monthDay"));
                                                    String month = String.valueOf(map.get("month"));
                                                    String year = String.valueOf(map.get("year"));
                                                    Log.d("date-->>", " " + date);
                                                    Log.d("month-->>", " " + month);
                                                    Log.d("yeare-->>", " " + year);

                                                    list.add(new MessageWord(fname + " " + lname, date + "-" + month + "-" + year));
                                                } catch (Exception ex) {

                                                }

                                                adapter.notifyDataSetChanged();

                                            }

                                            @Override
                                            public void onCancelled(FirebaseError firebaseError) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {

                                    }
                                });
                            }
                            catch (Exception ex){

                            }

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });





        rootView.setAdapter(adapter);

        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myintent = new Intent(getActivity(),ChatActivity.class);
                myintent.putExtra("Sender", sender_data);
                myintent.putExtra("Key", key_data);
                myintent.putExtra("Reciever", reciever_data);
                myintent.putExtra("user1",user1);
                startActivity(myintent);
            }
        });
        rootView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try
                {
                    list.remove(position);
                    Firebase keyed = message.child(key_data);
                    keyed.removeValue();
                    //getFragmentManager().beginTransaction().remove(view).popBackStackImmediate()
                }
                catch (Exception ex) {
                }
                return true;
            }
        });


        return view;
    }

}
