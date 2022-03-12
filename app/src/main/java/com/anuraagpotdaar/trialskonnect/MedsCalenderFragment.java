package com.anuraagpotdaar.trialskonnect;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anuraagpotdaar.trialskonnect.HelperClasses.MedsDispAdapter;
import com.anuraagpotdaar.trialskonnect.HelperClasses.MedsModel;
import com.anuraagpotdaar.trialskonnect.databinding.FragmentMedsCalenderBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MedsCalenderFragment extends Fragment {

    private FragmentMedsCalenderBinding binding;

    RecyclerView recyclerView;
    MedsDispAdapter medsDispAdapter;
    ArrayList<MedsModel> medsList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMedsCalenderBinding.inflate(inflater, container, false);


        String selected = getActivity().getIntent().getStringExtra("selected participant");

        recyclerView = binding.rvMedList;
        DatabaseReference medsRef = FirebaseDatabase.getInstance().getReference("Patient List/"+ selected + "/meds");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        medsList= new ArrayList<>();

        medsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    MedsModel medsModel = new MedsModel();
                    medsModel.setMedName(dataSnapshot.getKey());

                    DatabaseReference medsTimeRef = FirebaseDatabase.getInstance().getReference("Patient List/"+ selected + "/meds/" + medsModel.getMedName());

                    medsTimeRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()) {

                                medsModel.setMorning(snapshot.child("Morning").getValue(String.class));
                                medsModel.setAfternoon(snapshot.child("Afternoon").getValue(String.class));
                                medsModel.setEvening(snapshot.child("Evening").getValue(String.class));
                                medsModel.setNight(snapshot.child("Night").getValue(String.class));
                            }
                            medsDispAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    medsList.add(medsModel);
                }
                medsDispAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        medsDispAdapter = new MedsDispAdapter(getContext(),medsList);
        recyclerView.setAdapter(medsDispAdapter);


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}