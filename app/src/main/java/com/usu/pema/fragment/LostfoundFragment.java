package com.usu.pema.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.usu.pema.DetailfoundActivity;
import com.usu.pema.R;
import com.usu.pema.adapter.AdapterLostfound;
import com.usu.pema.model.Lostfound;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LostfoundFragment extends Fragment implements AdapterLostfound.OnItemClickListener{

    private FirebaseStorage firebaseStorage;
    private DatabaseReference databaseReference;

    private AdapterLostfound adapterLostfound;

    private List<Lostfound> listLostfound;

    private RecyclerView rvLostfound;
    private ProgressBar pbLostfoundFragment;
    private ValueEventListener valueEventListener;

    public LostfoundFragment() {
        // Required empty public constructor
    }

    public void openDetailFoundActivity(String[] data) {
        Intent intent = new Intent(getActivity(), DetailfoundActivity.class);
        intent.putExtra("JUDUL_KEY",data[0]);
        intent.putExtra("KONTAK_KEY",data[1]);
        intent.putExtra("DATE_KEY", data[2]);
        intent.putExtra("DETAIL_KEY", data[3]);
        intent.putExtra("IMAGE_KEY", data[4]);
        startActivity(intent);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lostfound, container, false);

        rvLostfound = view.findViewById(R.id.rv_lostfound);
        rvLostfound.setHasFixedSize(true);
        rvLostfound.setLayoutManager(new LinearLayoutManager(getContext()));

        pbLostfoundFragment = view.findViewById(R.id.pb_lostfound_fragment);


        listLostfound = new ArrayList<>();
        adapterLostfound = new AdapterLostfound(getActivity(), listLostfound);
        rvLostfound.setAdapter(adapterLostfound);
        adapterLostfound.setOnItemClickListener(this);

        firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("lostfound");

        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Lostfound lostfound = postSnapShot.getValue(Lostfound.class);
                    listLostfound.add(lostfound);

//                    Log.i(acara.getJudul(), "onDataChange: ");
                    adapterLostfound.notifyDataSetChanged();
                    pbLostfoundFragment.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                pbLostfoundFragment.setVisibility(View.GONE);
            }
        });
        return view;
    }

    @Override
    public void OnItemClick(int position) {

        Lostfound lostfoundClicked = listLostfound.get(position);
        String[] lostfoundData = {
                lostfoundClicked.getJudul(),
                lostfoundClicked.getKontakpenemu(),
                lostfoundClicked.getTanggalPost(),
                lostfoundClicked.getDetail(),
                lostfoundClicked.getGambarBarang()
        };
        openDetailFoundActivity(lostfoundData);

    }

    @Override
    public void OnShowItemClick(int position) {

        Lostfound lostfoundClicked = listLostfound.get(position);
        String[] lostfoundData = {
                lostfoundClicked.getJudul(),
                lostfoundClicked.getKontakpenemu(),
                lostfoundClicked.getTanggalPost(),
                lostfoundClicked.getDetail(),
                lostfoundClicked.getGambarBarang()
        };
        openDetailFoundActivity(lostfoundData);
    }
}
