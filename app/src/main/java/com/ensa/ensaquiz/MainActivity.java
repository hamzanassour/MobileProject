package com.ensa.ensaquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ensa.ensaquiz.databinding.ActivityMainBinding;
import com.ensa.ensaquiz.fragements.HomeFragment;
import com.ensa.ensaquiz.fragements.DashboardFragment;
import com.ensa.ensaquiz.fragements.UpdateFragment;
import com.ensa.ensaquiz.fragements.ScoreFragment;
import com.ensa.ensaquiz.security.LoginActivity;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new HomeFragment());
        transaction.commit();



        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (i) {
                    case 0:
                        transaction.replace(R.id.content, new HomeFragment());
                        transaction.commit();
                        break;
                    case 1:
                        transaction.replace(R.id.content, new DashboardFragment());
                        transaction.commit();
                        break;
                    case 2:
                        transaction.replace(R.id.content, new ScoreFragment());
                        transaction.commit();
                        break;
                    case 3:
                        transaction.replace(R.id.content, new UpdateFragment());
                        transaction.commit();
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(MainActivity.this);
        alertdialog.setMessage("Do you Want to log out?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        switch(item.getItemId()){

            case R.id.logout:

                AlertDialog alert=alertdialog.create();
                alert.setTitle("Logout");
                alert.show();

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}