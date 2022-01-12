package com.example.sampleschool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sampleschool.Common.Common;
import com.example.sampleschool.Model.StudentModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.gson.Gson;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText edt_email,edt_password;
    Button btn_login;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)
            home(firebaseAuth.getCurrentUser());
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(login);
    }

    private View.OnClickListener login = view -> {
        if (edt_email.getText().toString().isEmpty())
        {
            edt_email.setError("Email cannot be empty !");
        }
        else if (edt_password.getText().toString().isEmpty())
        {
            edt_password.setError("Password cannot be empty");
        }
        else
        {
            firebaseAuth.signInWithEmailAndPassword(edt_email.getText().toString().toUpperCase(Locale.ROOT),
                    edt_password.getText().toString().trim())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            home(authResult.getUser());
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(MainActivity.this, ""+e, Toast.LENGTH_SHORT).show());
        }
    };

    private void home(FirebaseUser user) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Students")
                .document(user.getUid())
                .addSnapshotListener((value, error) -> {
                    if (error != null)
                    {
                        Log.d("Listen Error: ",""+error);
                    }
                    else
                    {
                        if (value!=null && value.exists())
                        {
                            Common.currentStudent = value.toObject(StudentModel.class);
                            FirebaseMessaging.getInstance().getToken()
                                    .addOnSuccessListener(s -> {
                                        Common.updateToken(MainActivity.this,s);
                                        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show());

                        }
                        else {
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show();
                        }
                    }


                });

    }

}