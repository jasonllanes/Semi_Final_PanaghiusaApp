package com.sldevs.panaghiusa.ProfilePane;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.sldevs.panaghiusa.R;
import com.sldevs.panaghiusa.TBC_Items_Organic;
import com.sldevs.panaghiusa.TBC_Items_Plastic;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TBC_Organic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TBC_Organic extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseDatabase firebaseDatabase;
    FirebaseListAdapter adaptor;

    ListView lvTBC;

    public TBC_Organic() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TBC_Organic.
     */
    // TODO: Rename and change types and number of parameters
    public static TBC_Organic newInstance(String param1, String param2) {
        TBC_Organic fragment = new TBC_Organic();
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
        View v = inflater.inflate(R.layout.fragment_t_b_c__organic, container, false);
        lvTBC = v.findViewById(R.id.lvTBC);

        firebaseDatabase = FirebaseDatabase.getInstance();

        String id = FirebaseAuth.getInstance().getUid();
        Query query = FirebaseDatabase.getInstance().getReference().child("TBC_OrganicSpecific/" + id);
        FirebaseListOptions<TBC_Items_Organic> o = new FirebaseListOptions.Builder<TBC_Items_Organic>()
                .setLayout(R.layout.tbc_items_organic)
                .setQuery(query, TBC_Items_Organic.class)
                .build();
        adaptor = new FirebaseListAdapter(o) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView tvID = v.findViewById(R.id.tvID);
                TextView tvContributions = v.findViewById(R.id.tvContributions);
                TextView tvName = v.findViewById(R.id.tvName);
                TextView tvNumber = v.findViewById(R.id.tvNumber);
                TextView tvDate = v.findViewById(R.id.tvDateTime);
                String date_time;


                TBC_Items_Organic tbc_items_organic = (TBC_Items_Organic) model;
                tvID.setText("Contribution ID: " + tbc_items_organic.getContributionid());
                tvContributions.setText("Contributions: " + tbc_items_organic.getContribution());
                tvName.setText("Fullname: " + tbc_items_organic.getFullname());
                tvNumber.setText("Mobile No.: " + tbc_items_organic.getNumber());
                date_time = tbc_items_organic.getDate() + ", " + tbc_items_organic.getTime();
                tvDate.setText("Date & Time: " + date_time);
                lvTBC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getContext(),"Position: " + String.valueOf(i),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        lvTBC.setAdapter(adaptor);


        return v;
    }
    @Override
    public void onStart() {
        super.onStart();
        adaptor.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adaptor.stopListening();
    }

}