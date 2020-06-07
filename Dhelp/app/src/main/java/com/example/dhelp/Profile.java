package com.example.dhelp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    EditText fn,mail,pn,dob,age,address;
    ImageView profileImg;
    Uri uriProfileImg;
    Button edit,logout;
    ProgressBar progressBar;
    FirebaseFirestore fstore;
    FirebaseAuth auth;
    boolean check = false;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        logout = findViewById(R.id.logout);
        fn = findViewById(R.id.fn);
        mail = findViewById(R.id.mailb);
        pn = findViewById(R.id.pn);
        age = findViewById(R.id.age);
        dob = findViewById(R.id.dob);
        address = findViewById(R.id.address);
        edit = findViewById(R.id.editb);
        profileImg = findViewById(R.id.profilePic);
        progressBar = findViewById(R.id.progressbar2);


        loadUserInfo();

        FirebaseUser user = auth.getCurrentUser();


        mail.setText(user.getEmail());

        profileImg.setFocusable(true);

        fn.setEnabled(false);
        mail.setEnabled(false);
        pn.setEnabled(false);
        age.setEnabled(false);
        dob.setEnabled(false);
        address.setEnabled(false);

        fn.setInputType(EditorInfo.TYPE_NULL);
        mail.setInputType(EditorInfo.TYPE_NULL);
        pn.setInputType(EditorInfo.TYPE_NULL);
        age.setInputType(EditorInfo.TYPE_NULL);
        dob.setInputType(EditorInfo.TYPE_NULL);
        address.setInputType(EditorInfo.TYPE_NULL);


        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit.getText().toString().equals("edit")){
                    edit.setText(R.string.submit);
                    fn.setEnabled(true);
                    pn.setEnabled(true);
                    age.setEnabled(true);
                    dob.setEnabled(true);
                    address.setEnabled(true);

                    fn.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                    pn.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                    age.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                    dob.setInputType(EditorInfo.TYPE_CLASS_DATETIME);
                    address.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                }else {
                    edit.setText(R.string.edit);
                    fn.setEnabled(false);
                    mail.setEnabled(false);
                    pn.setEnabled(false);
                    age.setEnabled(false);
                    dob.setEnabled(false);
                    address.setEnabled(false);
                    saveUserInfo();

                }
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Profile.this,MainActivity.class);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(auth.getCurrentUser() == null){
            startActivity(new Intent(Profile.this,LoginScr.class));
            finish();
        }
    }



    private void loadUserInfo() {

        FirebaseUser user = auth.getCurrentUser();

        final DocumentReference documentReference = fstore.collection("Users").document(user.getUid()).collection("Documents").document("Profile Details");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {

                fn.setText(documentSnapshot.getString("Fname"));
                mail.setText(documentSnapshot.getString("mail"));
                pn.setText(documentSnapshot.getString("phone"));
                age.setText(documentSnapshot.getString("age"));
                dob.setText(documentSnapshot.getString("dob"));
                address.setText(documentSnapshot.getString("address"));

            }
        });


        if(user != null ){


            StorageReference profileRef = FirebaseStorage.getInstance().getReference().child("Users/"+user.getUid()+"/Profile.jpg");
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(profileImg);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                  //  Toast.makeText(Profile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });


        }else{
            Toast.makeText(Profile.this,"Some Errors",Toast.LENGTH_SHORT).show();
        }



    }

    private void saveUserInfo() {

        String name = fn.getText().toString();
        String em = mail.getText().toString();
        String phone = pn.getText().toString();
        String ag = age.getText().toString();
        String db = dob.getText().toString();
        String add = address.getText().toString();

        if(em.isEmpty()) {
            mail.setError("Email is required");
            mail.requestFocus();
            return;
        }else if(phone.isEmpty()){
            pn.setError("Phone is required");
            pn.requestFocus();
            return;
        }else if(name.isEmpty()){
            fn.setError("Name is required");
            fn.requestFocus();
            return;
        }else if(ag.isEmpty()){
            age.setError("Age is required");
            age.requestFocus();
            return;
        }else if(db.isEmpty()){
            dob.setError("Date Of Birth is required");
            dob.requestFocus();
            return;
        }else if(add.isEmpty()){
            address.setError("Address is Required");
            address.requestFocus();
            return;
        }else{

            FirebaseUser user = auth.getCurrentUser();

            DocumentReference documentReference = fstore.collection("Users").document(user.getUid()).collection("Documents").document("Profile Details");
            Map<String,Object> user1 = new HashMap<>();
            user1.put("Fname",name);
            user1.put("mail",em);
            user1.put("phone", phone);
            user1.put("age", ag);
            user1.put("dob", db);
            user1.put("address", add);
            documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("Debug","User is added");
                    Toast.makeText(Profile.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                }
            });


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode == 1000 && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriProfileImg = data.getData();
          //  profileImg.setImageURI(uriProfileImg);
            uploadImageToFirebaseStorage();
        }

    }

    private void uploadImageToFirebaseStorage() {


        storageReference = FirebaseStorage.getInstance().getReference();

        if(uriProfileImg != null){

            progressBar.setVisibility(View.VISIBLE);
            final StorageReference fileRef = storageReference.child("Users/"+auth.getCurrentUser().getUid()+"/Profile.jpg");
            fileRef.putFile(uriProfileImg)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get a URL to the uploaded content
                    progressBar.setVisibility(View.GONE);

                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(profileImg);
                        }
                    });

                    Toast.makeText(getApplicationContext(),"Profile Image Updated",Toast.LENGTH_SHORT).show();

                    check = true;


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), exception.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });



        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Profile.this,DashBoard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    public void showImageChooser()
    {

       Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       startActivityForResult(intent,1000);

    }




}
