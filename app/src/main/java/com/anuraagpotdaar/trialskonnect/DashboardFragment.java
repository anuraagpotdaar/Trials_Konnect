package com.anuraagpotdaar.trialskonnect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anuraagpotdaar.trialskonnect.databinding.FragmentDashboardBinding;
import com.anuraagpotdaar.trialskonnect.databinding.FragmentParticipantHelthInfoBinding;

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

        binding.cardView.setOnClickListener(view1 -> {
            replaceFragment();

        });
        binding.cardView1.setOnClickListener(view1 -> {
            replaceFragment();
        });
        binding.cardView2.setOnClickListener(view1 -> {
            replaceFragment();
        });



        return view;
    }
    private void replaceFragment (){
        Fragment healthDataFrag = new ParticipantHelthInfoFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame, healthDataFrag); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}