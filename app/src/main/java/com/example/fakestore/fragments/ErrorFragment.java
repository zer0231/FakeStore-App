package com.example.fakestore.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fakestore.databinding.FragmentErrorBinding;


public class ErrorFragment extends Fragment {

    private FragmentErrorBinding fragmentErrorBinding;

    public ErrorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        assert getArguments() != null;
        Throwable errorMessage = (Throwable) getArguments().getSerializable("errorMessage");
        fragmentErrorBinding = FragmentErrorBinding.inflate(inflater, container, false);
        fragmentErrorBinding.errorMessageTextview.setText(errorMessage.getLocalizedMessage());
        fragmentErrorBinding.tryAgainBtn.setOnClickListener(view -> {
            Navigation.findNavController(requireView()).navigateUp(); //This simply goes back to stack to try again
        });
        return fragmentErrorBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentErrorBinding = null;
    }
}