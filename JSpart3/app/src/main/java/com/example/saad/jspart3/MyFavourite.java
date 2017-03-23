package com.example.saad.jspart3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyFavourite.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyFavourite#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFavourite extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ListView text;
    ArrayList<Word> list = new ArrayList<Word>();
    ArrayAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyFavourite() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFavourite.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFavourite newInstance(String param1, String param2) {
        MyFavourite fragment = new MyFavourite();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_favourite, container, false);
        text = (ListView) view.findViewById(R.id.myrecord);
        SqlConnection mycon = new SqlConnection(getActivity());
        Cursor result =  mycon.getData();
        if(result.getCount() == 0){
           // text.setText("No Record Found.");
        }
        else{
            StringBuffer data = new StringBuffer();
            while (result.moveToNext()){
                data.append("ID: "+result.getString(0)+" Title: "+result.getString(1)+"\n");
                list.add(new Word(" "+result.getString(1) ," "+result.getString(2)," "+result.getString(3)," "+result.getString(4)," "+result.getString(6)," "+result.getString(5)," "+result.getString(7)," "+result.getString(8)," "+result.getString(9)," "+result.getString(10)," "+result.getString(11)," "+result.getString(12)," "+result.getString(13)," "+result.getString(14)," "+result.getString(15)," "+result.getString(16),""));
            }
            WordAdapter adaptor = new WordAdapter(getActivity(),list);
            text.setAdapter(adaptor);
            text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Word data = list.get(position);
                    Intent intent = new Intent(getActivity(), the_Job.class);
                    intent.putExtra("jobtitle", data.getmTitle());
                    intent.putExtra("company", data.getmCompany());
                    intent.putExtra("city", data.getmCity());
                    intent.putExtra("state", data.getmState());
                    intent.putExtra("country", data.getmCountry());
                    intent.putExtra("formattedLocation", data.getmformattedLocation());
                    intent.putExtra("source", data.getmSource());
                    intent.putExtra("data", data.getmPostDate());
                    intent.putExtra("snippet", data.getmSnippet());
                    intent.putExtra("url", data.getmURL());
                    intent.putExtra("latitude", data.getmLatitude());
                    intent.putExtra("longitude", data.getmLongitude());
                    intent.putExtra("jobkey", data.getmJobkey());
                    intent.putExtra("sponsored", data.getmSponsored());
                    intent.putExtra("expired", data.getmExpired());
                    intent.putExtra("formattedLocationFull", data.getMformattedLocationFull());
                    intent.putExtra("formattedRelativeTime", data.getMformattedRelativeTime());
                    intent.putExtra("career", data.getmEmployeeCareer());
                    intent.putExtra("category", data.getmJobCategory());
                    intent.putExtra("qualification", data.getmEmployeeQualification());
                    intent.putExtra("number", data.getmNumberOfPost());
                    intent.putExtra("salary", data.getmSalary());
                    intent.putExtra("skill", data.getmEmployeeSkillSet());
                    intent.putExtra("minExperience", data.getmMinimumExperience());
                    intent.putExtra("maxExperience", data.getmMaximumExperience());
                    intent.putExtra("department", data.getmDepartment());
                    intent.putExtra("comment", data.getmComment());
                    //intent.putExtra("email",key);
                    //intent.putExtra("s", s);
                    startActivity(intent);
                }
            });
            text.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Word data = list.get(position);
                    String title = data.getmTitle();
                    SqlConnection con = new SqlConnection(getActivity());
                    Integer result = con.delData(title);
                    if(result != 0){
                        Toast.makeText(getActivity(), "Record Deleted..", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "No Record Deleted..", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
