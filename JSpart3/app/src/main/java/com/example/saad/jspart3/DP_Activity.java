package com.example.saad.jspart3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class DP_Activity extends AppCompatActivity {

    ImageView src;
    ImageView cancelImage;
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dp_);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://js-part-3.firebaseio.com/SignUp_Database/");


        src = (ImageView)findViewById(R.id.dpImage);
        cancelImage = (ImageView)findViewById(R.id.cancelImage);

        Intent myintent = getIntent();
        String pictureURLrecieved = myintent.getStringExtra("email");
        //Firebase user = ref.child(pictureURLrecieved);
       /* user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = dataSnapshot.getValue(Map.class);
                String img_url = map.get("Image_URL");

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/

        Picasso.with(DP_Activity.this).load(pictureURLrecieved).into(src);
        Log.d("img-> "," "+src);
        src.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //src.invalidate();
        src.invalidate();
        src.postInvalidate();


        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
