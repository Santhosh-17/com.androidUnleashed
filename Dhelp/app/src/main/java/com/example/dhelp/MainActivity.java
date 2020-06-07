package com.example.dhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView t1,t2;
    ImageView img1;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            StorageReference profileRef = FirebaseStorage.getInstance().getReference().child("Users/"+auth.getCurrentUser().getUid()+"/Profile.jpg");
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                 /*   Picasso.get().load(uri)
                            .resize(50,50).into(img1);*/

                    Picasso.get()
                            .load(uri)
                            .transform(new CropCircleTransformation())
                            .into(img1);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                 //   Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
            t1.setText("My DashBoard");
            t2.setText(auth.getCurrentUser().getEmail());



        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar id
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //drawerLayout id
        drawerLayout = findViewById(R.id.drawer);


        //navigationView id
        navigationView = findViewById(R.id.navigationView);
        View hView = navigationView.getHeaderView(0);
        t1 = hView.findViewById(R.id.loginc);
        t2 = hView.findViewById(R.id.mailt);
        img1 = hView.findViewById(R.id.img);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auth = FirebaseAuth.getInstance();

                Intent intent = new Intent(MainActivity.this,LoginScr.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                if(drawerLayout.isDrawerOpen(GravityCompat.START))
                {
                    drawerLayout.closeDrawer(GravityCompat.START);

                }
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(drawerLayout.isDrawerOpen(GravityCompat.START))
                {
                    drawerLayout.closeDrawer(GravityCompat.START);

                }

                Intent intent = new Intent(MainActivity.this,LoginScr.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });





        navigationView.setNavigationItemSelectedListener(this);


        //actionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);

        //listener Drawer Layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //to enable hamburger sign
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        //sync the state
        actionBarDrawerToggle.syncState();

        //load default fragment
        //fragment Manager
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.cf,new MainFragment());
        fragmentTransaction.commit();



    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);

        }else
        {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        drawerLayout.closeDrawer(GravityCompat.START);
        switch(menuItem.getItemId())
        {
            case R.id.home: toolbar.setTitle(R.string.app_name);
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.cf,new MainFragment());
                            fragmentTransaction.commit();
                            break;

            case R.id.what: toolbar.setTitle(R.string.ic_what);
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.cf,new fragment_what());
                            fragmentTransaction.commit();
                            break;

            case R.id.prevent:toolbar.setTitle(R.string.prevention);
                              fragmentManager = getSupportFragmentManager();
                              fragmentTransaction = fragmentManager.beginTransaction();
                              fragmentTransaction.replace(R.id.cf,new fragment_prevent());
                              fragmentTransaction.commit();
                              break;

            case R.id.sym:  toolbar.setTitle(R.string.sym);
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.cf,new Symptoms());
                            fragmentTransaction.commit();
                            break;

            case R.id.about:toolbar.setTitle(R.string.ic_about);
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.cf,new Fragment_about());
                            fragmentTransaction.commit();
                            break;

            case R.id.toll: toolbar.setTitle(R.string.ic_toll_free);
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.cf,new TollFree());
                            fragmentTransaction.commit();
                            break;

        }
        return true;
    }
}
