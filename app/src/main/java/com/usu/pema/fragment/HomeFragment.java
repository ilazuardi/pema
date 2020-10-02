package com.usu.pema.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.usu.pema.R;
import com.usu.pema.adapter.AdapterAcara;
import com.usu.pema.model.Acara;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private AdapterAcara adapterAcara;

    private List<Acara> listAcara;

    RecyclerView rvEvent;
    ProgressBar pbHomeFragment;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvEvent = view.findViewById(R.id.rv_event);
        pbHomeFragment = view.findViewById(R.id.pb_home_fragment);
        rvEvent.setLayoutManager(new LinearLayoutManager(getContext()));

        storageReference = FirebaseStorage.getInstance().getReference("acara");
        databaseReference = FirebaseDatabase.getInstance().getReference("acara");

        listAcara = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Acara acara = postSnapShot.getValue(Acara.class);
                    listAcara.add(acara);

                    adapterAcara = new AdapterAcara(getActivity(), listAcara);
                    rvEvent.setAdapter(adapterAcara);
//                    Log.i(acara.getJudul(), "onDataChange: ");
                    pbHomeFragment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                pbHomeFragment.setVisibility(View.GONE);
            }
        });

        return view;
    }

}
