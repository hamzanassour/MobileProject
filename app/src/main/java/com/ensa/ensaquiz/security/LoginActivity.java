package com.ensa.ensaquiz.security;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ensa.ensaquiz.MainActivity;
import com.ensa.ensaquiz.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    // instead of using findViewById we are using the generated binding class ex : for login_activity.xml we hava ActivityLoginBinding we can find it in  build package
    // this class will have some references presented in xml file (attribute name take the id of the view in the xml file )
    ActivityLoginBinding activityLoginBinding;
    FirebaseAuth firebaseAuth; // from Firebase SDK
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("PLease Wait");
        progressDialog.setMessage("Logging in ...");

        // Listening in the submit button
        activityLoginBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, pass;
                email = activityLoginBinding.emailBoxText.getText().toString();
                pass = activityLoginBinding.passwordBoxText.getText().toString();


                progressDialog.show();
                // using signInWithEmailAndPassword pre built method to verify the password and email int firebase
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            // after a successful login the user will be redirected to Home page
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // if there is some issues we will print them to the user in the form of Toast
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
       // if user click in create new account button --> signup activity will be lunched
        activityLoginBinding.createNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });


    }
}