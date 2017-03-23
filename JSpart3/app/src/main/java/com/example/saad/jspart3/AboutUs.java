package com.example.saad.jspart3;


import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUs extends Fragment {


    public AboutUs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AlertDialog.Builder ab=new AlertDialog.Builder(this.getActivity());
        ab.setMessage("Jobseekicious is an application based on the concept of job portal system ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                /*.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });*/
        AlertDialog alert=ab.create();
        alert.setTitle("About us");
        alert.setIcon(R.mipmap.aboutus);
        alert.show();
        //alert.getWindow().setBackgroundDrawableResource(android.R.color.holo_blue_light);
        //alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }

}
