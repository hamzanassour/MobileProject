package com.ensa.ensaquiz.fragements;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ensa.ensaquiz.R;
import com.ensa.ensaquiz.databinding.FragmentProfileBinding;
import com.ensa.ensaquiz.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFragment extends Fragment {


    FragmentProfileBinding binding ;
    FirebaseAuth firebaseAuth ;
    FirebaseFirestore firebaseFirestore;
    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, name;

                email = binding.emailBoxText.getText().toString();
                password = binding.passBox.getText().toString();
                name = binding.nameBoxText.getText().toString();


                final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                currentUser.updateEmail(email);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}