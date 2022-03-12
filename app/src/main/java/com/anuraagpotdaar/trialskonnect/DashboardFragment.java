package com.anuraagpotdaar.trialskonnect;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anuraagpotdaar.trialskonnect.HelperClasses.MedsDispAdapter;
import com.anuraagpotdaar.trialskonnect.HelperClasses.MedsModel;
import com.anuraagpotdaar.trialskonnect.databinding.FragmentDashboardBinding;
import com.anuraagpotdaar.trialskonnect.databinding.FragmentParticipantHelthInfoBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment

        binding.cardView.setOnClickListener(view1 -> replaceFragment());
        binding.cardView1.setOnClickListener(view1 -> replaceFragment());
        binding.cardView2.setOnClickListener(view1 -> replaceFragment());

        binding.cardView3.setOnClickListener(view12 -> {
            Fragment medsFrag = new MedsCalenderFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.home_frame, medsFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        String selected = getActivity().getIntent().getStringExtra("selected participant");
        DatabaseReference medsRef = FirebaseDatabase.getInstance().getReference("Patient List/"+ selected + "/Health Data");


        medsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    binding.tvHRValue.setText(snapshot.child("Heart rate").child("Current").getValue(String.class) + " BPM");
                    binding.tvOxyVal.setText(snapshot.child("Oxygen").child("Current").getValue(String.class)+ " %");
                    binding.tvBPVal.setText(snapshot.child("BP").child("Current").getValue(String.class)+"mm Hg");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
    private void replaceFragment (){
        Fragment healthDataFrag = new ParticipantHelthInfoFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame, healthDataFrag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}