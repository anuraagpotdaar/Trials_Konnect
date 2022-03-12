package com.anuraagpotdaar.trialskonnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anuraagpotdaar.trialskonnect.HelperClasses.HealthDataModel;
import com.anuraagpotdaar.trialskonnect.HelperClasses.HealthDataReadingAdapter;
import com.anuraagpotdaar.trialskonnect.databinding.FragmentParticipantHelthInfoBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ParticipantHelthInfoFragment extends Fragment {

    private FragmentParticipantHelthInfoBinding binding;

    RecyclerView recyclerView ;
    DatabaseReference databaseReference;
    HealthDataReadingAdapter healthDataReadingAdapter;
    ArrayList<HealthDataModel> healthDataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentParticipantHelthInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        String selected = getActivity().getIntent().getStringExtra("selected participant");

        binding.tvHealthDataUserName.setText(getActivity().getIntent().getStringExtra("participant name"));


        recyclerView = binding.rvReadingList;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        healthDataList= new ArrayList<>();

        binding.ivDataType.setImageResource(R.drawable.ic_heart);
        binding.tvHaedingHealthData.setText("Heart rate");
        displayData(selected,"Heart rate");

        binding.toggleButton.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (binding.btnHeart.isChecked()) {
                binding.ivDataType.setImageResource(R.drawable.ic_heart);
                binding.tvHaedingHealthData.setText("Heart rate");
                displayData(selected,"Heart rate");
            } else if (binding.btnOxy.isChecked()) {
                binding.ivDataType.setImageResource(R.drawable.ic_oxygen);
                binding.tvHaedingHealthData.setText("Oxygen");
                displayData(selected,"Oxygen");

            } else if (binding.btnBP.isChecked()) {
                binding.ivDataType.setImageResource(R.drawable.ic_bp);
                binding.tvHaedingHealthData.setText("Blood pressure");
                displayData(selected,"BP");
            }
        });

        return binding.getRoot();
    }

    private void displayData(String selected, String selectedHealthData) {
        healthDataList.clear();

        databaseReference = FirebaseDatabase.getInstance().getReference("Patient List/"+selected+"/Health Data/"+ selectedHealthData);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    binding.tvCurrent.setText(snapshot.child("Current").getValue(String.class));
                    HealthDataModel healthDataModel = new HealthDataModel();

                    healthDataModel.setDate(dataSnapshot.getKey());

                    DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("Patient List/"+ selected + "/Health Data/" +selectedHealthData);

                    dataRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                healthDataModel.setValue(snapshot.child(healthDataModel.getDate()).getValue(String.class));
                            }
                            healthDataReadingAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    healthDataList.add(healthDataModel);
                }
                healthDataReadingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        healthDataReadingAdapter = new HealthDataReadingAdapter(getContext(),healthDataList);
        recyclerView.setAdapter(healthDataReadingAdapter);
        healthDataReadingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}