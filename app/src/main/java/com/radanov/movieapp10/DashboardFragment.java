package com.radanov.movieapp10;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.radanov.movieapp10.databinding.FragmentDashboardBinding;
import com.radanov.movieapp10.databinding.FragmentHomeBinding;


public class DashboardFragment extends Fragment {


   FragmentDashboardBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

}
