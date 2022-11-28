package com.ensa.ensaquiz.fragements;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ensa.ensaquiz.MainActivity;
import com.ensa.ensaquiz.R;
import com.ensa.ensaquiz.databinding.FragmentUpdateBinding;
import com.ensa.ensaquiz.entities.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class UpdateFragment extends Fragment {


    FragmentUpdateBinding binding ;
    FirebaseFirestore firebaseFirestore;
    public UpdateFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firebaseFirestore = FirebaseFirestore.getInstance();
        binding = FragmentUpdateBinding.inflate(inflater , container , false);


       firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot) {
               User user =  documentSnapshot.toObject(User.class);
             binding.emailBoxText.setText(user.getEmail());
             binding.nameBoxText.setText(user.getName());
           }
       });



binding.updateBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        final String email, password, name;
        email = binding.emailBoxText.getText().toString();
        password = binding.passBox.getText().toString();
        name = binding.nameBoxText.getText().toString();
        final HashMap<String , Object> newData = new HashMap<>();
        newData.put("email" ,email);
        newData.put("name" ,name);
        newData.put("pass" , password);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.updateEmail(email);
        firebaseUser.updatePassword(password);

        FirebaseAuth.getInstance().updateCurrentUser(firebaseUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                firebaseFirestore.collection("users")
                        .document(FirebaseAuth.getInstance().getUid()).update(newData);
            }
        });
        startActivity(new Intent(getActivity() , MainActivity.class));
    }
});

        return binding.getRoot();
    }
}